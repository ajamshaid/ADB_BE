package com.infotech.adb.api.ad;

import com.infotech.adb.dto.GDExportDTO;
import com.infotech.adb.dto.GDImportDTO;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ResourceBundle;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/gd")
@Log4j2
@Api(tags = "@GD")
public class GDAPI {

    @Autowired
    private LogRequestService logRequestService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    //**************************
    // 5.1.2.	Message 2 – Sharing of GD and Financial Information for Import with AD by PSW
    // **************************/
    @RequestMapping(value = "/import/gd-fin-information", method = RequestMethod.POST)
    public CustomResponse shareImportGDFinInfo(@RequestBody RequestParameter<GDImportDTO> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {

        GDImportDTO dto = requestBody.getData();

        //@TODO... what to do with GD Info now....yet to be decieded by AD..

        System.out.println("IN coming GD Info:"+dto.toString());

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        customResponse = ResponseUtility.successResponse("{}",AppConstants.PSWResponseCodes.OK,
                "Updated GD and financial information."
        ,requestBody, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Sharing of GD and Financial Information with AD by PSW";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }


    //**************************
    // 5.2.2.	Message 2 – Sharing of GD and Financial Information for Export with AD by PSW
    // **************************/
    @RequestMapping(value = "/export/gd-fin-information", method = RequestMethod.POST)
    public CustomResponse shareExportGDFinInfo(@RequestBody RequestParameter<GDExportDTO> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {

        GDExportDTO dto = requestBody.getData();

        //@TODO... what to do with GD Info now....yet to be decieded by AD..

        System.out.println("IN coming Export GD Info:"+dto.toString());

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        customResponse = ResponseUtility.successResponse("{}",AppConstants.PSWResponseCodes.OK,
                "Updated GD and financial information for Export."
                ,requestBody, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Sharing of GD and Financial Information For Export with AD by PSW";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

}

