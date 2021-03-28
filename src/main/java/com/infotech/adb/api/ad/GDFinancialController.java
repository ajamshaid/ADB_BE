package com.infotech.adb.api.ad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.ChangeBankDTO;
import com.infotech.adb.dto.GDFinancialDTO;
import com.infotech.adb.dto.IBANVerificationRequest;
import com.infotech.adb.dto.RequestParameter;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.ChangeBank;
import com.infotech.adb.model.entity.GDFinancial;
import com.infotech.adb.model.entity.LogRequest;
import com.infotech.adb.service.BankChangeService;
import com.infotech.adb.service.GDFinancialService;
import com.infotech.adb.service.LogRequestService;
import com.infotech.adb.util.*;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/ad/gdf")
@Log4j2
@Api(tags = "GD")
public class GDFinancialController {

    @Autowired
    private LogRequestService logRequestService;

    @Autowired
    private GDFinancialService gdFinancialService;

    @Autowired
    private BankChangeService bankChangeService;


    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/import/information", method = RequestMethod.POST)
    public CustomResponse gdAndFinancialImportInformation(HttpServletRequest request,
                                                          @RequestBody RequestParameter<GDFinancialDTO> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        if (AppUtility.isEmpty(requestBody.getMessageId())) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }

        try {
            GDFinancialDTO requestData = requestBody.getData();
            requestData.setGdType(GDFinancialDTO.GD_TYPE_IMPORT);
            GDFinancial gdFinancial = gdFinancialService.getGDFinancialByGDNumber(requestData.getGDNumber(), requestData.getGdType());
            if (gdFinancial == null) {
                gdFinancialService.createGDFinancial(requestData.convertToEntity());
            }
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, "GDNumber");
        }

        CustomResponse customResponse = ResponseUtility.createdResponse(null, AppConstants.PSWResponseCodes.OK,
                messageBundle.getString("gd.financial.updated"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("GD and financial information import", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }


    @RequestMapping(value = "/get/import/information", method = RequestMethod.POST)
    public CustomResponse getGdAndFinancialImportInformation(HttpServletRequest request,
                                                             @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws NoDataFoundException {

        GDFinancial gdFinancial = gdFinancialService.getGDFinancialByGDNumber(requestBody.getData().getGdNumber(), GDFinancialDTO.GD_TYPE_IMPORT);
        GDFinancialDTO gdFinancialDTO = JsonUtils.jsonToObject(gdFinancial.getGdfObjectJson(), GDFinancialDTO.class);
        CustomResponse customResponse = ResponseUtility.createdResponse(gdFinancialDTO, AppConstants.PSWResponseCodes.OK,
                gdFinancial == null ? messageBundle.getString("account.details.not.shared") :
                        messageBundle.getString("account.details.shared"), requestBody);

//        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
//
//        saveLogRequest("MSG:5.1.2 Get GD and financial information", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/export/information", method = RequestMethod.POST)
    public CustomResponse gdAndFinancialExportInformation(HttpServletRequest request,
                                                          @RequestBody RequestParameter<GDFinancialDTO> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        if (AppUtility.isEmpty(requestBody.getMessageId())) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }

        try {
            GDFinancialDTO requestData = requestBody.getData();
            requestData.setGdType(GDFinancialDTO.GD_TYPE_EXPORT);
            GDFinancial gdFinancial = gdFinancialService.getGDFinancialByGDNumber(requestData.getGDNumber(), requestData.getGdType());
            if (gdFinancial == null) {
                gdFinancialService.createGDFinancial(requestData.convertToEntity());
            }
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, "GDNumber");
        }

        CustomResponse customResponse = ResponseUtility.createdResponse(null, AppConstants.PSWResponseCodes.OK,
                messageBundle.getString("gd.financial.updated"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("GD and financial information export", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }


    @RequestMapping(value = "/get/export/information", method = RequestMethod.POST)
    public CustomResponse getGdAndFinancialInformation(HttpServletRequest request,
                                                       @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws NoDataFoundException {

//        ZonedDateTime requestTime = ZonedDateTime.now();

        GDFinancial gdFinancial = gdFinancialService.getGDFinancialByGDNumber(requestBody.getData().getGdNumber(), GDFinancialDTO.GD_TYPE_EXPORT);
        GDFinancialDTO gdFinancialDTO = JsonUtils.jsonToObject(gdFinancial.getGdfObjectJson(), GDFinancialDTO.class);
        CustomResponse customResponse = ResponseUtility.createdResponse(gdFinancialDTO, AppConstants.PSWResponseCodes.OK,
                gdFinancial == null ? messageBundle.getString("account.details.not.shared") :
                        messageBundle.getString("account.details.shared"), requestBody);

//        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();
//
//        saveLogRequest("MSG:5.2.2 Get GD and financial information", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/change/bank", method = RequestMethod.POST)
    public CustomResponse verifyAccount(HttpServletRequest request,
                                        @RequestBody RequestParameter<ChangeBankDTO> requestBody)
            throws CustomException, DataValidationException, NoDataFoundException, JsonProcessingException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        // TODO validate request common parameters
        if (AppUtility.isEmpty(requestBody.getMessageId())) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }

        try {
            bankChangeService.createChangeBank(requestBody.getData().convertToEntity());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }

        CustomResponse customResponse = ResponseUtility.createdResponse(null, AppConstants.PSWResponseCodes.OK,
                messageBundle.getString("change.bank.successful"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

        saveLogRequest("MSG:5.4 Change bank", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/get/change/bank", method = RequestMethod.POST)
    public CustomResponse getChangeBank(HttpServletRequest request,
                                        @RequestBody RequestParameter<ChangeBankDTO> requestBody)
            throws NoDataFoundException {

        ZonedDateTime requestTime = ZonedDateTime.now();
        // MethodId is exporterNTN
        ChangeBank changeBank = bankChangeService.getChangeBankByExporterNTN(requestBody.getMethodId());

        CustomResponse customResponse = ResponseUtility.buildResponseObject(changeBank, new ChangeBankDTO(), 200,
                changeBank == null ? messageBundle.getString("change.bank.not.shared") :
                        messageBundle.getString("change.bank.shared"), requestBody);

        ResponseUtility.APIResponse responseBody = (ResponseUtility.APIResponse) customResponse.getBody();

//        saveLogRequest("MSG:5.4 Get Change bank", RequestMethod.POST.name(), requestBody, requestTime, responseBody);
        return customResponse;
    }

    @RequestMapping(value = "/update/bca/information", method = RequestMethod.POST)
    public CustomResponse updateBCAInformation(HttpServletRequest request,
                                                      @RequestBody RequestParameter<IBANVerificationRequest> requestBody)
            throws NoDataFoundException, JsonProcessingException {

        HttpClientTest.updateNegativeSupplier();

        ZonedDateTime requestTime = ZonedDateTime.now();

        String bcsInformationJson = "{\n" +
                "  \"messageId\": \"a1374655-5eb8-4a0e-9eb5-989521cd1ca8\",\n" +
                "  \"timestamp\": \"20200925183412\",\n" +
                "  \"senderId\": \"SCBL\",\n" +
                "  \"receiverId\": \"PSW\",\n" +
                "  \"methodId\": \"1526\",\n" +
                "  \"data\": {\n" +
                "    \"iban\": \"PK36SCBL0010001123456005\",\n" +
                "    \"exporterNtn\": \"170047\",\n" +
                "    \"exporterName\": \"Schlumberger Seaco Inc.\",\n" +
                "    \"bcaDate\": \"13102020\",\n" +
                "    \"modeOfPayment\": \"Advance Payment\",\n" +
                "    \"uniqueNumber\": \"155724546\",\n" +
                "    \"bcaEventName\": \"BCA Amount Feeding\",\n" +
                "    \"eventDate\": \"20201020\",\n" +
                "    \"bcaInformation\": {\n" +
                "      \"runningSerialNumber\": \"4353569\",\n" +
                "      \"swiftReference\": \"33256445356783245323\",\n" +
                "      \"billNumber\": \"324512\",\n" +
                "      \"billDated\": \"20201023\",\n" +
                "      \"invoiceNumber\": \"002124\",\n" +
                "      \"invoiceDate\": \"20201023\",\n" +
                "      \"amount\": 19234.242,\n" +
                "      \"deductions\": {\n" +
                "        \"foreignBankChargesFcy\": 1234.4542,\n" +
                "        \"agentCommissionFcy\": 567.042,\n" +
                "        \"withholdingTaxPkr\": 6230.3421,\n" +
                "        \"edsPkr\": 2435.6443\n" +
                "      },\n" +
                "      \"netAmountRealized\": {\n" +
                "        \"bcaFc\": 2567.8753,\n" +
                "        \"fcyExchangeRate\": 167.864,\n" +
                "        \"bcaPkr\": 2567.8753,\n" +
                "        \"dateOfRealized\": \"20201021\",\n" +
                "        \"adjustmentFromSpecialFcyAmount\": 1567.8753,\n" +
                "        \"currency\": \"USD\",\n" +
                "        \"foreignBankCharges\": 510,\n" +
                "        \"amountRealized\": 2567.8753,\n" +
                "        \"balance\": 3435.6764,\n" +
                "        \"allowedDiscount\": 530.345,\n" +
                "        \"allowedDiscountPercentage\": 5\n" +
                "      },\n" +
                "      \"remarks\": \"Passed\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"signature\": \"82045ede93efbbcbea55da67c6655e9b\"\n" +
                "}";

        GDFinancialDTO accountDetail = JsonUtils.jsonToObject(bcsInformationJson,GDFinancialDTO.class);

        RequestParameter pswRequestPayload = requestBody.newRequestParameter();
        pswRequestPayload.setData(accountDetail);

        CustomResponse customResponse2 = requestPSWAPI("/bca/information", request, pswRequestPayload);

//        saveLogRequest("MSG9: Update Negative Supplier", RequestMethod.POST.name(), requestBody, requestTime,(ResponseUtility.APIResponse) customResponse2.getBody());
        saveLogRequest("Share BCA information with PSW by AD", RequestMethod.POST.name(), pswRequestPayload, requestTime,(ResponseUtility.APIResponse) customResponse2.getBody());
        return customResponse2;
    }

    private CustomResponse requestPSWAPI(String uri, HttpServletRequest request, RequestParameter requestParameter) {

        ResponseUtility.APIResponse responseBodyPSW = HTTPClientUtils.postRequest(uri, request.getHeader("Authorization"),
                requestParameter);
        CustomResponse customResponse2 = CustomResponse.status(HttpStatus.CREATED).body(responseBodyPSW);
        return customResponse2;
    }

//    /bca/information

    private void saveLogRequest(String messageName, String messageType, RequestParameter requestBody,
                                ZonedDateTime requestTime, ResponseUtility.APIResponse responseBody) throws JsonProcessingException {
        LogRequest logRequest = new LogRequest();
        logRequest.setSenderId(requestBody.getSenderId());
        logRequest.setReceiverId(requestBody.getReceiverId());
        logRequest.setMsgIdentifier(messageName);
        logRequest.setRequestMethod(messageType);
        logRequest.setRequestPayload(requestBody.toJson());
        logRequest.setResponsePayload(responseBody.toJson());
        logRequest.setRequestTime(requestTime);
        logRequest.setResponseTime(ZonedDateTime.now());
        logRequest.setCreatedOn(ZonedDateTime.now());
        logRequest.setResponseCode(responseBody.getMessage().getCode());
        logRequest.setResponseMessage(responseBody.getMessage().getDescription());
        logRequestService.createLogRequest(logRequest);
    }
}
