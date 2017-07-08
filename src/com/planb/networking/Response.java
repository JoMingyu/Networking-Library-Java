package com.planb.networking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
	private Map<String, List<String>> responseHeader = new HashMap<String, List<String>>();
	private String responseBody;
	private int responseCode;

	public void setResponseHeader(Map<String, List<String>> responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	public Map<String, List<String>> getResponseHeader() {
		return responseHeader;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public int getResponseCode() {
		return responseCode;
	}
}
