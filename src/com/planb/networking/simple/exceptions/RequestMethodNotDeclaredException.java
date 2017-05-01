package com.planb.networking.simple.exceptions;

public class RequestMethodNotDeclaredException extends Exception {
	public RequestMethodNotDeclaredException() {
		super("요청 메소드가 지정되지 않았습니다.");
	}
}
