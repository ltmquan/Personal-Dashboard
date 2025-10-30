package com.quanluu.dashboard.exception;

public class BadRequestException extends BaseException {
	
    public BadRequestException(String message, String entityType, String errorCode) {
        super(message, entityType, errorCode);
    }
}
