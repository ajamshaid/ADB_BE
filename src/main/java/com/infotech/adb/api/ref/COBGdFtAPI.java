package com.infotech.adb.api.ref;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.COBGdFtDTO;
import com.infotech.adb.dto.COBGdFtExportDTO;
import com.infotech.adb.dto.COBGdFtImportDTO;
import com.infotech.adb.dto.ChangeBankRequestDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.COBGdFt;
import com.infotech.adb.model.entity.ChangeOfBank;
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
@RequestMapping("/gd-ft")
@Log4j2
public class COBGdFtAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getAllLastModifiedCOBGdFt()
            throws CustomException, NoDataFoundException {

        List<COBGdFt> refList = null;
        try {
            refList = referenceService.getAllLastModifiedCOBGdFt();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new COBGdFtDTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getCOBGdFtById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {


        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        COBGdFt entity = null;
        try {
            entity = referenceService.getCOBGdFtById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }

        COBGdFtDTO dto = null;
        if("01".equalsIgnoreCase(entity.getTradeTranType())){
            dto = new COBGdFtImportDTO();
        }else{
            dto = new COBGdFtExportDTO();
        }
        return ResponseUtility.buildResponseObject(entity, dto,true);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateCOBGdFt(@RequestBody ChangeBankRequestDTO reqDTO, @RequestParam(value = "pushToPSW",defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        ChangeOfBank entity = null;
        CustomResponse customResponse = null;
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateCOBGdFtAndShare(reqDTO));
            } else {
                entity = referenceService.updateCOB(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity,"200","Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }

}