package com.alexscode.bdma.hadoop.err;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;

@ControllerAdvice
public class Custom500Advice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Custom500Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Object handleUserNotFoundException(CustomNotFoundException ex, WebRequest request) {
        HashMap<String, Object> error = new HashMap<>();
        error.put("Message", ex.getMessage());
        error.put("Timestamp", new Date());
        error.put("code", "500");
        error.put("status", "INTERNAL SERVER ERROR");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
