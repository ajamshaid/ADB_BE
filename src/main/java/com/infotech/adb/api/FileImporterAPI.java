package com.infotech.adb.api;


import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.OpenCsvUtil;
import com.infotech.adb.util.ResponseUtility;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file-importer")
@Log4j2
@Api(tags = "@Import")
public class FileImporterAPI {

    @Autowired
    private ReferenceService referenceService;

    @PostMapping("/iban")
    public CustomResponse uploadTraderProfilesFile(@RequestParam("csvfile") MultipartFile csvfile) throws CustomException {

        String message = "";

        // Checking the upload-file's name before processing
        if (csvfile.getOriginalFilename().isEmpty()) {
            ResponseUtility.exceptionResponse(new DataValidationException("No selected file to upload! Please do the checking"), "");
        }
        // checking the upload file's type is CSV or NOT
        if (!OpenCsvUtil.isCSVFile(csvfile)) {
            ResponseUtility.exceptionResponse(new DataValidationException("Error: this is not a CSV file!"), "");
        }
        try {
            // save file data to database
            referenceService.parseCSVAndSaveAccountDetails(csvfile.getInputStream());

        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, AppConstants.DBConstraints.UNIQ_IBAN);
        }

        return ResponseUtility.successResponse(null, null , csvfile.getOriginalFilename() + " : Upload File Successfully!");
    }

    @PostMapping("/neg-list")
    public CustomResponse uploadBankNegList(@RequestParam("csvfile") MultipartFile csvfile) throws CustomException {

        String message = "";
        // Checking the upload-file's name before processing
        if (csvfile.getOriginalFilename().isEmpty()) {
            ResponseUtility.exceptionResponse(new DataValidationException("No selected file to upload! Please do the checking"), "");
        }
        // checking the upload file's type is CSV or NOT
        if (!OpenCsvUtil.isCSVFile(csvfile)) {
            ResponseUtility.exceptionResponse(new DataValidationException("Error: this is not a CSV file!"), "");
        }
        try {
            // save file data to database
            referenceService.parseCSVAndSaveBankNegList(csvfile.getInputStream());

        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e, AppConstants.DBConstraints.UNIQ_IBAN);
        }
        return ResponseUtility.successResponse(null, null , csvfile.getOriginalFilename() + " : Upload File Successfully!");
    }
}
