package com.infotech.adb.api.psw;

import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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
        } else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_COUNTRIES)) {
            System.out.println("-------- PSW Receive Updated negative list of [COUNTRIES] Request :" + AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_COUNTRIES);
            return getOKResponse(requestBody, "Updated negative list of countries",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_COMMODITIES)) {
            System.out.println("-------- PSW Receive Updated negative list of [COMMODITIES] Request :" + AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_COMMODITIES);
            return getOKResponse(requestBody, "Updated negative list of commodities",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_SUPPLIERS)) {
            System.out.println("-------- PSW Receive Updated negative list of [SUPPLIERS] Request :" + AppConstants.PSW.METHOD_ID_UPDATE_RESTRICTED_SUPPLIERS);
            return getOKResponse(requestBody, "Updated negative list of suppliers",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_UPDATE_TRADER_ACCT_STATUS)) {
            System.out.println("-------- PSW Receive Updated  [Trader Acct Status] Request :" + AppConstants.PSW.METHOD_ID_UPDATE_TRADER_ACCT_STATUS);
            return getOKResponse(requestBody, "Updated Account Status",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_UPDATE_TRADERS_EMAIL_MOB)) {
            System.out.println("-------- PSW Receive Updated  [Trader Email and Mob] Request :" + AppConstants.PSW.METHOD_ID_UPDATE_TRADERS_EMAIL_MOB);
            return getOKResponse(requestBody, "Updated Trader Email and MObile number",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT)) {
            System.out.println("-------- PSW Receive Updated  [Update Financial transaction data for import] Request :" + AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT);
            return getOKResponse(requestBody, " Updated financial transaction data for import",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_SHARE_BDA_INFO_IMPORT)) {
            System.out.println("-------- PSW Received -->[Share of BDA Info for import] Request :" + AppConstants.PSW.METHOD_ID_SHARE_BDA_INFO_IMPORT);
            return getOKResponse(requestBody, " Updated BDA Info for import",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT)) {
            System.out.println("-------- PSW Received -->[Update Financial transaction data for Export] Request :" + AppConstants.PSW.METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT);
            return getOKResponse(requestBody, " Updated financial transaction data  for Export ",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_SHARE_BCA_INFO_EXPORT)) {
            System.out.println("-------- PSW Received -->[Share BCA Information for Export] Request :" + AppConstants.PSW.METHOD_ID_SHARE_BCA_INFO_EXPORT);
            return getOKResponse(requestBody, " Updated BCA Information  for Export ",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_SHARE_CASH_MARGIN_MESSAGE)) {
            System.out.println("-------- PSW Received -->[Sharing of Cash Margin Message by AD to PSW for Payment Mode - Open Account :" + AppConstants.PSW.METHOD_ID_SHARE_CASH_MARGIN_MESSAGE);
            return getOKResponse(requestBody, " Cash Margin Information Received ",requestBody.getMethodId());
        }else if(requestBody.getMethodId().equals(AppConstants.PSW.METHOD_ID_SHARE_COB_APPROVAL_REJECTION_MESSAGE)) {
            System.out.println("-------- PSW Received -->[Sharing of Change of Bank request approval/rejection message by AD with PSW :" + AppConstants.PSW.METHOD_ID_SHARE_COB_APPROVAL_REJECTION_MESSAGE);
            return getOKResponse(requestBody, " Change of bank request status received ",requestBody.getMethodId());
        }

        return getOKResponse(requestBody, "Method Not FOUND........ ","none");
    }

    private CustomResponse getOKResponse(RequestParameter requestBody, String message,String
                                         methodId) {
        return ResponseUtility.successResponse(null, AppConstants.PSWResponseCodes.OK, message, requestBody,true);
    }


}
