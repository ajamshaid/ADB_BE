package com.infotech.adb.util;

public class AppConstants {

    public final static String AD_ID = "SLKB";

    public final static String PAYMENT_MODE_TYPE_IMPORT = "Import";
    public final static String PAYMENT_MODE_TYPE_EXPORT = "Export";

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
