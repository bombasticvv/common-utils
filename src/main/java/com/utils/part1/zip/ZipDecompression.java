package com.utils.part1.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * zip压缩文件解压工具类
 * 
 */
public class ZipDecompression {

    public static void main(String[] args) throws IOException {
    	String filePath=System.getProperty("user.dir")+"/resource/CompressedFile.zip";  //待解压文件
    	String decompressFolder=System.getProperty("user.dir")+"/resource/decompressFolder/";  //解压到该目录
        decompression(filePath,decompressFolder);
        System.out.println("end");
    }
    /**
     * 解压ZIP文件
     * @param zipFile  要解压的ZIP文件路径
     * @param destination  解压到哪里
     * @throws IOException
     */
    public static void decompression(String zipFile,String destination) throws IOException {
        ZipFile zip=new ZipFile(zipFile,Charset.forName("GBK"));
        Enumeration en=zip.entries();
        ZipEntry entry=null;
        byte[] buffer=new byte[8192];
        int length=-1;
        InputStream input=null;
        BufferedOutputStream bos=null;
        File file=null;
        
        while(en.hasMoreElements()) {
            entry=(ZipEntry)en.nextElement();
            if(entry.isDirectory()) {
                continue;
            }
            
            input=zip.getInputStream(entry);
            file=new File(destination,entry.getName());
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            bos=new BufferedOutputStream(new FileOutputStream(file));
            
            while(true) {
                length=input.read(buffer);
                if(length==-1) break;
                bos.write(buffer,0,length);
            }
            bos.close();
            input.close();
        }
        zip.close();
    }
}
