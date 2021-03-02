package com.infotech.adb.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestMain {

    public static void main(String args[]){
        System.out.println(new BCryptPasswordEncoder().encode("admin1"));
    }
}
