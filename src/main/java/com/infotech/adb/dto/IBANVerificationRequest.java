package com.infotech.adb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IBANVerificationRequest {

    private String iban;
    private String email;
    private String mobileNumber;
    private String gdNumber;
}
