//package com.infotech.adb.exceptions;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.Date;
//
///**
// * The type Global exception handler.
// *
// * @author Nasir Farooq
// */
//@Log4j2
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//  @Autowired
//  private MessageSource messageSource;
//
//  /**
//   * Globle excpetion handler response entity.
//   *
//   * @param ex the ex
//   * @param request the request
//   * @return the response entity
//   */
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
//    ExceptionResponse errorDetails =
//            new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
//    ex.printStackTrace();
//    log.error("Date {} , Exception Message {}",new Date(),ex.getMessage());
//    log.error("Exception Message {}",ex.getCause());
//    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//  }
//}
