package com.infotech.adb.api.ref;

import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.AccountDetail;
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
@RequestMapping("/account-details")
@Log4j2
public class AccountDetailAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getAll(HttpServletRequest request,
                                 @RequestParam(value = "status", required = false) String status)
            throws CustomException, NoDataFoundException {

        List<AccountDetail> refList = null;
        try {
            refList = referenceService.getAllAccountDetails();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList);
    }

    @RequestMapping(value = "/{iban}", method = RequestMethod.GET)
    public CustomResponse getByIBAN(HttpServletRequest request,
                                      @PathVariable String iban)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(iban)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        AccountDetail entity = null;
        try {
            entity = referenceService.getAccountDetailByIban(iban);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity);
    }
}
