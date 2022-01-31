package com.infotech.adb.api.ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.GDClearanceDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.GDClearance;
import com.infotech.adb.model.entity.User;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;


@RestController
@RequestMapping("/user")
@Log4j2
public class UserAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getAllUser()
            throws CustomException, NoDataFoundException {

        List<User> refList = null;
        try {
            refList = referenceService.getAllUser();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getUserById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        User entity = null;
        try {
            entity = referenceService.getUserById(id);
            entity.setPassword(null);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity);
    }
    @RequestMapping(value = "/userName/{userName}", method = RequestMethod.GET)
    public CustomResponse getUserById(@PathVariable String userName)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(userName)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        User entity = null;
        try {
            entity = referenceService.getUserByUserName(userName);
            entity.setPassword(null);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CustomResponse saveUser(@RequestBody User user)
            throws  DataValidationException, NoDataFoundException, CustomException {

        if (AppUtility.isEmpty(user)) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        User entity = null;
        CustomResponse customResponse = null;
        try {
            entity = referenceService.updateUser(user);
        }catch (Exception e){
            ResponseUtility.exceptionResponse(e, AppConstants.DBConstraints.UNIQ_USERNAME);
        }
        customResponse = ResponseUtility.successResponse(entity, "200", "Record Added Successfully");
        return customResponse;
    }


    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateUser(@RequestBody User user)
            throws DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(user) || AppUtility.isEmpty(user.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        User entity = null;
        CustomResponse customResponse = null;
        entity = referenceService.updateUser(user);
        customResponse = ResponseUtility.successResponse(entity, "200", "Record Updated Successfully");
        return customResponse;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.PUT)
    public CustomResponse resetPassword(@RequestBody User user)
            throws DataValidationException, NoDataFoundException, CustomException {
        if (AppUtility.isEmpty(user) && user.getId() <= 0) {
            throw new DataValidationException(AppUtility.getResourceMessage("validation.error"));
        }
        log.info("resetPassword API initiated...");
        User newUser = referenceService.resetPassword(user);
        return ResponseUtility.buildResponseObject(newUser);
    }
}