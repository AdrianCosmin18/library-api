package com.example.crudjs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarsListEmptyException extends RuntimeException{

    public CarsListEmptyException(){
        super("ERROR: List of cars is empty");
    }
}
