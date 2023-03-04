package com.example.crudjs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarNotFoundByBrandAndModel extends RuntimeException{
    public CarNotFoundByBrandAndModel(){
        super("There is no car with this brand and model");
    }
}
