package com.quarkdata.data.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证工具类
 *
 * @author jiadao
 */
public class RegexValidation {


    /**
     * 手机号
     */
    public static final String PHONE_NUMBER = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    /**
     * Email正则表达式="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
     */
    public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     */
    public static final String IP_ADDRESS = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";

    /**
     * 匹配IP地址和端口(简单匹配，格式，如：192.168.1.1:8080，127.0.0.1:12345，没有匹配IP段的大小)
     */
    public static final String IP_ADDRESS_WITH_PORT = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?)):\\d{2,5}";

    /**
     * URL正则表达式
     * 匹配 http www ftp
     */
//    public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" +
//            "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*" +
//            "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" +
//            "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
    public static final String URL = "^(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]$";
    /**
     * 经度正则表达式，经度整数部分为0-180,小数部分为0到6位
     */
    public static final String LONGITUDE = "^(\\-|\\+)?(((\\d|[1-9]\\d|1[0-7]\\d|0{1,3})\\.\\d{0,6})|(\\d|[1-9]\\d|1[0-7]\\d|0{1,3})|180\\.0{0,6}|180)$";

    /**
     * 纬度正则表达式，纬度整数部分为0-90,小数部分为0到6位
     */
    public static final String LATITUDE = "^(\\-|\\+)?([0-8]?\\d{1}\\.\\d{0,6}|90\\.0{0,6}|[0-8]?\\d{1}|90)$";

    /**
     * 数组，匹配被中括号包裹的
     */
    public static final String ARRAY = "(\\[).*?(\\])";

    /**
     * 对象，匹配被大括号包裹的
     */
    public static final String OBJECT = "(\\{).*?(\\})";

    /**
     * 常用日期时间 yyyy-MM-dd HH:mm:ss格式 或 yyyy-MM-dd格式
     */
    public static final String DATE_COMMON = "(^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$)|(^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2})";

    /**
     * ISO日期时间 yyyy-MM-ddTHH:mm:ss.xxxZ格式 例如：2018-05-28T12:34:56.000Z
     */
    public static final String DATE_ISO = "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}T\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\.\\d{3}Z$";

    /**
     * 默认日期时间字符串格式 EEE MMM dd HH:mm:ss z yyyy格式 例如：Tue Feb 06 15:28:00 CST 2018
     */
    public static final String DATE_STRING_DEFAULT = "^\\S{3}\\s+\\S{3}\\s+\\d{2}\\s+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\s+\\S{1,}\\s+\\d{4}$";

    /**
     * 布尔 true false 不区分大小写 数字0和1
     */
    public static final String BOOLEAN = "(?i:true|false|0|1)";

    /**
     * 整数
     */
    public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";

    /**
     * 浮点数
     */
    public static final String DECIMAL = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+)$";

    /**
     * 任意字符，不包括换行,空白
     */
    public static final String TEXT = "(.*?)";


    /**
     * 判断字段是否为手机号 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isPhoneNumber(String str) {
        return Regular(str, PHONE_NUMBER);
    }

    /**
     * 判断字段是否为Email 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str) {
        return Regular(str, EMAIL);
    }

    /**
     * 判断字段是否为ip地址 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isIPAddress(String str) {
        return Regular(str, IP_ADDRESS) | Regular(str, IP_ADDRESS_WITH_PORT);
    }

    /**
     * 判断是否为Url 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isUrl(String str) {
        return Regular(str, URL);
    }

    /**
     * 判断是否为经度 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isLongitude(String str) {
        return Regular(str, LONGITUDE);
    }

    /**
     * 判断是否为纬度 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isLatitude(String str) {
        return Regular(str, LATITUDE);
    }

    /**
     * 判断是否为Array 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isArray(String str) {
        return Regular(str, ARRAY);
    }

    /**
     * 判断是否为Object 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isObject(String str) {
        return Regular(str, OBJECT);
    }

    /**
     * 判断是否为Date 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isDate(String str) {
        return Regular(str, DATE_COMMON) | Regular(str, DATE_ISO) | Regular(str,DATE_STRING_DEFAULT);
    }

    /**
     * 判断是否为布尔型 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isBoolean(String str) {
        return Regular(str, BOOLEAN);
    }

    /**
     * 判断是否为整数 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isInteger(String str) {
        return Regular(str, INTEGER);
    }

    /**
     * 判断是否为小数 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isDecimal(String str) {
        return Regular(str, DECIMAL);
    }

    /**
     * 判断是否为字符串 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isText(String str) {
        return Regular(str, TEXT);
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     *
     * @param str     匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    private static boolean Regular(String str, String pattern) {
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * test
     */
    public static void main(String[] args) {
//        System.out.println(isEmail("jiadao0703@thundersoft.com")); //t
//        System.out.println(isEmail("jiadao0703@thundersoft")); //f
//        System.out.println(isEmail("jiadao0703@"));//f


//        System.out.println(isUrl("www.baidu.com"));//F
//        System.out.println(isUrl("http://alibaba.cn"));//t
//        System.out.println(isUrl("ftp://aa"));//t
//        System.out.println(isUrl("192.168.5.2"));//F


//        System.out.println(isIPAddress("192.168.2.97:8080"));//t
//        System.out.println(isIPAddress("192.168.2.97:808011"));//f
//        System.out.println(isIPAddress("192.168.2.97"));//t
//        System.out.println(isIPAddress("921.681.21.971"));//t
//        System.out.println(isIPAddress("1921.681.21.971"));//f
//
//        System.out.println(isLongitude("192.12332"));//f
//        System.out.println(isLongitude("135.12345"));//t
//        System.out.println(isLongitude("-99.122"));//t

//        System.out.println(isLatitude("90"));//t
//        System.out.println(isLatitude("12.23"));//t
//        System.out.println(isLatitude("-12.22222"));//t
//        System.out.println(isLatitude("112.22222"));//f


//        System.out.println(isArray("[]"));//t
//        System.out.println(isArray("[1]"));//t
//        System.out.println(isArray("[1,2]"));//t
//        System.out.println(isArray("[1,2,,]"));//t
//        System.out.println(isArray("d[1,2,,]a"));//f


//        System.out.println(isObject("{}"));//T
//        System.out.println(isObject("{DD.A}"));//T
//        System.out.println(isObject("{'SS':'DDD'}"));//T
//        System.out.println(isObject("SS{'SS':'DDD'}"));//f

//        System.out.println(isDate("1995-12-22 12:34:56"));//T
//        System.out.println(isDate("199-12-22 12:34:56"));//F
//        System.out.println(isDate("1995-1-2 12:34:56"));//T
//        System.out.println(isDate("1995-01-22 02:04:06"));//T
//        System.out.println(isDate("1995-01-33 12:34:56"));//T
//        System.out.println(isDate("1995-01-22 12:34:6"));//T
//        System.out.println(isDate("1995-01-22"));//f
//        System.out.println(isDate("1995-01-22T12:34:56.000Z"));//t
//        System.out.println(isDate("Tue Feb 06 15:28:00 CST 2018"));//t

//         System.out.println(isBoolean("true"));//T
//         System.out.println(isBoolean("false"));//T
//         System.out.println(isBoolean("True"));//T
//         System.out.println(isBoolean("TRUE"));//T
//         System.out.println(isBoolean("0"));//T
//         System.out.println(isBoolean("1"));//T
//         System.out.println(isBoolean("2"));//F


//         System.out.println(isInteger("0"));//T
//         System.out.println(isInteger("2"));//T
//         System.out.println(isInteger("-2"));//T
//         System.out.println(isInteger("2.0"));//F
//         System.out.println(isInteger("02"));//F
//         System.out.println(isInteger("2112222222222211111"));//T

//         System.out.println(isDecimal("0"));//F
//         System.out.println(isDecimal("0.0"));//T
//         System.out.println(isDecimal("2.0"));//T
//         System.out.println(isDecimal("-2.0"));//T
//         System.out.println(isDecimal("2.0123123143354222"));//T
//         System.out.println(isDecimal("02.0"));//F
//         System.out.println(isDecimal("2.0.0"));//F
//         System.out.println(isDecimal("1000000.0001"));//T

//         System.out.println(isText("1995-01-22T12:34:56.000Z"));//T
//         System.out.println(isText("1000000.0001"));//T
//         System.out.println(isText("12345"));//T
//         System.out.println(isText("0"));//T
//         System.out.println(isText("true"));//T
//         System.out.println(isText(" "));//F
//         System.out.println(isText(""));//F
//         System.out.println(isText("null"));//T
//         System.out.println(isText(null));//F

//        System.out.println(isPhoneNumber(null));//F
//        System.out.println(isPhoneNumber(""));//F
//        System.out.println(isPhoneNumber("12345456677"));//F
//        System.out.println(isPhoneNumber("13011262330"));//T
//        System.out.println(isPhoneNumber("130112623301"));//F
    }
}
