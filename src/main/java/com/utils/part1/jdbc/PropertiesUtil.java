package com.utils.part1.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static String getValue(String key) {
		Properties  prop = new Properties();
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("conf/jdbc.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String)prop.get(key);
	}
	
	public static void main(String[] args) {
		System.out.println(getValue("url"));
		System.out.println(PropertiesUtil.class.getResource("").getPath());
		System.out.println(new PropertiesUtil().getClass().getResource("").getPath());
		System.out.println(PropertiesUtil.class.getClassLoader().getResource("").getPath());
	}
	
}
