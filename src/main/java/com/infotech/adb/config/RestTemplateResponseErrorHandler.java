package com.infotech.adb.config;

import com.infotech.adb.exceptions.NoDataFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
@Log4j2
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return (
                httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("RestTemplateResponseErrorHandler -> Internal Server Error :"+httpResponse.getRawStatusCode());
            log.error("InterServerError -> httpResponse :"+httpResponse);
            log.debug("InterServerError -> httpResponse ->body :"+httpResponse.getBody());
            // handle SERVER_ERROR
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
            if(HttpStatus.UNAUTHORIZED.equals(httpResponse.getStatusCode())){
                log.error("++Response Message :"+httpResponse.getRawStatusCode());
            }

            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoDataFoundException("No Data Found Exception");
            }
        }
    }
}