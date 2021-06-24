package com.infotech.adb.api.fe;


import com.infotech.adb.dto.AccountPMDTO;
import com.infotech.adb.dto.BankNegativeCountriesDTO;
import com.infotech.adb.dto.TraderProfileStatusDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.service.PSWService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/psw")
@Log4j2
@Api(tags = "@psw")
public class PSWRequestAPI {

    @Autowired
    private PSWService pswService;




    @RequestMapping(value = "/update-pm", method = RequestMethod.POST)
    public CustomResponse updatePaymentModes(@RequestBody AccountPMDTO dto)
            throws CustomException, DataValidationException, NoDataFoundException {

        log.debug("updatePaymentModes::IN Coming Request Data is:"+dto.toString());

         ResponseUtility.APIResponse response =  pswService.shareUpdatedAuthPMs(dto);

        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        if(AppUtility.isEmpty(response)){ //if Un-successful Response
            ResponseUtility.exceptionResponse(new CustomException("Resquest update Failed.."),"");
        }else{
            customResponse = ResponseUtility.successResponse("{}",
                    response.getMessage().getCode(),
                response.getMessage().getDescription(), null,false);
        }
        return customResponse;
    }

    @RequestMapping(value = "/activate-tp", method = RequestMethod.POST)
    public CustomResponse updateTraderProfileStatus(@RequestBody TraderProfileStatusDTO dto)
            throws CustomException, DataValidationException, NoDataFoundException {

        log.debug("updateTraderProfileStatus:: IN Coming Request Data is:"+dto.toString());

        ResponseUtility.APIResponse response ;

        response =  pswService.updateTraderProfileStatus(dto);
        response = ResponseUtility.buildAPIResponse(null, HttpStatus.OK.toString(),new ResponseUtility.Message("200","Hello World"));


        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        if(AppUtility.isEmpty(response)){ //if Un-successful Response

            ResponseUtility.exceptionResponse(new CustomException("Resquest update Failed.."),"");

        }else{
            customResponse = ResponseUtility.successResponse("{}", AppConstants.PSWResponseCodes.OK,
                    "Updated authorized payment modes", null,false);
        }
        return customResponse;
    }


    @RequestMapping(value = "/update/neg-countries", method = RequestMethod.POST)
    public CustomResponse updateNegativeCountries(@RequestBody BankNegativeCountriesDTO dto)
            throws CustomException, DataValidationException, NoDataFoundException {

        log.debug("updateNegativeCountries:: IN Coming Request Data is:"+dto.toString());

        ResponseUtility.APIResponse response ;

        response =  pswService.shareNegativeListOfCountries(dto);
  //      response = ResponseUtility.buildAPIResponse(null, HttpStatus.OK.toString(),new ResponseUtility.Message("200","Hello World"));


        ZonedDateTime requestTime = ZonedDateTime.now();
        CustomResponse customResponse = null;

        if(AppUtility.isEmpty(response)){ //if Un-successful Response

            ResponseUtility.exceptionResponse(new CustomException("Resquest update Failed.."),"");

        }else{
            customResponse = ResponseUtility.successResponse("{}", AppConstants.PSWResponseCodes.OK,
                    "Negative List Of Countries Shared", null,false);
        }
        return customResponse;
    }
}
