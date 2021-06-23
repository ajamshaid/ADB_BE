package com.infotech.adb.util;

import com.infotech.adb.exceptions.CustomException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SuppressWarnings(value = {"rawtypes"})
public class AppUtility {

    private static ResourceBundle resourceBundle = null;
    public static final int defaultDecimalPrecision = 2;
    private static int decimalPercision = defaultDecimalPrecision;
    private static final String DATE_FORMAT = "yyyyMMdd";



    // Signature field will contain base64 of the SHA256 hashed value of data field

    public static String buildSignature(String data) {
        return new String(Base64.encodeBase64(DigestUtils.sha256(data)));
    }

    public static String formatedDate(Date date) {

        String dt = "";
        if(!isEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            dt= sdf.format(date);
        }
        return dt;

    }

    public static Date getDateFromString(String date) {
        Date dt = null;
        if(!isEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            try {
                dt= sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dt;
    }

    public static String formatZonedDateTime(String format, ZonedDateTime zonedDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(zonedDateTime);

    }

    public static String getCurrentTimeStampString() {
        return formatedDate(Calendar.getInstance().getTime());
    }

    public static Timestamp getCurrentTimeStamp() {

        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public static String getDeleteStamp() {
        return "_Del_" + Calendar.getInstance().getTimeInMillis();
    }

    public static int getDecimalPercision() {
        return decimalPercision;
    }

    public static void setDecimalPercision(int decimalPercision) {
        AppUtility.decimalPercision = decimalPercision;
    }

    public static ResourceBundle getResourceBundle() {
        if (null == resourceBundle) {
            resourceBundle = ResourceBundle.getBundle("GUIConstants.MESSAGE_BUNDLE", Locale.US);
        }
        return resourceBundle;
    }

    public static BigDecimal covertToBigDecimal(String val) {
        if (isEmpty(val) && isBigDecimal(val)) {
            return new BigDecimal(val);
        }
        return BigDecimal.ZERO;
    }

    public static Long covertToLong(String val) {
        if (isEmpty(val) && isNumeric(val)) {
            return new Long(val);
        }
        return new Long("0");
    }

    public static BigInteger covertToBigInteger(String val) {
        if (isEmpty(val) && isNumeric(val)) {
            return new BigInteger(val);
        }
        return BigInteger.ZERO;
    }

    public static Double covertToDouble(String val) {
        if (isEmpty(val) && isDouble(val)) {
            return new Double(val);
        }
        return new Double("0");
    }

    public static Integer covertToInteger(String val) {
        if (isEmpty(val) && isNumeric(val)) {
            return new Integer(val);
        }
        return new Integer("0");
    }

    public static String formatDoubleToStringWithPrecision(Double d) {
        String str = "###,###,###.####";
        if (AppUtility.getDecimalPercision() == 2) {
            str = "###,###,###.##";
        } else if (AppUtility.getDecimalPercision() == 3) {
            str = "###,###,###.###";
        }
        DecimalFormat df = new DecimalFormat(str);
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(d);
    }

    public static String formatDoubleToStringWithoutCommasWithPrecision(Double d) {
        String str = "#########.####";
        if (AppUtility.getDecimalPercision() == 2) {
            str = "#########.##";
        } else if (AppUtility.getDecimalPercision() == 3) {
            str = "#########.###";
        }
        DecimalFormat df = new DecimalFormat(str);
        df.setRoundingMode(RoundingMode.FLOOR);
        // String str = "%1$."+UtilityFunctions.getDecimalPercision()+"f";
        // return String.format(str,d);
        return df.format(d);
    }

    public static String formatBigDecimalToStringWithoutCommasWithPrecision(BigDecimal d) {
        String str = "%1$." + AppUtility.getDecimalPercision() + "f";
        return String.format(str, d);
    }

    public static String formatDoubleToString(Double d) {
        return String.format("%1$,.4f", d);
    }

    public static String formatDoubleToStringWithoutCommas(Double d) {
        return String.format("%1$.4f", d);
    }

    public static String formatBigDecimalToString(BigDecimal d) {
        return String.format("%1$,.4f", d);
    }

    public static String formatIntegerToString(Integer d) {
        return String.format("%,d", d);
    }

    public static String formatLongToString(Long d) {
        return String.format("%,d", d);
    }

    public static String formatBigIntegerToString(BigInteger d) {
        return String.format("%,d", d);
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof Optional<?> && !((Optional<?>) object).isPresent()) {
            return true;
        }

        if (object instanceof String) {
            String objString = object.toString();
            if (objString.trim().length() <= 0 || objString.trim().equalsIgnoreCase("null")) {
                return true;
            }
        } else if (object instanceof StringBuilder) {
            StringBuilder stringBuilder = (StringBuilder) object;
            if (stringBuilder.toString().trim().length() <= 0) {
                return true;
            }
            // Check for List and Sets
        } else if (object instanceof ArrayList<?> || object instanceof Collection<?>) {
            if (((Collection) object).isEmpty()) {
                return true;
            }
        } else if (object instanceof Map<?, ?>) {
            if (((Map) object).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumeric(String value) {
        try {
            new BigInteger(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isBigDecimal(String value) {
        try {
            new BigDecimal(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String value) {
        try {
            new Double(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings({"deprecation", "unused"})
    public static boolean isDate(String value) {
        try {
            Date date = new Date(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*
     * public static String encrypt(String input) { MessageDigest mdEnc = null;
     * try { mdEnc = MessageDigest.getInstance("MD5");/* Encryption } catch
     * (NoSuchAlgorithmException ex) {
     * Logger.getLogger(AppUtitlity2.class.getName()).log(Level.SEVERE, null,
     * ex); } // algorithm mdEnc.update(input.getBytes(), 0, input.length());
     * String encryptedPassword = new BigInteger(1,
     * mdEnc.digest()).toString(16); return encryptedPassword; }
     *
     *
     * public static String formatWrappersToString(Object obj) { String str =
     * ""; if (obj != null) { if (obj instanceof Double) { str =
     * formatDoubleToString((Double) obj); } else if (obj instanceof BigDecimal)
     * { str = formatBigDecimalToString((BigDecimal) obj); } else if (obj
     * instanceof Integer) { str = formatIntegerToString((Integer) obj); } else
     * if (obj instanceof BigInteger) { str =
     * formatBigIntegerToString((BigInteger) obj); } else if (obj instanceof
     * Long) { str = formatLongToString((Long) obj); } else if(obj instanceof
     * Date){ str = DateTimeUtils.SIMPLE_DATE_TIME_FORMAT.format(obj); }else {
     * str = obj.toString(); } } return str; }
     *
     * public static String formatDateToString(Object obj, boolean isTimeAlso) {
     * String str = ""; if (obj != null) { if (obj instanceof Timestamp ) { str
     * = DateTimeUtils.SIMPLE_DATE_TIME_FORMAT.format(obj); } else if(obj
     * instanceof Date){ str = isTimeAlso ?
     * DateTimeUtils.SIMPLE_DATE_TIME_FORMAT.format(obj) :
     * DateTimeUtils.SIMPLE_DATE_FORMAT.format(obj); } } return str; }
     *
     * /** Change the provided date to the server's default time zone - GMT.
     *
     * @param date
     *
     * @return
     *
     * @throws Exception
     */
    public static Date convertDateToDefaultTimeZone(Date date) throws Exception {
        TimeZone zone = TimeZone.getTimeZone("GMT");
        Calendar first = Calendar.getInstance(zone);
        first.setTimeInMillis(date.getTime());

        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, first.get(Calendar.YEAR));
        output.set(Calendar.MONTH, first.get(Calendar.MONTH));
        output.set(Calendar.DAY_OF_MONTH, first.get(Calendar.DAY_OF_MONTH));
        output.set(Calendar.HOUR_OF_DAY, first.get(Calendar.HOUR_OF_DAY));
        output.set(Calendar.MINUTE, first.get(Calendar.MINUTE));
        output.set(Calendar.SECOND, first.get(Calendar.SECOND));

        output.set(Calendar.SECOND, first.get(Calendar.SECOND));
        output.set(Calendar.MILLISECOND, first.get(Calendar.MILLISECOND));

        return output.getTime();
    }

    public static String encodeUTF(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Nullify time in Calander object
     *
     * @param c
     * @return
     * @throws Exception
     */
    public static Calendar nullifyTime(Calendar c) throws CustomException {
        Calendar cal = c;

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    public static String generateRandomUniqString() {
        SecureRandom random = new SecureRandom();
        return "PSW"+( new BigInteger(60, random).toString(18));
    }

    /**
     * Private Methods
     *
     * @param x
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private static byte[] computeHash(String x) throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    @SuppressWarnings("unused")
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
}