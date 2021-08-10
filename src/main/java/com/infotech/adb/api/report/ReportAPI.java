package com.infotech.adb.api.report;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.service.ReportService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/report")
@Log4j2
public class ReportAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/{gdId}/print-gd", method = RequestMethod.GET)
    public ResponseEntity<?> printGD(HttpServletRequest request,
                                      @PathVariable(value = "gdId") Long gdId)
            throws DataValidationException, NoDataFoundException, CustomException {
        log.info("printSAD API initiated...");
        //String stateId = request.getHeader(AppConstants.STATE_ID_HEADER);

        if (AppUtility.isEmpty(gdId)) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        ByteArrayInputStream bis = null;
        try {
            bis = reportService.buildGDPrint(gdId);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e);
        }
        return ResponseUtility.buildReportResponseObject(new InputStreamResource(bis));
    }
}