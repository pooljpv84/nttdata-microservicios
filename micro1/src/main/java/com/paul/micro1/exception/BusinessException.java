package com.paul.micro1.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String sms) { super(sms); }
}
