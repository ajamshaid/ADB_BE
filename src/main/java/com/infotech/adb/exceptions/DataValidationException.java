package com.infotech.adb.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Log4j2
public class DataValidationException extends Exception {
    String message = "Data is not valid";

    public DataValidationException() {
        super();
    }

    public DataValidationException(String message) {
        super(message);
        this.message = message;
        log.error(message);
    }

    public DataValidationException(Throwable cause) {
        super(cause);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        log.error(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
