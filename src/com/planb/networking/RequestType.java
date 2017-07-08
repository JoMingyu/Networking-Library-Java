package com.planb.networking;

public enum RequestType {
	GET, POST, DELETE;
	private final String name;

	private RequestType(String name) {
		this.name = name;
	}

	private RequestType() {
		this.name = toString();
	}

	public String getName() {
		return name;
	}
}
