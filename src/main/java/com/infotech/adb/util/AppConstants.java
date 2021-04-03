package com.infotech.adb.util;

import java.util.UUID;

public class AppConstants {

    public final static String AD_ID = "SLKB";

    public final static String AD_SIGNATURE = "SLKB_SIGNATURE_ABCBC";
    public static final String HEADER_AUTHORIZATION = "Authorization";


    public final static String PAYMENT_MODE_TYPE_IMPORT = "IMPORT";
    public final static String PAYMENT_MODE_TYPE_EXPORT = "EXPORT";

    public final static String REQ_TYPE_ACCT_DETAILS_WITH_PM = "ACCT_DETAILS_WITH_PM";
    public final static String REQ_TYPE_RES_COUNTRIES = "RES_COUNTRIES";
    public final static String REQ_TYPE_RES_COMMODITIES = "RES_COMMODITIES";
    public final static String REQ_TYPE_RES_SUPPLIERS = "RES_SUPPLIERS";

    public static class PSW {
        public final static String ID = "PSW";
        public final static String BASE_URL = "http://localhost:8081/adb";
        public final static String API_AUTH = "/oauth/token";
        public final static String AUT_GRANT_TYPE = "client_credentials";
        public final static String CLIENT_ID = "adb";
        public final static String CLIENT_SECRET = "adb";

        public final static String METHOD_ID_UPDATE_INFO_AND_PM = "1512";

        public final static String API_UPDATE_URL = "/dealers/a/d/i/edi";

    }



    public static class MESSAGE_GUID {
        public final static UUID MSG_UPDATE_ACCT_INFO_PAYMENT_MODE = UUID.fromString("940b2c1c-92b6-11eb-a8b3-0242ac130003");
        //public final static String MSG_UPDATE_INFO_PAYMENT_MODE = "INFO_PAY_MODES";
    }

    public static class DBConstraints {

    }
    public static class PSWResponseCodes {

        public static final String OK = "200";
        public static final String CREATED = "201";
        public static final String ACCEPTED = "202";
        public static final String NO_DATA_FOUND = "204"; //HttpStatus.NO_CONTENT
        public static final String VERIFIED = "207";
        public static final String UN_VERIFIED = "208";

    }
}
