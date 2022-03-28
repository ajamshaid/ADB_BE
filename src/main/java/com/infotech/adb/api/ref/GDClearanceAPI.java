package com.infotech.adb.api.ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.GDClearanceDTO;
import com.infotech.adb.dto.ReversalOfBdaBcaDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.GDClearance;
import com.infotech.adb.model.entity.ReversalOfBdaBca;
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
@RequestMapping("/gd-clearance")
@Log4j2
public class GDClearanceAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getLastModifiedGDClearance()
            throws CustomException, NoDataFoundException {

        List<GDClearance> refList = null;
        try {
            refList = referenceService.getLastModifiedGDClearance();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new GDClearanceDTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getGDClearanceById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        GDClearance entity = null;
        try {
            entity = referenceService.getGDClearanceById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new GDClearanceDTO(),true);
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CustomResponse saveGDClearance(@RequestBody GDClearanceDTO reqDTO, @RequestParam(value = "pushToPSW",defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO)) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        GDClearance entity = null;
        CustomResponse customResponse = null;
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateGDClearanceAndShare(reqDTO));
            } else {
                entity = referenceService.updateGDClearance(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity,"200","Record Added Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }


    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateGDClearance(@RequestBody GDClearanceDTO reqDTO, @RequestParam(value = "pushToPSW",defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        GDClearance entity = null;
        CustomResponse customResponse = null;
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateGDClearanceAndShare(reqDTO));
            } else {
                entity = referenceService.updateGDClearance(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity,"200","Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }

    @RequestMapping(value = "/search-gd-clearence", method = RequestMethod.GET)
    public CustomResponse searchGdClearance(HttpServletRequest request,
                                                 @RequestParam(value = "tradeType", required = false) String tradeType,
                                                 @RequestParam(value = "traderNTN", required = false) String traderNTN,
                                                 @RequestParam(value = "fromDate", required = false) String fromDate,
                                                 @RequestParam(value = "toDate", required = false) String toDate,
                                                 @RequestParam(value = "isNew", defaultValue = "true", required = false) boolean isNew)
            throws CustomException, NoDataFoundException {

        List<GDClearance> gdClearanceList = null;
        try {
            gdClearanceList = referenceService.searchGDClearance(tradeType, traderNTN, fromDate, toDate
                    , AppConstants.RecordStatuses.getSearchStatesList(isNew));
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e);
        }
        return ResponseUtility.buildResponseList(gdClearanceList, new GDClearanceDTO());
    }
}