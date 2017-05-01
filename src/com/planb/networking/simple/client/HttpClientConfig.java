package com.planb.networking.simple.client;

final class HttpClientConfig {
	private String targetAddress = null;
	private int targetPort = 80;
	
	private int readTimeout = 3000;
	private int connectTimeout = 3000;
	
	void setTagetAddress(String address) {
		this.targetAddress = address;
	}
	
	String getTargetAddress() {
		return targetAddress;
	}
	
	void setTargetPort(int port) {
		this.targetPort = port;
	}
	
	int getTargetPort() {
		return targetPort;
	}
	
	void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	int getReadTimeout() {
		return readTimeout;
	}
	
	void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	int getConnectTimeout() {
		return connectTimeout;
	}
}
