package com.utils.part1.httpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class HttpClientComm {
	
	private int _tmOut = 15000;
	
	public String doPostJson(String url,String jsonParam,Map<String, String> heads,Map<String, String> cookies,String charset) {
		HttpClient client = getHttpClient();
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-type", "application/json");
		
		setHeaders(method, heads);
		setCookie(client, cookies);
		
		String resString = "";
		try {
			RequestEntity entity=new ByteArrayRequestEntity(jsonParam.getBytes(charset));
			method.setRequestEntity(entity);
			int statusCode=client.executeMethod(method);
			if(statusCode==HttpStatus.SC_OK) {
				byte[] responseBody=method.getResponseBody();
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
	
	public String doGet(String url,Map<String, String> param,Map<String, String> heads,Map<String, String> cookies,String charset) {
		HttpClient client = getHttpClient();
		GetMethod method = new GetMethod(url);
		
		setHeaders(method, heads);
		setCookie(client, cookies);
		
		if(param!=null&&param.size()>0) {
			NameValuePair[] nameValuePair=new NameValuePair[param.size()];
			Iterator<Entry<String, String>> it=param.entrySet().iterator();
			int i=0;
			while(it.hasNext()) {
				Entry<String, String> entry=it.next();
				nameValuePair[i]=new NameValuePair(entry.getKey(), entry.getValue());
				i++;
			}
			method.setQueryString(nameValuePair);
		}
		
		String resString = "";
		try {
			int statusCode=client.executeMethod(method);
			if(statusCode==HttpStatus.SC_OK) {
				byte[] responseBody=method.getResponseBody();
				resString=new String(responseBody, charset);
			}else {
				System.out.println("状态码:"+statusCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resString;
	}
	
	private void setHeaders(HttpMethod method,Map<String, String> heads) {
		if(heads==null||heads.isEmpty()) {
			return;
		}
		Iterator<Entry<String, String>> it=heads.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> entry=it.next();
			method.addRequestHeader(entry.getKey(), entry.getValue());
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
	
	private HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(_tmOut);  //设置连接超时
		httpClient.getParams().setSoTimeout(_tmOut);    //设置读数据超时
		return httpClient;
	}
}
