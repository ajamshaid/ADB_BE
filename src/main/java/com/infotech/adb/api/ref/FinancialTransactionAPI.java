package com.infotech.adb.api.ref;

import com.infotech.adb.api.consumer.PSWAPIConsumer;
import com.infotech.adb.dto.FinancialTransactionExportDTO;
import com.infotech.adb.dto.FinancialTransactionImportDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.FinancialTransaction;
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
@RequestMapping("/fin_transaction")
@Log4j2
public class FinancialTransactionAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private PSWAPIConsumer consumer;

    @RequestMapping(value = "/import/", method = RequestMethod.GET)
    public CustomResponse getAllImportFT(HttpServletRequest request,
                                 @RequestParam(value = "status", required = false) String status)
            throws CustomException, NoDataFoundException {

        List<FinancialTransaction> refList = null;
        try {
            refList = referenceService.getAllFinancialTransactionByType(AppConstants.TYPE_IMPORT);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new FinancialTransactionImportDTO());
    }

    @RequestMapping(value = "/import/{id}", method = RequestMethod.GET)
    public CustomResponse getImportFTById(HttpServletRequest request,
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

    @RequestMapping(value = "/import/", method = RequestMethod.PUT)
    public CustomResponse updateImportFT(HttpServletRequest request,
                                     @RequestBody FinancialTransactionImportDTO reqDTO)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getFtId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        FinancialTransaction entity = null;
        try {
            entity = referenceService.updateFinancialTransaction(reqDTO.convertToEntity());



        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, "");
        }

        try {
            consumer.shareFinancialInformationImport(reqDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtility.buildResponseObject(entity, new FinancialTransactionImportDTO(), false);
    }

    /**********************
     *  Export
     *************************/

    @RequestMapping(value = "/export/", method = RequestMethod.GET)
    public CustomResponse getAllExportFT(HttpServletRequest request,
                                 @RequestParam(value = "status", required = false) String status)
            throws CustomException, NoDataFoundException {

        List<FinancialTransaction> refList = null;
        try {
            refList = referenceService.getAllFinancialTransactionByType(AppConstants.TYPE_EXPORT);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new FinancialTransactionExportDTO());
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    public CustomResponse getExportFTById(HttpServletRequest request,
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
        return ResponseUtility.buildResponseObject(entity, new FinancialTransactionExportDTO(),true);
    }

    @RequestMapping(value = "/export/", method = RequestMethod.PUT)
    public CustomResponse updateExportFT(HttpServletRequest request,
                                         @RequestBody FinancialTransactionExportDTO reqDTO)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getFtId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        FinancialTransaction entity = null;
        try {
            entity = referenceService.updateFinancialTransaction(reqDTO.convertToEntity());

        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, "");
        }


        try {
            consumer.shareFinancialInformationExport(reqDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtility.buildResponseObject(entity, new FinancialTransactionExportDTO(), false);
    }

}
