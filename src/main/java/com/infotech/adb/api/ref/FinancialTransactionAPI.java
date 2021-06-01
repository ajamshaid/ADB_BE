package com.infotech.adb.api.ref;

import com.infotech.adb.dto.FinancialTransactionImportDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.FinancialTransaction;
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
@RequestMapping("/fin_transaction")
@Log4j2
public class FinancialTransactionAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/import/", method = RequestMethod.GET)
    public CustomResponse getAll(HttpServletRequest request,
                                 @RequestParam(value = "status", required = false) String status)
            throws CustomException, NoDataFoundException {

        List<FinancialTransaction> refList = null;
        try {
            refList = referenceService.getAllFinancialTransactionByStatus(status);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new FinancialTransactionImportDTO());
    }

    @RequestMapping(value = "/import/{id}", method = RequestMethod.GET)
    public CustomResponse getById(HttpServletRequest request,
                                    @PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        FinancialTransaction entity = null;
        try {
            entity = referenceService.getAllFinancialTransactionById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new FinancialTransactionImportDTO(),true);
    }

}
