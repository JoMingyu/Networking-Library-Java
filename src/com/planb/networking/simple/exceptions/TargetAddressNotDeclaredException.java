package com.planb.networking.simple.exceptions;

public class TargetAddressNotDeclaredException extends Exception {
	public TargetAddressNotDeclaredException() {
		super("요청 주소가 지정되지 않았습니다.");
	}
}
