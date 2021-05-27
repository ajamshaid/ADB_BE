package com.infotech.adb.api.ad;

import com.infotech.adb.dto.ChangeBankRequestDTO;
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
@RequestMapping("/cob")
@Log4j2
@Api(tags = "@ChangeOfBankAPI")
public class ChangeOfBankAPI_ToDel {

    @Autowired
    private LogRequestService logRequestService;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    //**************************
    // 7.1.2.	Message 1 – Sharing of Change of Bank request with AD
    // **************************/
    @RequestMapping(value = "/share/cob-request", method = RequestMethod.POST)
    public CustomResponse shareChangeOfBankRequest(@RequestBody RequestParameter<ChangeBankRequestDTO> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException {

        ChangeBankRequestDTO dto = requestBody.getData();

        //@TODO... what to do with COB Request now....yet to be decided by AD..

        System.out.println("IN coming Change Bank Request:"+dto.toString());

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        customResponse = ResponseUtility.successResponse("{}",AppConstants.PSWResponseCodes.OK,
                "Change of bank request received."
        ,requestBody, false);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
        String logMessage = "Message 1 – Sharing of Change of Bank request with AD";

        logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

   //**************************
   // 7.1.5.	Message 1 – Sharing of Change of Bank request approval/rejection message by other selected AD with PSW
   // **************************/
   @RequestMapping(value = "/share/cob-approval", method = RequestMethod.POST)
   public CustomResponse shareChangeOfBankRequestApprovalRejectionMessage(@RequestBody RequestParameter<ChangeBankRequestDTO> requestBody)
           throws CustomException, DataValidationException, NoDataFoundException {

       ChangeBankRequestDTO dto = requestBody.getData();

       //@TODO... what to do with COB Approval/Rejection Request now....yet to be decided by AD..

       System.out.println("IN coming Change Bank Approval/Rejection Request:"+dto.toString());

       ZonedDateTime requestTime = ZonedDateTime.now();
       CustomResponse customResponse = null;

       customResponse = ResponseUtility.successResponse("{}",AppConstants.PSWResponseCodes.OK,
               "Change of bank request status acknowledged"
               ,requestBody, false);

       ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
       String logMessage = "7.1.5.\tMessage 1 – Sharing of Change of Bank request approval/rejection message by other selected AD with PSW ";

       logRequestService.saveLogRequest(logMessage, RequestMethod.POST.name(), requestBody, requestTime, responseBody);
       return customResponse;
   }
}

