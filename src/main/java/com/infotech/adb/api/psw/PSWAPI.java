package com.infotech.adb.api.psw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/dealers/a/d/i")
@Log4j2
@Api(tags = "PSW")
public class PSWAPI {


//    @Autowired
//    private PSWClient pswClient ;

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public CustomResponse getToken(@RequestParam String userName, @RequestParam String password) {
        log.info("Test .. PSW AccessToken");

        log.info("----Going to Call PSW API Service of Token Using HTTP Client......");
        String token = "Hard Coded";// pswClient.getAuthrizationToken(userName,password);
        //String token = pswClient.getAuthrizationToken("info@ad.com","Mudassir2017");

        return   ResponseUtility.successResponse(token,null,"Token Fetched Successfully");
    }


    //4.6.	Message 6 – Sharing of Updated Information and Authorized Payment Modes by AD with PSW

    @RequestMapping(value = "/edi", method = RequestMethod.POST)
    public CustomResponse updateToPSW(@RequestBody RequestParameter<?> requestBody) throws DataValidationException {

        if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_UPDATE_INFO_AND_PM)){
            System.out.println("-------- PSW Receive ACcount Info and PM update Request :"+AppConstants.PSW.METHOD_ID_UPDATE_INFO_AND_PM);
            HashMap<String, String> data = (HashMap<String, String>) requestBody.getData();
            String iban = data.get("iban");
            if (AppUtility.isEmpty(iban)) {
                throw new DataValidationException("IBAN Missing");
            }
            if (iban.startsWith("PK")){
                return getOKResponse(requestBody, "Updated authorized payment modes",requestBody.getMethodId());
            }else{
                throw new DataValidationException("In-correct IBAN");
            }
        } else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_UPDATE_INFO_AND_PM)) {
            System.out.println("-------- PSW Receive ACcount Info and PM update Request :" + AppConstants.PSW.METHOD_ID_UPDATE_INFO_AND_PM);
        }

        return getOKResponse(requestBody, "ALL Good ","none");
    }











    @RequestMapping(value = "/update/negative/countries", method = RequestMethod.POST)
    public CustomResponse getNegativeCountriesList(HttpServletRequest request,
                                            @RequestBody RequestParameter requestBody) {

        return getOKResponse(requestBody, messageBundle.getString("negative.countries.shared"),"none");
    }

    @RequestMapping(value = "/update/negative/commodities", method = RequestMethod.POST)
    public CustomResponse getNegativeCommoditiesList(HttpServletRequest request,
                                                   @RequestBody RequestParameter requestBody) {

        return getOKResponse(requestBody, messageBundle.getString("negative.commodities.shared"),"none");
    }

    @RequestMapping(value = "/update/negative/suppliers", method = RequestMethod.POST)
    public CustomResponse getNegativeSuppliersList(HttpServletRequest request,
                                                     @RequestBody RequestParameter requestBody) {

        return getOKResponse(requestBody, messageBundle.getString("negative.suppliers.shared"),"none");
    }

    @RequestMapping(value = "/bca/information", method = RequestMethod.POST)
    public CustomResponse bcaInformation(HttpServletRequest request,
                                                   @RequestBody RequestParameter requestBody) {

        return getOKResponse(requestBody, messageBundle.getString("bca.information.updated"),"none");
    }


    @RequestMapping(value = "/financial/transaction", method = RequestMethod.POST)
    public CustomResponse financialTransaction(HttpServletRequest request,
                                         @RequestBody RequestParameter requestBody) {

        return getOKResponse(requestBody, messageBundle.getString("negative.suppliers.shared"),"none");
    }

    //    5.2.3. Message 3 – Sharing of BCA Information

    private CustomResponse getOKResponse(RequestParameter requestBody, String message,String
                                         methodId) {
        return ResponseUtility.successResponse(null, AppConstants.PSWResponseCodes.OK, message, requestBody,true);
    }

    private void saveLogRequest(String messageName, String messageType, RequestParameter requestBody, ZonedDateTime requestTime, ResponseUtility.APIResponse responseBody) throws JsonProcessingException {
        LogRequest logRequest = new LogRequest();
        logRequest.setMsgIdentifier(messageName);
        logRequest.setRequestMethod(messageType);
        logRequest.setRequestPayload(requestBody.toJson());
        logRequest.setResponsePayload(responseBody.toJson());
        logRequest.setRequestTime(requestTime);
        logRequest.setResponseTime(ZonedDateTime.now());
        logRequest.setCreatedOn(ZonedDateTime.now());
        logRequest.setResponseCode(responseBody.getResponseCode());
        logRequest.setResponseMessage(responseBody.getMessage().getDescription());
      //  logRequestService.createLogRequest(logRequest);
    }
}
