package com.planb.networking;

import java.io.IOException;
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
	private OutputStream out = null;
	private OutputStreamWriter wr = null;
	
	public HttpClient(String targetAddress, int port, int readTimeout, int connectTimeout) {
		// Constructor with address, port, timeouts
		
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}

		this.targetAddress = port == 80 ? targetAddress : targetAddress + ":" + port;
		this.readTimeout = readTimeout;
		this.connectTimeout = connectTimeout;
	}
	
	public HttpClient(String targetAddress, int port) {
		// Constructor with address, port
		
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = port == 80 ? targetAddress : targetAddress + ":" + port;
	}
	
	public HttpClient(String targetAddress) {
		// Constructor with address
		
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = targetAddress;
	}
	
	public HttpClient(Config config) {
		// Constructor with config
		
		this.targetAddress = config.getTargetAddress();
		this.readTimeout = config.getReadTimeout();
		this.connectTimeout = config.getConnectTimeout();
	}
	
	public String getTargetAddress() {
		return targetAddress;
	}
	public void setTargetAddress(String targetAddress, int port) {
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = port == 80 ? targetAddress : targetAddress + ":" + port;
	}
	
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Response requestUri(Request request){
		return request(request);
	}
	
	@Deprecated
	public Response get(String uri, Map<String, Object> headers, Map<String, Object> params) {
		return request(new Request.RequestBuilder(uri).setHeaders(headers).setParams(params).setRequestType(Request.Type.GET).build());
	}
	
	@Deprecated
	public Response post(String uri, Map<String, Object> headers, Map<String, Object> params) {
		return request(new Request.RequestBuilder(uri).setHeaders(headers).setParams(params).setRequestType(Request.Type.POST).build());
	}
	
	@Deprecated
	public Response delete(String uri, Map<String, Object> headers, Map<String, Object> params) {
		return request(new Request.RequestBuilder(uri).setHeaders(headers).setParams(params).setRequestType(Request.Type.DELETE).build());
	}

	@Deprecated
	public Response post(String uri, Map<String, Object> headers, JSONObject requestObject) {
		// POST request with JSON data
		
		String requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
		// Request address with uri
		
		try {
			connect(requestAddress, new Request.RequestBuilder(uri).setHeaders(headers).setParam(requestObject).setRequestType(Request.Type.POST).build());
			
			wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(requestObject.toString());
			wr.flush();
			
			return NetworkingHelper.getResponse(connection);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private final Response request(Request request) {
		// all Request Include
		
		String requestAddress = null;
		if(request.getType() != Request.Type.POST && request.getParams() != null && request.getParams().size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, request.getUri(), request.getParams());
			// Request address with uri and parameter
		} else if(request.getType() != Request.Type.POST && (request.getParams() == null || request.getParams().size() == 0)) {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, request.getUri());
			// Request address with uri
		}
		
		try {
			connect(requestAddress, request);
			
			if(request.getType() == Request.Type.POST && request.getParams() != null && request.getParams().size() > 0) {
				out = connection.getOutputStream();
				out.write(NetworkingHelper.createParamBytes(request.getParams()));
				// Send byte[] data if body data is exists
				out.flush();
			}
			if(request.getType() == Request.Type.POST && request.getJson() != null && request.getJson().length() > 0){
				wr = new OutputStreamWriter(connection.getOutputStream());
				wr.write(request.getJson().toString());
				wr.flush();
			}
			
			return NetworkingHelper.getResponse(connection);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private final void connect(String requestAddress, Request request) throws IOException {
		url = new URL(requestAddress);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(request.getType().getName());
		connection.setDoOutput(true);
		// Enable do output
		connection.setReadTimeout(this.readTimeout);
		connection.setConnectTimeout(this.connectTimeout);
		
		if(request.getHeaders() != null && request.getHeaders().size() > 0) {
			for(Map.Entry<String, Object> ent : request.getHeaders().entrySet()) {
				connection.setRequestProperty(ent.getKey(), (String) ent.getValue());
			}
		}
		
		connection.connect();
	}
}