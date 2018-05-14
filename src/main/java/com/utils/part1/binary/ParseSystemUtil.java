package com.utils.part1.binary;

import java.util.Arrays;

/**
 * 二进制与16进制相互转换
 * 
 */
public class ParseSystemUtil {
	/**
	 * 二进制转16进制 
	 */
	public static String parseByte2HexStr(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	/**
	 * 16进制转二进制 
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	public static void main(String[] args) {
		String str="128h中";
		System.out.println("二进制:"+Arrays.toString(str.getBytes()));
		String hexStr=parseByte2HexStr(str.getBytes());
		System.out.println("二进制转十六进制:"+hexStr);
		System.out.println("十六进制转二进制:"+Arrays.toString(parseHexStr2Byte(hexStr)));
	}
}
