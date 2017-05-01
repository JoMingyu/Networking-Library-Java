package com.planb.networking.simple.client;

public class HttpClientConfig {
	private static String targetAddress = null;
	private static int targetPort = 80;
	private static String requestMethod = null;
	
	private static int readTimeout = 0;
	private static int connectTimeout = 0;
	
	public static void setTagetAddress(String address) {
		HttpClientConfig.targetAddress = address;
	}
	
	public static String getTargetAddress() {
		return targetAddress;
	}
	
	public static void setTargetPort(int port) {
		HttpClientConfig.targetPort = port;
	}
	
	public static int getTargetPort() {
		return targetPort;
	}
	
	public static void setRequestMethod(String method) {
		HttpClientConfig.requestMethod = method;
	}
	
	public static String getRequestMethod() {
		return requestMethod;
	}
	
	public static void setReadTimeout(int readTimeout) {
		HttpClientConfig.readTimeout = readTimeout;
	}
	
	public static int getReadTimeout() {
		return readTimeout;
	}
	
	public static void setConnectTimeout(int connectTimeout) {
		HttpClientConfig.connectTimeout = connectTimeout;
	}
	
	public static int getConnectTimeout() {
		return connectTimeout;
	}
}
