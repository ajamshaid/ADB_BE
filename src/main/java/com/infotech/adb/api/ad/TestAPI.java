package com.infotech.adb.api.ad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.api.consumer.PSWClient;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.User;
import com.infotech.adb.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/test")
@Log4j2
public class TestAPI {

    @Autowired
    UserService userService;

    @Autowired
    PSWClient pswClient;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(UriComponentsBuilder ucBuilder) {

        log.info("Test .. Fetch All Users Method");
        List<User> users = userService.findAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/update-info", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestUpdateInfoTest() throws JsonProcessingException {

        log.info("Test .. Request update Info");
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setIban("PK 123213123");
        accountDetail.setAccountType("701");
        return  new ResponseEntity<>(pswClient.updateAccountAndPMInPWS(accountDetail), HttpStatus.OK);

    }
}
