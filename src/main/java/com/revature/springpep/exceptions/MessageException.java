package com.revature.springpep.exceptions;

public class MessageException extends RuntimeException {
    public MessageException(String message) {
        super(message);
    }
}