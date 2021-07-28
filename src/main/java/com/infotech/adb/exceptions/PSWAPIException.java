package com.infotech.adb.exceptions;

import com.infotech.adb.util.ResponseUtility;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("serial")
@Data
@Log4j2
public class PSWAPIException extends Exception {
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
        log.error(cause.getMessage());
    }
}