package com.ijustyce.fastkotlin.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.ijustyce.fastkotlin.IApplication
import com.ijustyce.fastkotlin.utils.IJson
import com.ijustyce.fastkotlin.utils.RegularUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * Created by arch on 17-11-6.
 */
object HttpManager {

    private var retrofit: Retrofit? = null
    private var baseUrl: String = ""
    private var intercept = CommonInterceptor()
    private val DEFAULT_TIMEOUT = 15
    private var httpsCer: String? = null
    private var networkInfo: NetworkInfo?
    private var showLog = false

    init {
        val conManager = IApplication.instance()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkInfo = conManager.activeNetworkInfo
    }

    fun setShowLog(showLog: Boolean) {
        HttpManager.showLog = showLog
        if (retrofit != null) {
            throw IllegalArgumentException("please call this function before you use HttpManager")
        }
    }

    fun isConnected(): Boolean {
        return networkInfo?.isAvailable?:true
    }

    fun addCommonParameter(key: String, value: String) {
        intercept.addCommonParameter(key, value)
    }

    fun addCommonHeader(key: String, value: String?) {
        intercept.addCommonHeader(key, value)
    }

    fun setBaseUrl(url: String) {
        baseUrl = url
    }

    fun setHttpsCer(name: String) {
        httpsCer = name
    }

    fun getBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        if (showLog) {
            builder.addNetworkInterceptor(HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger { message -> Log.e("网络请求:", message) })
                    .setLevel(HttpLoggingInterceptor.Level.BODY)).build()
        }
        builder.addInterceptor(intercept)
        return builder
    }

    private fun getUnSafeBuilder(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier { _, _ -> true }
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private fun getCertificates(): SSLSocketFactory? {
        try {

            val inputStream = IApplication.instance().getAssets().open(httpsCer)
            val certificateFactory = CertificateFactory.getInstance("X.509")

            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)

            keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(inputStream))

            val sslContext = SSLContext.getInstance("TLS")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun init() {
        val client = getBuilder().build()
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(IJson.getGson()))
                .client(client)
                .build()
    }

    fun <T> service(service: Class<T>): T {
        if (retrofit == null) {
            if (RegularUtils.isCommonUrl(baseUrl)) {
                init()
            }
        }
        return retrofit?.create(service) ?:
                throw RuntimeException("please call HttpManager.setBaseUrl(url) before use")
    }

    private var defaultCallBack: HttpResult<*>? = null
    fun <T> setDefaultCallBack(httpResult: HttpResult<T>) {
        defaultCallBack = httpResult
    }

    interface HttpResult<T> {
        fun success(call: Call<T>?, response: Response<T>?)
        fun failed(call: Call<T>?, t: Throwable?) {}
    }

    fun <T> send(httpResult: HttpResult<T>?, call: Call<T>?): Boolean {
        if (call == null || !isConnected()) return false
        call.clone().enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                (defaultCallBack as? HttpResult<T>)?.success(call, response)
                httpResult?.success(call, response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace()
                (defaultCallBack as? HttpResult<T>)?.failed(call, t)
                httpResult?.failed(call, t)
            }
        })
        return true
    }
}