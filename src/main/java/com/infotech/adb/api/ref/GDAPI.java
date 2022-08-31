package com.infotech.adb.api.ref;

import com.infotech.adb.dto.CancellationOfFTDTO;
import com.infotech.adb.dto.GDExportDTO;
import com.infotech.adb.dto.GDImportDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.CancellationOfFT;
import com.infotech.adb.model.entity.GD;
import com.infotech.adb.model.entity.GDExport;
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
@RequestMapping("/gd")
@Log4j2
public class GDAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/import/", method = RequestMethod.GET)
    public CustomResponse getAllGDImport()
            throws CustomException, NoDataFoundException {

        List<GD> refList = null;
        try {
            refList = referenceService.getAllGD(AppConstants.TYPE_IMPORT);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new GDImportDTO());
    }

    @RequestMapping(value = "/import/{id}", method = RequestMethod.GET)
    public CustomResponse getImportGDById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        GD entity = null;
        try {
            entity = referenceService.getGDById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new GDImportDTO(), true);
    }

    @RequestMapping(value = "/search-gd", method = RequestMethod.GET)
    public CustomResponse searchGD(HttpServletRequest request,
                                   @RequestParam(value = "finInsUniqueNumber", required = false) String finInsUniqueNumber,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "gdNumber", required = false) String gdNumber,
                                   @RequestParam(value = "ntnFtn", required = false) String ntnftn,
                                   @RequestParam(value = "fromDate", required = false) String fromDate,
                                   @RequestParam(value = "toDate", required = false) String toDate)
            throws CustomException, NoDataFoundException {

        List<GD> gdList = null;
        try {
            gdList = referenceService.searchGD(finInsUniqueNumber,name,gdNumber,ntnftn,fromDate, toDate);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e);
        }
        return ResponseUtility.buildResponseList(gdList, new GDImportDTO());
    }


    /**********************
     *  Export
     *************************/

    @RequestMapping(value = "/export/", method = RequestMethod.GET)
    public CustomResponse getAllGDExport()
            throws CustomException, NoDataFoundException {

        List<GDExport> refList = null;
        try {
            refList = referenceService.getAllGDExport();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new GDExportDTO());
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    public CustomResponse getImportGDExportById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        GDExport entity = null;
        try {
            entity = referenceService.getGDExportById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new GDExportDTO(), true);
    }

    @RequestMapping(value = "/search-gd-export", method = RequestMethod.GET)
    public CustomResponse searchGDExport(HttpServletRequest request,
                                         @RequestParam(value = "finInsUniqueNumber", required = false) String finInsUniqueNumber,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "gdNumber", required = false) String gdNumber,
                                         @RequestParam(value = "ntnFtn", required = false) String ntnftn,
                                         @RequestParam(value = "fromDate", required = false) String fromDate,
                                         @RequestParam(value = "toDate", required = false) String toDate)
            throws CustomException, NoDataFoundException {

        List<GDExport> gdExportList = null;
        try {
            gdExportList = referenceService.searchGDExport(finInsUniqueNumber,name,gdNumber,ntnftn,fromDate, toDate);
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e);
        }
        return ResponseUtility.buildResponseList(gdExportList, new GDExportDTO());
    }
}
