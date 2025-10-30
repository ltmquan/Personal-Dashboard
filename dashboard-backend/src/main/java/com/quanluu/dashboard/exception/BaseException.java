package com.quanluu.dashboard.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
	
	private final String entityType;
	
	private final String errorCode;
	
	public BaseException(String msg, String entityType, String errorCode) {
		super(msg);
		
		this.entityType = entityType;
		this.errorCode = errorCode;
	}

}
