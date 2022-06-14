package com.infotech.adb.api.ref;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.GDClearanceDTO;
import com.infotech.adb.dto.SettelmentOfFIDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.GDClearance;
import com.infotech.adb.model.entity.SettelmentOfFI;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;


@RestController
@RequestMapping("/settlement-fi")
@Log4j2
public class SettelmentOfFIAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getLastModifiedSettlementOfFI(@RequestParam(value = "isNew", required = false) boolean isNew)
            throws CustomException, NoDataFoundException {

        List<SettelmentOfFI> refList = null;
        try {
            refList = referenceService.getLastModifiedSettlementOfFI(isNew ? AppConstants.RecordStatuses.CREATED_BY_MQ
                    : AppConstants.RecordStatuses.PUSHED_TO_PSW);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new SettelmentOfFIDTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getSettlementOfFIById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        SettelmentOfFI entity = null;
        try {
            entity = referenceService.getSettlementOfFIById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new SettelmentOfFIDTO(), true);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateSettlementOfFI(@RequestBody SettelmentOfFIDTO reqDTO, @RequestParam(value = "pushToPSW", defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        SettelmentOfFI entity = null;
        CustomResponse customResponse = null;
        try {
            if (pushToPSW) {
                reqDTO.setStatus(AppConstants.RecordStatuses.PUSHED_TO_PSW);
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateSettlementOfFIAndShare(reqDTO));
            } else {
                reqDTO.setStatus(AppConstants.RecordStatuses.CREATED_BY_MQ);
                entity = referenceService.updateSettlementOfFI(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity, "200", "Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }

    @RequestMapping(value = "/search-settle-fi", method = RequestMethod.GET)
    public CustomResponse searchSettlementOfFI(HttpServletRequest request,
                                               @RequestParam(value = "tradeType", required = false) String tradeType,
                                               @RequestParam(value = "traderNTN", required = false) String traderNTN,
                                               @RequestParam(value = "fromDate", required = false) String fromDate,
                                               @RequestParam(value = "toDate", required = false) String toDate,
                                               @RequestParam(value = "isNew", defaultValue = "true", required = false) boolean isNew)
            throws CustomException, NoDataFoundException {

        List<SettelmentOfFI> settelmentOfFIList = null;
        try {
            settelmentOfFIList = referenceService.searchSettlementOfFI(tradeType, traderNTN, fromDate, toDate
                    , AppConstants.RecordStatuses.getSearchStatesList(isNew));
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e);
        }
        return ResponseUtility.buildResponseList(settelmentOfFIList, new SettelmentOfFIDTO());
    }
}