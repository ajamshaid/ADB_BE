package com.infotech.adb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.CONFLICT )
//reason = "Record already exist [Duplicate Entry]" 
public class DBConstraintViolationException extends org.springframework.dao.DataIntegrityViolationException {
	
	String message = "Record already exist";

	public DBConstraintViolationException(String message) {
		super(message);
		this.message = message;
	}

	public DBConstraintViolationException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}