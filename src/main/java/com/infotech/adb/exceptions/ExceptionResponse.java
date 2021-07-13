package com.infotech.adb.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
/**
 * @author M.Nasir.Farooq
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private Date timestamp;
    private String status;
    private String error;
    private String message;
}
