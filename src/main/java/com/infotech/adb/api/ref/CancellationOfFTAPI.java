package com.infotech.adb.api.ref;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.CancellationOfFTDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.CancellationOfFT;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;


@RestController
@RequestMapping("/cancel-ft")
@Log4j2
public class CancellationOfFTAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getLastModifiedCancellationOfFT()
            throws CustomException, NoDataFoundException {

        List<CancellationOfFT> refList = null;
        try {
            refList = referenceService.getLastModifiedCancellationOfFT();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new CancellationOfFTDTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getCancellationOfFTById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        CancellationOfFT entity = null;
        try {
            entity = referenceService.getCancellationOfFTById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new CancellationOfFTDTO(),true);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateCancellationOfFT(@RequestBody CancellationOfFTDTO reqDTO, @RequestParam(value = "pushToPSW",defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {
        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        CancellationOfFT entity = null;
        CustomResponse customResponse = null;
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateCancellationOfFTAndShare(reqDTO));
            } else {
                entity = referenceService.updateCancellationOfFT(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity,"200","Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;

    }
}