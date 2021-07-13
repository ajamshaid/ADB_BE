package com.infotech.adb.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Log4j2
@RestControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex) {
        return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> handleAllException(CustomException ex) {
        return getResponseEntity(ex,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PSWAPIException.class)
    public final ResponseEntity<Object> handleAllException(PSWAPIException ex) {
        return getResponseEntity(ex,HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(DataValidationException.class)
    public final ResponseEntity<Object> handleException(DataValidationException ex) {
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DBConstraintViolationException.class)
    public final ResponseEntity<Object> handleException(DBConstraintViolationException ex) {
        return getResponseEntity(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public final ResponseEntity<Object> handleException(NoDataFoundException ex) {
        return getResponseEntity(ex, HttpStatus.NO_CONTENT);
    }

//    @ExceptionHandler(UserAlreadyLoggedInException.class)
//    public final ResponseEntity<Object> handleException(UserAlreadyLoggedInException ex) {
//        return getResponseEntity(ex, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
//    }

    private ResponseEntity<Object> getResponseEntity(Exception ex, HttpStatus status) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ""+status.value(),
                status.getReasonPhrase(), ex.getMessage());
        ex.printStackTrace();
        log.error("Date {} , Exception Message {}", new Date(), ex.getMessage());
        log.error("Exception Message {}", ex.getCause());
        return new ResponseEntity<Object>(exceptionResponse, status);
    }
}
