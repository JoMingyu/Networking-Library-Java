package com.planb.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class NetworkingHelper {
	private static String validateUri(String targetAddress, String uri) {
		if(uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		
		if(!uri.startsWith("/")) {
			uri = "/" + uri;
		}
		
		return targetAddress + uri;
	}
	
	static String createRequestAddress(String targetAddress, String uri) {
		// POST 요청 또는 파라미터가 없는 GET 요청에서의 request address
		
		return validateUri(targetAddress, uri);
	}
	
	static String createRequestAddress(String targetAddress, String uri, Map<String, Object> params) {
		/*
		 * 파라미터가 있는 GET 요청에서의 request address
		 * URI?key=value&key=value 형태
		 */
		
		StringBuilder requestAddress = new StringBuilder();
		requestAddress.append(validateUri(targetAddress, uri)).append("?");
		for(String key : params.keySet()) {
			String value = (String) params.get(key);
			try {
				requestAddress.append(key).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String requestAddressStr = requestAddress.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr;
	}
	
	static byte[] createParamBytes(Map<String, Object> params) {
		// POST 메소드에서 사용하는 byte 타입의 body 데이터
		StringBuilder requestData = new StringBuilder();
		
		for(String key : params.keySet()) {
			String value = String.valueOf(params.get(key));
			try {
				requestData.append(key).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String requestAddressStr = requestData.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr.getBytes();
	}
	
	static String getResponse(InputStream in) {
		if(in == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024 * 16];
		int length;
		try {
			while((length = in.read(buf)) != -1) {
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
