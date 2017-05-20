package com.planb.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

public class HttpClient {
	private String targetAddress;
	private URL url;
	
	private int readTimeout = 3000;
	private int connectTimeout = 3000;
	
	private HttpURLConnection connection = null;
	private InputStream in = null;
	private OutputStream out = null;
	private OutputStreamWriter wr = null;
	
	public HttpClient(String targetAddress, int port) {
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = port == 80 ? targetAddress : targetAddress + ":" + port;
	}
	
	public HttpClient(String targetAddress) {
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = targetAddress;
	}
	
	public Response post(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * post 요청
		 * status code 리턴
		 */
		String requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
		// URI를 통해 요청 주소 얻어오기
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// POST 요청 시 DoOutput 활성화
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			if(params != null && params.size() > 0) {
				out = connection.getOutputStream();
				out.write(NetworkingHelper.createParamBytes(params));
				// Body 데이터가 있으면 바이트 형태의 데이터를 전송
				out.flush();
			}
			
			Response response = new Response();
			in = connection.getInputStream();
			String responseBody = NetworkingHelper.getResponse(in);
			// connection으로 얻은 InputStream에서 응답 얻어오기
			response.setResponseBody(responseBody);
			response.setResponseCode(connection.getResponseCode());
			response.setResponseHeader(connection.getHeaderFields());
			
			connection.disconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response post(String uri, Map<String, Object> headers, JSONObject requestObject) {
		/*
		 * post 요청 : 본문 데이터가 JSON
		 * status code 리턴
		 */
		String requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
		// URI를 통해 요청 주소 얻어오기
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// POST 요청 시 DoOutput 활성화
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(requestObject.toString());
			wr.flush();
			
			Response response = new Response();
			in = connection.getInputStream();
			String responseBody = NetworkingHelper.getResponse(in);
			// connection으로 얻은 InputStream에서 응답 얻어오기
			response.setResponseBody(responseBody);
			response.setResponseCode(connection.getResponseCode());
			response.setResponseHeader(connection.getHeaderFields());
			
			connection.disconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response get(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * get 요청
		 * status code와 응답 데이터 리턴
		 */
		String requestAddress = null;
		if(params != null && params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri, params);
			// Request address with uri and parameter
		} else {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
			// Request address with uri
		}
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			Response response = new Response();
			in = connection.getInputStream();
			String responseBody = NetworkingHelper.getResponse(in);
			// connection으로 얻은 InputStream에서 응답 얻어오기
			response.setResponseBody(responseBody);
			response.setResponseCode(connection.getResponseCode());
			response.setResponseHeader(connection.getHeaderFields());
			
			connection.disconnect();
			return response;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int delete(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * delete 요청
		 * status code 리턴
		 */
		String requestAddress = null;
		if(params != null && params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri, params);
			// Request address with uri and parameter
		} else {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
			// Request address with uri
		}
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			connection.disconnect();
			return connection.getResponseCode();
		} catch(IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
}