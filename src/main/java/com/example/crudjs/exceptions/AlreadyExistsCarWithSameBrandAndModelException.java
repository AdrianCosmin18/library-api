package com.example.crudjs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistsCarWithSameBrandAndModelException extends RuntimeException{

    public AlreadyExistsCarWithSameBrandAndModelException(String brand, String model){
        super("ERROR: Already exists this car:" + brand + " " + model);
    }
}
