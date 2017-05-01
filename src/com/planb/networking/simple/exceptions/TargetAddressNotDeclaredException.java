package com.planb.networking.simple.exceptions;

public class TargetAddressNotDeclaredException extends Exception {
	public TargetAddressNotDeclaredException() {
		super("요청 타겟이 지정되지 않았습니다.");
	}
}
