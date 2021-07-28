package com.infotech.adb.exceptions;

import lombok.extern.log4j.Log4j2;

@SuppressWarnings("serial")

@Log4j2
public class CustomException extends Exception {

    String message = "Sorry for the inconvenience. The system will be restored shortly";

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
        log.error(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
      //  cause.printStackTrace();
        log.error(cause.getMessage());
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.setMessage(message);
       // cause.printStackTrace();
        log.error(this.message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}