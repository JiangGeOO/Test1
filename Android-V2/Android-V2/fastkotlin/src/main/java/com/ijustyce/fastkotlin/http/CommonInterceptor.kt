package com.ijustyce.fastkotlin.http

import com.ijustyce.fastkotlin.utils.StringUtils
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.util.*

/**
 * Created by arch on 17-11-6.
 */
class CommonInterceptor : Interceptor {

    private val commonParameter = HashMap<String, String>()
    private val header = HashMap<String, String>()

    fun addCommonHeader(key: String, value: String?): CommonInterceptor {
        if (StringUtils.isEmpty(key)) return this
        if (value != null) {
            header.put(key, value)
        } else {
            header.remove(key)
        }
        return this
    }

    fun addCommonParameter(key: String, value: String?): CommonInterceptor {
        if (StringUtils.isEmpty(key)) return this
        if (value != null) {
            commonParameter.put(key, value)
        } else {
            commonParameter.remove(key)
        }
        return this
    }

    private fun canInjectIntoBody(request: Request?): Boolean {
        if (request == null) {
            return false
        }
        if (!"POST".equals(request.method(), ignoreCase = true)) {
            return false
        }
        val body = request.body() ?: return false
        val mediaType = body.contentType()
        val subType = mediaType?.subtype()
        //  有参数,并且类型不是 x-www-form-urlencoded 这里必须要判断是否为空,比如没有参数的post请求
        return subType == null || subType.equals("x-www-form-urlencoded", ignoreCase = true)
    }

    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val builder = oldRequest.newBuilder()

        val params = HashMap<String, String>()
        val headers = HashMap<String, String>()
        params.putAll(commonParameter)
        headers.putAll(header)

        // 添加新的参数
        val authorizedUrlBuilder = oldRequest.url().newBuilder()

        //  针对POST请求
        if (canInjectIntoBody(oldRequest)) {
            val formBodyBuilder = FormBody.Builder()
            var postBodyString = bodyToString(oldRequest.body())
            val map = StringUtils.getKeyValue(postBodyString)
            for (entry in params.entries) {
                val key = entry.key
                if (!map.containsKey(key)) {
                    formBodyBuilder.add(key, entry.value)
                }
            }

            val formBody = formBodyBuilder.build()
            postBodyString += (if (postBodyString.length > 0) "&" else "") + bodyToString(formBody)
            builder.post(RequestBody.create(
                    MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
        } else if (!"POST".equals(oldRequest.method(), ignoreCase = true)) {
            val postBodyString = bodyToString(oldRequest.body())
            val map = StringUtils.getKeyValue(postBodyString)
            for (entry in params.entries) {
                val key = entry.key
                if (!map.containsKey(key)) {
                    authorizedUrlBuilder.addQueryParameter(key, entry.value)
                }
            }
        }//  如果不是POST请求

        for (entry in headers.entries) {
            val key = entry.key
            builder.addHeader(key, entry.value)
        }

        // 新的请求
        builder.url(authorizedUrlBuilder.build())
        return chain.proceed(builder.build())
    }
}