package com.infotech.adb.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptDecryptMain {

    public static void main(String args[])  {
        System.out.println("-------Hello");

        String data = "{\"iban\": \"PK123\",\"accountStatus\": \"600\"}";
        System.out.println( "Signature : "+buildSignature(data));
    }

    public static String buildSignature(String data) {
        return new String(Base64.encodeBase64(DigestUtils.sha256(data)));
    }
}
