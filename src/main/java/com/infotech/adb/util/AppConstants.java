package com.infotech.adb.util;

public class AppConstants {

    public final static String AD_ID = "SLB";
    public final static String MQ_USER ="MQ_QOUT";
    public final static String TYPE_IMPORT = "IMPORT";
    public final static String TYPE_GD_IMPORT = "GD-IMPORT";
    public final static String TYPE_EXPORT = "EXPORT";

    //YyyyMmddhhmmss

    public final static String AD_SIGNATURE = "SLB_SIGNATURE";
    public static final String HEADER_AUTHORIZATION = "Authorization";



    public final static String REQ_TYPE_ACCT_DETAILS_WITH_PM = "ACCT_DETAILS_WITH_PM";
    public final static String REQ_TYPE_RES_COUNTRIES = "RES_COUNTRIES";
    public final static String REQ_TYPE_RES_COMMODITIES = "RES_COMMODITIES";
    public final static String REQ_TYPE_RES_SUPPLIERS = "RES_SUPPLIERS";

    public static class PAYMENT_MODE {

        public final static String LETTER_OF_CREDIT = "Letter of Credit";

        public final static String IMP_LC_VALUE = "301";
        public final static String IMP_OPEN_ACCOUNT_VALUE = "302";
        public final static String IMP_ADV_PAYMENT_VALUE = "303";
        public final static String IMP_CC_VALUE = "304";
        public final static String IMP_REMITTANCE_NOT_INVOLVED_VALUE = "309";
        public final static String IMP_OPEN_ACCOUNT_CASH_MARGIN_VALUE = "310";

        public final static String EXP_OPEN_ACCOUNT_VALUE = "305";
        public final static String EXP_ADV_PAYMENT_VALUE = "306";
        public final static String EXP_WITH_LC_VALUE = "307";
        public final static String EXP_WITH_OUT_LC_VALUE = "308";

    }
    public static class PSW {

        public final static String TIME_STAMP_FORMAT="YyyyMmddhhmmss";
        public final static String ID = "PSW";
        public final static String BASE_URL = "https://sit.psw.gov.pk";
        public final static String API_AUTH = "/auth/connect/token";
        public final static String AUT_GRANT_TYPE = "client_credentials";
        public final static String CLIENT_ID = "ADSAUDX35";
        public final static String CLIENT_SECRET = "Nwpm2dByDNoe";

        public final static String API_UPDATE_URL = "/api/dealers/a/d/i/edi";
        public final static String METHOD_ID_UPDATE_INFO_AND_PM = "1512";//MGS-4.3
        public final static String METHOD_ID_UPDATE_TRADER_ACCT_STATUS= "1516"; // MSG-4.4

        public final static String METHOD_ID_SHARE_NEG_LIST_OF_COUNTRIES= "1557";//MGS-11.1
        public final static String METHOD_ID_SHARE_NEG_LIST_OF_COMMODITIES= "1558";//MGS-11.2
        public final static String METHOD_ID_SHARE_NEG_LIST_OF_SUPPLIERS= "1559";//MGS-11.3

       /*
       public final static String METHOD_ID_SHARE_NEG_LIST_OF_COUNTRIES= "1554";//MGS-11.1
        public final static String METHOD_ID_SHARE_NEG_LIST_OF_COMMODITIES= "1555";//MGS-11.2
        public final static String METHOD_ID_SHARE_NEG_LIST_OF_SUPPLIERS= "1556";//MGS-11.3

       */
        public final static String METHOD_ID_UPDATE_TRADERS_EMAIL_MOB= "1534"; //MGS-11

        //5.1.	Financial & GD Information Messages (Import)
        public final static String METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT_NEW= "1520"; //MGS-1 5.1.1
        public final static String METHOD_ID_SHARE_FIN_TRANS_DATA_IMPORT_UPDATE= "1549"; //MGS-1 5.1.1

        public final static String METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT_NEW= "1524"; //MGS-1 5.2.1
        public final static String METHOD_ID_SHARE_FIN_TRANS_DATA_EXPORT_UPDATE= "1548"; //MGS-1 5.2.1

        public final static String METHOD_ID_SHARE_BDA_INFO_IMPORT= "1522"; //MGS-3 5.1.3
        public final static String METHOD_ID_SHARE_BCA_INFO_EXPORT= "1526"; //MGS-3 5.2.3

        //Cash Margin Message
        public final static String METHOD_ID_SHARE_GD_CLEARANCE_MESSAGE= "1541"; //MGS-1 6.1
        public final static String METHOD_ID_SHARE_COB_APPROVAL_REJECTION_MESSAGE= "1538"; //MGS-2 7.2
        public final static String METHOD_ID_SHARE_COB_APPROVAL_REJECTION_BY_OLD_ADB_MESSAGE= "1539"; //MGS-4 7.4

        public final static String METHOD_ID_CANCELLATION_OF_FT= "1535"; //MGS-1 8.1
        public final static String METHOD_ID_REVERSAL_OF_BDA_BCA= "1536"; //MGS-1 9.1
        public final static String METHOD_ID_SETTLEMENT_FIN_INST= "1537"; //MGS-1 10.1

        /* Missing Messages Method ID 1538 & 1539
        1538 Sharing of Change of Bank request approval/rejection message by AD with PSW
        1539 Sharing of Change of Bank request approval/rejection message by other selected AD with PSW
        */
    }
    public static class RecordStatuses {
        public static final String CREATED_BY_MQ = "MQ Created";
        public static final String CREATED_BY_PSW = "PSW Created";
        public static final String PUSHED_TO_PSW = "Pushed To PSW";

    }
    public static class DBConstraints {

        public static final String UNIQ_IBAN = "UNIQ_IBAN";
        public static final String UNIQ_USERNAME = "UNIQ_USERNAME";
    }
    public static class PSWResponseCodes {

        public static final String OK = "200";
        public static final String CREATED = "201";
        public static final String ACCEPTED = "202";

        public static final String VERIFIED = "207";
        public static final String UN_VERIFIED = "208";

        public static final String CANCELLED = "211";
        public static final String REVERSED = "212";
        public static final String SETTLED = "213";
        public static final String NOT_ALLOWED = "214";


        public static final String NO_DATA_FOUND = "700"; //HttpStatus.NO_CONTENT
        public static final String SIGNATURE_INVALID = "701";
        public static final String VALIDATION_ERROR = "704";
    }
}
