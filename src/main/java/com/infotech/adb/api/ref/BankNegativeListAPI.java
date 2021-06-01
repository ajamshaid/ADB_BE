package com.infotech.adb.api.ref;

import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.model.entity.BankNegativeList;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/bank-negtive-list")
@Log4j2
public class BankNegativeListAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/{bankCode}", method = RequestMethod.GET)
    public CustomResponse getByIBAN(HttpServletRequest request,
                                      @PathVariable String bankCode)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(bankCode)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        BankNegativeList entity = null;
        try {
            entity = referenceService.getBankNegativeListByCode(bankCode);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity);
    }
}
