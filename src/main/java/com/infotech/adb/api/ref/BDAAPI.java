package com.infotech.adb.api.ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.BCADTO;
import com.infotech.adb.dto.BDADTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.BDA;
import com.infotech.adb.service.ReferenceService;
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
@RequestMapping("/bda")
@Log4j2
public class BDAAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getAllBDALastModified()
            throws CustomException, NoDataFoundException {

        List<BDA> refList = null;
        try {
            refList = referenceService.getAllBDALastModified();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new BDADTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getBDAById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        BDA entity = null;
        try {
            entity = referenceService.getBDAById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new BDADTO(),true);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateBDA(@RequestBody BDADTO reqDTO, @RequestParam(value = "pushToPSW",defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        CustomResponse customResponse = null;
        BDA entity = null;
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateBDAAndShare(reqDTO));
            } else {
                entity = referenceService.updateBDA(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity,"200","Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }

    @RequestMapping(value = "/search-bda", method = RequestMethod.GET)
    public CustomResponse searchBCA(HttpServletRequest request,
                                    @RequestParam(value = "iban", required = false) String iban,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "status", required = false) String status,
                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                    @RequestParam(value = "toDate", required = false) String toDate)
            throws CustomException, NoDataFoundException {

        List<BDA> bdaList = null;
        try {
            bdaList = referenceService.searchBDA(iban, name, status, fromDate, toDate);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e);
        }
        return ResponseUtility.buildResponseList(bdaList, new BDADTO());
    }
}
