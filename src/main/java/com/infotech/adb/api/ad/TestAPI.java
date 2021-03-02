package com.infotech.adb.api.ad;

import com.infotech.adb.model.entity.User;
import com.infotech.adb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/client")
public class TestAPI {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(UriComponentsBuilder ucBuilder) {

        List<User> users = userService.findAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
