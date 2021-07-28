package com.infotech.adb.api.ref;


import com.infotech.adb.dto.COBGdFtDTO;
import com.infotech.adb.dto.COBGdFtDTOImport;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.COBGdFt;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public CustomResponse getAllCOBGdFt()
            throws CustomException, NoDataFoundException {

        List<COBGdFt> refList = null;
        try {
            refList = referenceService.getAllCOBGdFt();
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
            dto = new COBGdFtDTOImport();
        }else{

        }
        return ResponseUtility.buildResponseObject(entity, dto,true);
    }

}