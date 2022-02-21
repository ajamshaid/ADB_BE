package com.infotech.adb.api.ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.*;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.GDClearance;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.model.entity.MqLog;
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
@RequestMapping("/ft")
@Log4j2
public class FinancialTransactionAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;


    @RequestMapping(value = "/import/", method = RequestMethod.GET)
    public CustomResponse getAllImportFT(@RequestParam(value = "isNew", required = false) boolean isNew)
            throws CustomException, NoDataFoundException {

        List<FinancialTransaction> refList = null;
        try {
            refList = referenceService.getAllFinancialTransactionByType(AppConstants.TYPE_IMPORT
                    , isNew ? AppConstants.RecordStatuses.CREATED_BY_MQ
                            : AppConstants.RecordStatuses.PUSHED_TO_PSW);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new FinancialTransactionImportDTO());
    }

    @RequestMapping(value = "/import/{id}", method = RequestMethod.GET)
    public CustomResponse getImportFTById(@PathVariable Long id)
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
        return ResponseUtility.buildResponseObject(entity, new FinancialTransactionImportDTO(), true);
    }

    @RequestMapping(value = "/import/", method = RequestMethod.PUT)
    public CustomResponse updateImportFT(@RequestBody FinancialTransactionImportDTO reqDTO, @RequestParam(value = "pushToPSW", defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getFtId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }

        CustomResponse customResponse = null;
        FinancialTransaction entity = null;
        try {

            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateFTImportAndShare(reqDTO));
            } else {
                entity = referenceService.updateFinancialTransaction(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity, "200", reqDTO.getFinInsUniqueNumber() + " Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }

    @RequestMapping(value = "/import/{id}/itemInfo", method = RequestMethod.POST)
    public CustomResponse saveImportItemInfo(@RequestBody ItemInformationExportDTO reqDTO,
                                             @PathVariable(value = "id") Long id)
            throws DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        ItemInformation entity = null;
        entity = this.referenceService.saveItemInfo(id, reqDTO);
        CustomResponse customResponse = null;
        customResponse = ResponseUtility.successResponse(entity, "200", "Item Added Successfully");
        return customResponse;
    }

    @RequestMapping(value = "/search-import", method = RequestMethod.GET)
    public CustomResponse searchImportFt(@RequestParam(value = "iban", required = false) String iban,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "fromDate", required = false) String fromDate,
                                         @RequestParam(value = "toDate", required = false) String toDate)
            throws CustomException, DataValidationException, NoDataFoundException {
        log.info("searchLogs API initiated...");

        List<FinancialTransaction> financialTransactions = null;
        try {
            financialTransactions = referenceService.searchFT(iban, name, fromDate, toDate);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseList(financialTransactions, new FinancialTransactionImportDTO());
    }

    @RequestMapping(value = "/add-imp", method = RequestMethod.POST)
    public CustomResponse createImportFt(HttpServletRequest request,
                                           @RequestBody FinancialTransactionImportDTO financialTransactionImportDTO)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(financialTransactionImportDTO) || !AppUtility.isEmpty(financialTransactionImportDTO.getFtId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        FinancialTransaction financialTransaction = null;
        try {
            financialTransaction = referenceService.createFt(financialTransactionImportDTO.convertToEntity());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e,null);
        }
        return ResponseUtility.buildResponseObject(financialTransaction);
    }

    @RequestMapping(value = "/add-item", method = RequestMethod.POST)
    public CustomResponse createImportItem(HttpServletRequest request,
                                         @RequestBody ItemInformationImportDTO itemInformationImportDTO)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(itemInformationImportDTO) || !AppUtility.isEmpty(itemInformationImportDTO.getIiId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        ItemInformation itemInformation = null;
        try {
            itemInformation = referenceService.createItemInfo(itemInformationImportDTO.convertToEntity());
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e,null);
        }
        return ResponseUtility.buildResponseObject(itemInformation);
    }

    /**********************
     *  Export
     *************************/

    @RequestMapping(value = "/export/", method = RequestMethod.GET)
    public CustomResponse getAllExportFT(@RequestParam(value = "isNew", required = false) boolean isNew)
            throws CustomException, NoDataFoundException {

        List<FinancialTransaction> refList = null;
        try {
            refList = referenceService.getAllFinancialTransactionByType(AppConstants.TYPE_EXPORT
                    , isNew ? AppConstants.RecordStatuses.CREATED_BY_MQ
                            : AppConstants.RecordStatuses.PUSHED_TO_PSW);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new FinancialTransactionExportDTO());
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    public CustomResponse getExportFTById(@PathVariable Long id)
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
        return ResponseUtility.buildResponseObject(entity, new FinancialTransactionExportDTO(), true);
    }

    @RequestMapping(value = "/export/", method = RequestMethod.PUT)
    public CustomResponse updateExportFT(@RequestBody FinancialTransactionExportDTO reqDTO, @RequestParam(value = "pushToPSW", defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getFtId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }

        CustomResponse customResponse = null;
        FinancialTransaction entity = null;
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateFTExportAndShare(reqDTO));
            } else {
                entity = referenceService.updateFinancialTransaction(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity, "200", "Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }

    @RequestMapping(value = "/export/{id}/itemInfo", method = RequestMethod.POST)
    public CustomResponse saveItemInfo(@RequestBody ItemInformationExportDTO reqDTO,
                                       @PathVariable(value = "id") Long id)
            throws DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        ItemInformation entity = null;
        entity = this.referenceService.saveItemInfo(id, reqDTO);
        CustomResponse customResponse = null;
        customResponse = ResponseUtility.successResponse(entity, "200", "Item Added Successfully");
        return customResponse;
    }

    @RequestMapping(value = "/search-export", method = RequestMethod.GET)
    public CustomResponse searchExportFt(@RequestParam(value = "iban", required = false) String iban,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "fromDate", required = false) String fromDate,
                                         @RequestParam(value = "toDate", required = false) String toDate)
            throws CustomException, DataValidationException, NoDataFoundException {
        log.info("searchExportFt API initiated...");

        List<FinancialTransaction> financialTransactions = null;
        try {
            financialTransactions = referenceService.searchFT(iban, name, fromDate, toDate);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, null);
        }
        return ResponseUtility.buildResponseList(financialTransactions, new FinancialTransactionExportDTO());
    }
}
