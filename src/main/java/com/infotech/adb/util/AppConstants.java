package com.infotech.adb.util;

import java.util.UUID;

public class AppConstants {

    public final static String AD_ID = "SLKB";

    public final static String AD_SIGNATURE = "SLKB_SIGNATURE_ABCBC";
    public static final String HEADER_AUTHORIZATION = "Authorization";



    public final static String REQ_TYPE_ACCT_DETAILS_WITH_PM = "ACCT_DETAILS_WITH_PM";
    public final static String REQ_TYPE_RES_COUNTRIES = "RES_COUNTRIES";
    public final static String REQ_TYPE_RES_COMMODITIES = "RES_COMMODITIES";
    public final static String REQ_TYPE_RES_SUPPLIERS = "RES_SUPPLIERS";

    public static class PAYMENT_MODE {
        public final static String TYPE_IMPORT = "IMPORT";
        public final static String TYPE_EXPORT = "EXPORT";

        public final static String IMPORT_LC = "301";
        public final static String IMPORT_OPEN_ACCOUNT = "302";
        public final static String IMPORT_ADVANCE_PAYMENT = "303";
        public final static String IMPORT_CC = "304";

    }

    public static class PSW {

        public final static String ID = "PSW";
        public final static String BASE_URL = "http://localhost:8081/adb";
        public final static String API_AUTH = "/oauth/token";
        public final static String AUT_GRANT_TYPE = "client_credentials";
        public final static String CLIENT_ID = "adb";
        public final static String CLIENT_SECRET = "adb";

        public final static String API_UPDATE_URL = "/dealers/a/d/i/edi";
        public final static String METHOD_ID_UPDATE_INFO_AND_PM = "1512";//MGS-6
        public final static String METHOD_ID_UPDATE_RESTRICTED_COUNTRIES= "1513";//MGS-7
        public final static String METHOD_ID_UPDATE_RESTRICTED_COMMODITIES= "1514";//MGS-8
        public final static String METHOD_ID_UPDATE_RESTRICTED_SUPPLIERS= "1515";//MGS-9
        public final static String METHOD_ID_UPDATE_TRADER_ACCT_STATUS= "1516"; // MSG-10
        public final static String METHOD_ID_UPDATE_TRADERS_EMAIL_MOB= "1534"; //MGS-11

        //5.1.	Financial & GD Information Messages (Import)
        public final static String METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT= "1520"; //MGS-1 5.1.1
        public final static String METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT= "1524"; //MGS-1 5.2.1

        public final static String METHOD_ID_SHARE_BDA_INFO_IMPORT= "1522"; //MGS-3 5.1.3
        public final static String METHOD_ID_SHARE_BCA_INFO_EXPORT= "1526"; //MGS-3 5.2.3


    }


    public static class MESSAGE_GUID {
        public final static UUID MSG_UPDATE_ACCT_INFO_PAYMENT_MODE = UUID.fromString("940b2c1c-92b6-11eb-a8b3-0242ac130003");
        public final static UUID MSG_UPDATE_GUID = UUID.fromString("a1374655-5eb8-4a0e-9eb5-989521cd1ca");

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
