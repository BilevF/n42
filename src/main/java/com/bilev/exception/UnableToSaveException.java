package com.bilev.exception;

public class UnableToSaveException extends RuntimeException  {
    public UnableToSaveException() { super(); }
    public UnableToSaveException(String message) { super(message); }
    public UnableToSaveException(String message, Throwable cause) { super(message, cause); }
    public UnableToSaveException(Throwable cause) { super(cause); }
}