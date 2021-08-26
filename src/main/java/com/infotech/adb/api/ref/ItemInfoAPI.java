package com.infotech.adb.api.ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.FinancialTransactionExportDTO;
import com.infotech.adb.dto.FinancialTransactionImportDTO;
import com.infotech.adb.dto.ItemInformationExportDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
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
@RequestMapping("/item-info")
@Log4j2
public class ItemInfoAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CustomResponse deleteItem(HttpServletRequest request,
                                     @PathVariable("id") Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        try {
            referenceService.deleteItemById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        CustomResponse customResponse = null;
        customResponse = ResponseUtility.successResponse(null, "200", "Item deleted Successfully");
        return customResponse;
    }
}
