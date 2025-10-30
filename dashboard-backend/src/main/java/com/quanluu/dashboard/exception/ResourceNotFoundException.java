package com.quanluu.dashboard.exception;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message, String entityType,  String errorCode) {
        super(message, entityType, errorCode);
    }
}
