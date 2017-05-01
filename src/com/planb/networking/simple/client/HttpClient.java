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
	HttpClientConfig config = null;
	
	URL url = null;
	HttpURLConnection connection = null;
	InputStream in = null;
	OutputStream out = null;
	ByteArrayOutputStream res = new ByteArrayOutputStream();
	
	public HttpClient() throws TargetAddressNotDeclaredException {
		config = new HttpClientConfig();
		if(config.getTargetAddress() == null) {
			throw new TargetAddressNotDeclaredException();
		}
	}
	
	public int post(String uri) {
		/*
		 * body data�� ���� post ��û
		 * status code ����
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
		 * body data�� �ִ� post ��û
		 * status code ����
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
	
	public HashMap<Integer, String> get(String uri) {
		/*
		 * �Ķ���Ͱ� ���� get ��û
		 * status code�� ���� ������ ����
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
			Map<Integer, String> map = new HashMap<Integer, String>(1);
			map.put(connection.getResponseCode(), response);
			
			return (HashMap<Integer, String>) map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<Integer, String> get(String uri, HashMap<String, Object> params) {
		/*
		 * �Ķ���Ͱ� �ִ� get ��û
		 * status code�� ���� ������ ����
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
			Map<Integer, String> map = new HashMap<Integer, String>(1);
			map.put(connection.getResponseCode(), response);
			
			return (HashMap<Integer, String>) map;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String createRequestAddress(String uri) {
		// POST ��û �Ǵ� �Ķ���Ͱ� ���� GET ��û������ request address
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// ������ URI ����
		
		return config.getTargetAddress() + ":" + config.getTargetPort() + uri;
	}
	
	private String createRequestAddress(String uri, HashMap<String, Object> params) {
		/*
		 * �Ķ���Ͱ� �ִ� GET ��û������ request address
		 * URI?key=value&key=value ����
		 */
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// ������ URI ����
		
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
		// POST �޼ҵ忡�� ����ϴ� byte Ÿ���� body ������
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