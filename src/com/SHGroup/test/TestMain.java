package com.SHGroup.test;

import com.planb.networking.HttpClient;
import com.planb.networking.Request;
import com.planb.networking.Response;

public class TestMain {
	public static void main(String[] args) {
		try {
			HttpClient htc = new HttpClient("https://www.google.com/////////");
			Response res = htc.requestUri(new Request.RequestBuilder(
					"search").addParam("q", "안녕").addParam("oq", "안녕").build());
			//System.out.println(res.getResponseBody());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			HttpClient htc = new HttpClient("http://kshgroup.kr/////////");
			Response res = htc.requestUri(new Request.RequestBuilder().build());
			System.out.println(res.getResponseBody());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
