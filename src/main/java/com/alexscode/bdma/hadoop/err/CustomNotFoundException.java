package com.alexscode.bdma.hadoop.err;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomNotFoundException extends Exception{
    public CustomNotFoundException() {
    }

    public CustomNotFoundException(String message) {
        super(message);
    }
}
