package com.bilev.exception.dao;

public class UnableToFindException extends Exception {
    public UnableToFindException() { super(); }
    public UnableToFindException(String message) { super(message); }
    public UnableToFindException(String message, Throwable cause) { super(message, cause); }
    public UnableToFindException(Throwable cause) { super(cause); }
}