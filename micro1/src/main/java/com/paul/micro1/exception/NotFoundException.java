package com.paul.micro1.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String sms) { super(sms); }
}