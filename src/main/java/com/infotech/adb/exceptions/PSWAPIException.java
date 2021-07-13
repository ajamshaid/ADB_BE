package com.infotech.adb.exceptions;

import com.infotech.adb.util.ResponseUtility;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@Data
public class PSWAPIException extends Exception {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ResponseUtility.APIResponse apiResponse;

    public PSWAPIException() {
        super();
    }

    public PSWAPIException(ResponseUtility.APIResponse apiResponse) {
        super(apiResponse.getMessage().getDescription());
        this.apiResponse= apiResponse;
   //     this.logger.error(apiResponse.getMessage().getDescription());
    }
    public PSWAPIException(Throwable cause) {
        super(cause);
      //  cause.printStackTrace();
        this.logger.error(cause.getMessage());
    }
}