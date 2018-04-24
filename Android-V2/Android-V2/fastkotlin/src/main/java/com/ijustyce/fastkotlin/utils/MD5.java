package com.ijustyce.fastkotlin.utils;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 加密工具类
 */
@SuppressLint("DefaultLocale")
public class MD5 {

	public static String encrypt16byte(String data) throws Exception {
		if (StringUtils.INSTANCE.isEmpty(data)) return data;
		// 返回结果
		String result = "";
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(data.getBytes("UTF-8"));
			byte[] array = md.digest();

			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				if (Integer.toHexString(0xFF & array[i]).length() == 1) {
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & array[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & array[i]));
				}
			}
			// 16位加密，从第9位到25位
			result = md5StrBuff.substring(8, 24).toLowerCase();
		} catch (Exception e) {
			throw new Exception(e);
		}

		return result;
	}

	public static String encrypt32byte(String data)  {
		if (StringUtils.INSTANCE.isEmpty(data)) return data;
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			ILog.INSTANCE.e("Huh, MD5 should be supported?", e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			ILog.INSTANCE.e("Huh, UTF-8 should be supported?", e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString().toLowerCase();
	}
}
