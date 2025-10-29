package com.paul.micro2.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String sms) { super(sms); }
}