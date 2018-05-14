package com.utils.part1.httpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class HttpClientComm {
	
	private int _tmOut = 15000;
	
	public String postJson(String url,String jsonParam,Map<String, String> heads,Map<String, String> cookies,String charset) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(_tmOut);  //设置连接超时
		httpClient.getParams().setSoTimeout(_tmOut);    //设置读数据超时
		
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-type", "application/json");
		setHeaders(postMethod, heads);
		setCookie(httpClient, cookies);
		String resString = "";
		
		try {
			RequestEntity entity=new ByteArrayRequestEntity(jsonParam.getBytes(charset));
			postMethod.setRequestEntity(entity);
			int statusCode=httpClient.executeMethod(postMethod);
			if(statusCode==HttpStatus.SC_OK) {
				byte[] responseBody=postMethod.getResponseBody();
				resString=new String(responseBody, charset);
			}else {
				System.out.println("状态码:"+statusCode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resString;
	}
	
	private void setHeaders(PostMethod method,Map<String, String> heads) {
		if(heads==null||heads.isEmpty()) {
			return;
		}
		Iterator<Entry<String, String>> it=heads.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> entry=it.next();
			method.setRequestHeader(entry.getKey(), entry.getValue());
		}
	}
	
	private void setCookie(HttpClient httpClient,Map<String, String> cookies) {
		if(cookies==null||cookies.isEmpty()) {
			return;
		}
		HttpState state=httpClient.getState();
		Iterator<Entry<String, String>> it=cookies.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> entry=it.next();
			Cookie cookie=new Cookie();
			cookie.setName(entry.getKey());
			cookie.setValue(entry.getValue());
			state.addCookie(cookie);
		}
	}
}
