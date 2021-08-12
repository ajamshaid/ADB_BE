package com.infotech.adb.api;


import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.service.PSWService;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/psw")
@Log4j2
public class PushToPSWAPI {

    @Autowired
    private PSWService pswService;

    @RequestMapping(value = "/update-pm", method = RequestMethod.POST)
    public CustomResponse updatePaymentModes(@RequestBody AccountPMDTO dto)
            throws PSWAPIException,  NoDataFoundException {
        log.debug("updatePaymentModes:: IN Coming Request Data is:" + dto.toString());
        ResponseUtility.APIResponse response = pswService.shareUpdatedAuthPMs(dto);
        return ResponseUtility.translatePSWAPIResponse(response);
    }

    @RequestMapping(value = "/activate-tp", method = RequestMethod.POST)
    public CustomResponse updateTraderProfileStatus(@RequestBody TraderProfileStatusDTO dto)
            throws PSWAPIException,  NoDataFoundException {
        log.debug("updateTraderProfileStatus:: IN Coming Request Data is:" + dto.toString());
        ResponseUtility.APIResponse response = pswService.updateTraderProfileStatus(dto);
        return ResponseUtility.translatePSWAPIResponse(response);
    }

    @RequestMapping(value = "/update/neg-countries", method = RequestMethod.POST)
    public CustomResponse updateNegativeCountries(@RequestBody BankNegativeCountriesDTO dto)
            throws PSWAPIException, NoDataFoundException {

        log.debug("updateNegativeCountries:: IN Coming Request Data is:" + dto.toString());
        ResponseUtility.APIResponse response;
        response = pswService.shareNegativeListOfCountries(dto);
        return ResponseUtility.translatePSWAPIResponse(response);
    }

    @RequestMapping(value = "/update/neg-commodities", method = RequestMethod.POST)
    public CustomResponse updateNegativeCommodities(@RequestBody BankNegativeCommoditiesDTO dto)
            throws PSWAPIException, NoDataFoundException {
        log.debug("updateNegativeCommodities:: IN Coming Request Data is:" + dto.toString());
        ResponseUtility.APIResponse response;
        response = pswService.shareNegativeListOfCommodities(dto);
        return ResponseUtility.translatePSWAPIResponse(response);
    }

    @RequestMapping(value = "/update/neg-suppliers", method = RequestMethod.POST)
    public CustomResponse updateNegativeSuppliers(@RequestBody BankNegativeSuppliersDTO dto)
            throws PSWAPIException, NoDataFoundException {

        log.debug("updateNegativeSuppliers:: IN Coming Request Data is:" + dto.toString());
        ResponseUtility.APIResponse response;
        response = pswService.shareNegativeListOfSuppliers(dto);
        return ResponseUtility.translatePSWAPIResponse(response);
    }
}
