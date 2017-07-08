package com.planb.networking;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Request {
	public enum Type {
		GET, POST, DELETE;
		private final String name;

		private Type(String name) {
			this.name = name;
		}

		private Type() {
			this.name = toString();
		}

		public String getName() {
			return name;
		}
	}
	
	private Type type = null;
	private String uri = null;
	private Map<String, Object> headers = null;
	private Map<String, Object> params = null;
	private JSONObject json = null;

	private Request(RequestBuilder rb) {
		this.type = rb.type;
		this.uri = rb.uri;
		this.headers = rb.headers;
		this.params = rb.params;
		this.json = json;
	}

	public Type getType() {
		return type;
	}

	public String getUri() {
		return uri;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public JSONObject getJson() {
		return json;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public static class RequestBuilder {
		private Type type = Type.GET;
		private String uri = "";
		private Map<String, Object> headers = null;;
		private Map<String, Object> params = null;
		private JSONObject json = null;

		public RequestBuilder() {
		}

		public RequestBuilder(String uri) {
			if (uri != null) {
				this.uri = uri;
			}
		}

		public RequestBuilder setUri(String uri) {
			if (uri != null) {
				this.uri = uri;
			}
			
			return this;
		}

		public RequestBuilder setRequestType(Type type) {
			if (type != null) {
				this.type = type;
			}
			
			return this;
		}

		public RequestBuilder setHeaders(Map<String, Object> headers) {
			this.headers = headers;
			
			return this;
		}

		public RequestBuilder addHeader(String key, Object header) {
			if (this.headers == null) {
				this.headers = new HashMap<String, Object>();
			}
			this.headers.put(key, header);
			
			return this;
		}

		public RequestBuilder removeHeader(String key) {
			if (this.headers != null) {
				this.headers.remove(key);
			}
			
			return this;
		}

		public RequestBuilder setParams(Map<String, Object> params) {
			this.params = params;
			
			return this;
		}

		public RequestBuilder setParam(JSONObject json) {
			this.json = json;
			
			return this;
		}

		public RequestBuilder addParam(String key, Object param) {
			if (this.params == null) {
				this.params = new HashMap<String, Object>();
			}
			this.params.put(key, param);
			
			return this;
		}

		public RequestBuilder removeParam(String key) {
			if (this.params != null) {
				this.params.remove(key);
			}
			
			return this;
		}

		public Request build() {
			for (String n : params.keySet()) {
				if (n.equalsIgnoreCase("user-agent")) {
					return new Request(this);
				}
			}
			params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
			
			return new Request(this);
		}
	}
}
