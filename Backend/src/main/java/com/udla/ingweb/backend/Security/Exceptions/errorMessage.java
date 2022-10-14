package com.udla.ingweb.backend.Security.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class errorMessage extends Exception {

    public errorMessage(String message){
        super(message);
    }
}
