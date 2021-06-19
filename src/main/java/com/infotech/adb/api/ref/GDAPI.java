package com.infotech.adb.api.ref;

import com.infotech.adb.dto.GDImportDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.GD;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppConstants;
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
        return ResponseUtility.buildResponseObject(entity, new GDImportDTO(),true);
    }

    /**********************
     *  Export
     *************************/


}
