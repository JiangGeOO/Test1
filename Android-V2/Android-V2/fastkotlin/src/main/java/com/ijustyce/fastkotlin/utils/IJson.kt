package com.ijustyce.fastkotlin.utils

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by yc on 17-11-6.
 */
object IJson {

    internal var gson: Gson

    fun toJson(jsonObject: Any?, type: Type?): String? {
        jsonObject?: return null
        type?: return null
        try {
            return gson.toJson(jsonObject, type)
        } catch (var4: Exception) {
            var4.printStackTrace()
            return null
        }
    }

    fun getGson(): Gson {
        return gson
    }

    fun <T> fromJson(jsonString: String?, type: Type?): T? {
        if (StringUtils.isEmpty(jsonString) || type == null) return null
        try {
            return gson.fromJson(jsonString, type)
        } catch (var4: Exception) {
            var4.printStackTrace()
            return null
        }
    }

    init {
        val myExclusionStrategy = object : ExclusionStrategy {
            override fun shouldSkipField(fa: FieldAttributes): Boolean {
                return fa.name.startsWith("_")
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        }
        val stringTypeAdapter = object : TypeAdapter<String?>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: String?) {
                out.value(if (StringUtils.isEmpty(value)) "" else value)
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): String? {
                if (`in`.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return null
                } else {
                    val str = `in`.nextString()
                    return if (StringUtils.isEmpty(str)) "" else str
                }
            }
        }
        val intTypeAdapter = object : TypeAdapter<Int>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Int?) {
                if (value == null) {
                    out.value(0L)
                } else {
                    out.value(value)
                }
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): Int? {
                if (`in`.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return null
                } else {
                    return Integer.valueOf(StringUtils.getInt(`in`.nextString()))
                }
            }
        }
        val longTypeAdapter = object : TypeAdapter<Long>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Long?) {
                if (value == null) {
                    out.value(0L)
                } else {
                    out.value(value)
                }

            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): Long? {
                if (`in`.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return null
                } else {
                    return java.lang.Long.valueOf(StringUtils.getLong(`in`.nextString()))
                }
            }
        }
        val floatTypeAdapter = object : TypeAdapter<Float>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Float?) {
                if (value == null) {
                    out.value(0L)
                } else {
                    out.value(value)
                }

            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): Float? {
                if (`in`.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return null
                } else {
                    return java.lang.Float.valueOf(StringUtils.getFloat(`in`.nextString()))
                }
            }
        }
        val doubleTypeAdapter = object : TypeAdapter<Double>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Double?) {
                if (value == null) {
                    out.value(0L)
                } else {
                    out.value(value)
                }

            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): Double? {
                if (`in`.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return null
                } else {
                    return java.lang.Double.valueOf(StringUtils.getDouble(`in`.nextString()))
                }
            }
        }
        gson = GsonBuilder().setExclusionStrategies(myExclusionStrategy)
                .registerTypeAdapter(String::class.java, stringTypeAdapter)
                .registerTypeAdapter(Int::class.java, intTypeAdapter)
                .registerTypeAdapter(Integer.TYPE, intTypeAdapter)
                .registerTypeAdapter(Long::class.java, longTypeAdapter)
                .registerTypeAdapter(java.lang.Long.TYPE, longTypeAdapter)
                .registerTypeAdapter(Float::class.java, floatTypeAdapter)
                .registerTypeAdapter(java.lang.Float.TYPE, floatTypeAdapter)
                .registerTypeAdapter(Double::class.java, doubleTypeAdapter)
                .registerTypeAdapter(java.lang.Double.TYPE, doubleTypeAdapter)
                .create()
    }
}