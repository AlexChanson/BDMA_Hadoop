package com.alexscode.bdma.hadoop.err;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;

@ControllerAdvice
public class StudentNotFoundAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final Object handleUserNotFoundException(CustomNotFoundException ex, WebRequest request) {
        HashMap<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("timestamp", new Date());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
