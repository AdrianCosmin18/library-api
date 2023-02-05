package com.example.crudjs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarNotFoundByIdException extends RuntimeException{

    public CarNotFoundByIdException(Long id){
        super("ERROR: Car not found with this id:" + id);
    }
}
