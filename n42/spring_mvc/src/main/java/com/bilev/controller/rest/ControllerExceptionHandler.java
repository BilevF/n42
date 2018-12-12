package com.bilev.controller.rest;

import com.bilev.exception.service.OperationFailed;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(final Exception e) {

        return "Something bad happened";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public String exceptionHandler(final AccessDeniedException e) {

        return "Access is denied";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(OperationFailed.class)
    public String operationFailedHandler(final OperationFailed e) {

        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private Error processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        Error error = new Error(HttpStatus.EXPECTATION_FAILED.value(), "validation error");
        for (FieldError fieldError: fieldErrors) {
            error.getFieldErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    @Getter
    @Setter
    class Error {
        private final int status;
        private final String message;
        private Map<String, String> fieldErrors = new HashMap<>();

        Error(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }


}
