package com.planb.networking.simple.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {
	HttpClientConfig config = null;
	
	URL url = null;
	HttpURLConnection connection = null;
	InputStream in = null;
	OutputStream out = null;
	ByteArrayOutputStream res = new ByteArrayOutputStream();
	
	public HttpClient() throws MalformedURLException {
		config = new HttpClientConfig();
		url = new URL(config.getTargetAddress() + ":" + config.getTargetPort());
	}
}
