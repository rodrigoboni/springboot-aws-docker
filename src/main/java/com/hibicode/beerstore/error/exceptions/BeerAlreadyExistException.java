package com.hibicode.beerstore.error.exceptions;

import org.springframework.http.HttpStatus;

public class BeerAlreadyExistException extends BusinessException {
    public BeerAlreadyExistException() {
        super("generic-already-exists", "Beer", HttpStatus.BAD_REQUEST);
    }
}
