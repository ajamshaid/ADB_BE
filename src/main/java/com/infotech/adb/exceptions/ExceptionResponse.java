///**
// *
// */
//package com.infotech.adb.exceptions;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.http.HttpStatus;
//
//import java.util.Date;
//
///**
// * @author arslan.ahmad
// *
// */
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class ExceptionResponse extends APIResponse {
//
//	private Date timestamp;
//	private String details;
//
//	public ExceptionResponse(Date date, String message, String details, HttpStatus status) {
//		super(true, message, status);
//		this.timestamp = date;
//		this.details = details;
//	}
//
//}
