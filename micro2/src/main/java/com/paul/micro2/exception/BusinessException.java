package com.paul.micro2.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String sms) { super(sms); }
}
