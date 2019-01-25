package com.hibicode.beerstore.error.exceptions;

import org.springframework.http.HttpStatus;

public class BeerNotFoundException extends BusinessException {
	
	public BeerNotFoundException() {
		super("generic-not-found", "Beer", HttpStatus.NOT_FOUND);
	}
}
