package com.planb.networking.simple.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.planb.networking.simple.exceptions.RequestMethodNotDeclaredException;
import com.planb.networking.simple.exceptions.TargetAddressNotDeclaredException;

public class HttpClient {
	HttpClientConfig config = null;
	
	URL url = null;
	HttpURLConnection connection = null;
	InputStream in = null;
	OutputStream out = null;
	ByteArrayOutputStream res = new ByteArrayOutputStream();
	
	public HttpClient() throws IOException, TargetAddressNotDeclaredException, RequestMethodNotDeclaredException {
		config = new HttpClientConfig();
		if(config.getTargetAddress() == null) {
			throw new TargetAddressNotDeclaredException();
		}
		url = new URL(config.getTargetAddress() + ":" + config.getTargetPort());
		connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(config.getReadTimeout());
		connection.setConnectTimeout(config.getConnectTimeout());
	}
}
