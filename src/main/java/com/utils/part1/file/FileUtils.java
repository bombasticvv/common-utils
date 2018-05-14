package com.utils.part1.file;

import java.io.File;

public class FileUtils {
	
	/**
	 * @param size
	 * 		file.length()作为入参
	 */
	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;
		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}
	
	public static void main(String[] args) {
		String filePath=System.getProperty("user.dir")+"/resource/CompressedFile.zip";
		File file=new File(filePath);
		System.out.println(convertFileSize(file.length()));
	}
}
