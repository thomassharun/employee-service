package com.tus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class EmployeeExistenceException extends RuntimeException{
	
	public EmployeeExistenceException(String message) {
		super(message);
	}

}
