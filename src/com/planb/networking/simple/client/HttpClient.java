package com.planb.networking.simple.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.planb.networking.simple.exceptions.TargetAddressNotDeclaredException;

public class HttpClient {
	HttpClientConfig config = null;
	
	URL url = null;
	HttpURLConnection connection = null;
	InputStream in = null;
	OutputStream out = null;
	ByteArrayOutputStream res = new ByteArrayOutputStream();
	
	public HttpClient() throws IOException, TargetAddressNotDeclaredException {
		config = new HttpClientConfig();
		if(config.getTargetAddress() == null) {
			throw new TargetAddressNotDeclaredException();
		}
	}
	
	public int post(String uri, HashMap<String, Object> params) {
		// Status code 리턴
		String requestAddress = createRequestAddress(uri);
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			out = connection.getOutputStream();
			out.write(createParamString(params));
			
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			
			return 0;
		}
	}
	
	public HashMap<Integer, String> get(String uri, HashMap<String, Object> params) {
		// Status code와 응답 리턴
		String requestAddress = createRequestAddress(uri);
	}
	
	private String createRequestAddress(String uri) {
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// 비정상 흐름 방지
		
		return config.getTargetAddress() + ":" + config.getTargetPort() + uri;
	}
	
	private byte createParamString(HashMap<String, Object> params) {
		StringBuilder param = new StringBuilder();
		
		Set<String> keySet = params.keySet();
		Iterator<String> iterator = keySet.iterator();
	}
}
