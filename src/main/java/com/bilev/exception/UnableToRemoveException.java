package com.bilev.exception;

public class UnableToRemoveException extends Exception {
    public UnableToRemoveException() { super(); }
    public UnableToRemoveException(String message) { super(message); }
    public UnableToRemoveException(String message, Throwable cause) { super(message, cause); }
    public UnableToRemoveException(Throwable cause) { super(cause); }
}