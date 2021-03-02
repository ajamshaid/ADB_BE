package com.infotech.adb.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataValidationException extends Exception {
    String message = "Data is not valid";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DataValidationException() {
        super();
    }

    public DataValidationException(String message) {
        super(message);
        this.message = message;
        logger.error(message);
    }

    public DataValidationException(Throwable cause) {
        super(cause);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        logger.error(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
