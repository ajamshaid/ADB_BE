package com.infotech.adb.service;

import com.infotech.adb.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();
}
