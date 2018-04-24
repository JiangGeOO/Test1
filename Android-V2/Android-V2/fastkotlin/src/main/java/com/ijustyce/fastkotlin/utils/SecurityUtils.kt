package com.ijustyce.fastkotlin.utils

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object SecurityUtils {

    fun HmacSHA1Encrypt(encryptText: String, encryptKey: String): ByteArray {
        val data = encryptKey.toByteArray()
        val secretKey = SecretKeySpec(data, "HmacSHA1")
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(secretKey)
        val text = encryptText.toByteArray()
        return mac.doFinal(text)
    }
}