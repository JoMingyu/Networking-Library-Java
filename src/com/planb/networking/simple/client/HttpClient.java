package com.planb.networking.simple.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.planb.networking.simple.exceptions.TargetAddressNotDeclaredException;

public class HttpClient {
	private HttpClientConfig config = null;
	
	private URL url = null;
	private HttpURLConnection connection = null;
	private InputStream in = null;
	private OutputStream out = null;
	
	public HttpClient() throws TargetAddressNotDeclaredException {
		config = new HttpClientConfig();
		if(config.getTargetAddress() == null) {
			throw new TargetAddressNotDeclaredException();
		}
	}
	
	public int post(String uri) {
		/*
		 * body data가 없는 post 요청
		 * status code 리턴
		 */
		String requestAddress = createRequestAddress(uri);
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			return connection.getResponseCode();
		} catch(IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int post(String uri, HashMap<String, Object> params) {
		/*
		 * body data가 있는 post 요청
		 * status code 리턴
		 */
		String requestAddress = createRequestAddress(uri);
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			out = connection.getOutputStream();
			out.write(createParamBytes(params));
			
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public HashMap<String, Object> get(String uri) {
		/*
		 * 파라미터가 없는 get 요청
		 * status code와 응답 데이터 리턴
		 */
		String requestAddress = createRequestAddress(uri);
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			in = connection.getInputStream();
			String response = getResponse(in);
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("code", connection.getResponseCode());
			map.put("response", response);
			
			return (HashMap<String, Object>) map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, Object> get(String uri, HashMap<String, Object> params) {
		/*
		 * 파라미터가 있는 get 요청
		 * status code와 응답 데이터 리턴
		 */
		String requestAddress = createRequestAddress(uri, params);
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			in = connection.getInputStream();
			String response = getResponse(in);
			Map<String, Object> map = new HashMap<String, Object>(1);
			map.put("code", connection.getResponseCode());
			map.put("response", response);
			
			return (HashMap<String, Object>) map;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String createRequestAddress(String uri) {
		// POST 요청 또는 파라미터가 없는 GET 요청에서의 request address
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// 비정상 URI 방지
		
		return config.getTargetAddress() + ":" + config.getTargetPort() + uri;
	}
	
	private String createRequestAddress(String uri, HashMap<String, Object> params) {
		/*
		 * 파라미터가 있는 GET 요청에서의 request address
		 * URI?key=value&key=value 형태
		 */
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// 비정상 URI 방지
		
		StringBuilder requestAddress = new StringBuilder();
		requestAddress.append(config.getTargetAddress());
		requestAddress.append(":");
		requestAddress.append(config.getTargetPort());
		requestAddress.append(uri);
		requestAddress.append("?");
		Set<String> keySet = params.keySet();
		Iterator<String> iterator = keySet.iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next();
			String value = (String) params.get(key);
			requestAddress.append(key).append("=").append(value).append("&");
		}
		
		String requestAddressStr = requestAddress.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr;
	}
	
	private byte[] createParamBytes(HashMap<String, Object> params) {
		// POST 메소드에서 사용하는 byte 타입의 body 데이터
		if(params.size() == 0) {
			return "".getBytes();
		}
		
		StringBuilder requestData = new StringBuilder();
		Set<String> keySet = params.keySet();
		Iterator<String> iterator = keySet.iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next();
			String value = String.valueOf(params.get(key));
			requestData.append(key).append("=").append(value).append("&");
		}
		
		String requestAddressStr = requestData.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr.getBytes();
	}
	
	private String getResponse(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024 * 8];
		int length;
		
		try {
			while((length = in.read(buf)) != 1) {
				out.write(buf, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			return new String(out.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}