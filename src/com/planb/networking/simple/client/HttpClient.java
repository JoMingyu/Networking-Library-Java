package com.planb.networking.simple.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
	
	public int post(String uri, HashMap<String, Object> headers, HashMap<String, Object> params) {
		/*
		 * post ��û
		 * status code ����
		 */
		String requestAddress = createRequestAddress(uri);
		// URI�� ���� ��û �ּ� ������
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// POST ��û �� DoOutput Ȱ��ȭ
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			if(headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			if(params.size() > 0) {
				out = connection.getOutputStream();
				out.write(createParamBytes(params));
				// Body �����Ͱ� ������ ����Ʈ ������ �����͸� ����
			}
			
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public HashMap<String, Object> get(String uri, HashMap<String, Object> headers, HashMap<String, Object> params) {
		/*
		 * get ��û
		 * status code�� ���� ������ ����
		 */
		String requestAddress = null;
		if(params.size() > 0) {
			requestAddress = createRequestAddress(uri, params);
			// URI�� �Ķ���͸� ���� ��û �ּ� ������
		} else {
			requestAddress = createRequestAddress(uri);
		}
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			if(headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>(1);
			try {
				in = connection.getInputStream();
				String response = getResponse(in);
				// connection���� ���� InputStream���� ���� ������
				map.put("code", connection.getResponseCode());
				map.put("response", response);
			} catch(IOException e) {
				map.put("code", 500);
			}
			
			return (HashMap<String, Object>) map;
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
		
		for(String key : params.keySet()) {
			String value = (String) params.get(key);
			requestAddress.append(key).append("=").append(value).append("&");
		}
		
		String requestAddressStr = requestAddress.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr;
	}
	
	private byte[] createParamBytes(HashMap<String, Object> params) {
		// POST �޼ҵ忡�� ����ϴ� byte Ÿ���� body ������
		StringBuilder requestData = new StringBuilder();
		
		for(String key : params.keySet()) {
			String value = String.valueOf(params.get(key));
			requestData.append(key).append("=").append(value).append("&");
		}
		
		String requestAddressStr = requestData.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr.getBytes();
	}
	
	private String getResponse(InputStream in) {
		if(in == null) {
			return null;
		}
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