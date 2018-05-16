package com.utils.part1.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.utils.part1.binary.ParseSystemUtil;

/**
 * 散列算法（哈希算法）：SHA和MD5
 * 支持的算法有MD5、SHA-1、SHA-256、SHA-512等
 */
public class MessageDigestUtil {
	public static String encryptHex(String content,String algorithm) {
		String resultString = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(content.getBytes());
			byte[] bytes=digest.digest();
			resultString=ParseSystemUtil.parseByte2HexStr(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return resultString;
	}
	public static void main(String[] args) {
		System.out.println(encryptHex("1234567890", "MD5"));
		System.out.println(encryptHex("1234567890", "SHA-1"));
		System.out.println(encryptHex("1234567890", "SHA-256"));
		System.out.println(encryptHex("1234567890", "SHA-512"));
	}
}
