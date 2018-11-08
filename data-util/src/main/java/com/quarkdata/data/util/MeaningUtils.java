package com.quarkdata.data.util;


import org.apache.log4j.Logger;

import java.util.*;

/**
 * 语义计算工具类
 */
public class MeaningUtils {

    static Logger logger = Logger.getLogger(MeaningUtils.class);

    private static double THRESHOLD = 0.55; // 语义占比阀值，占比超过该值即可认定


    public static double validProportion(List<Object> objects, int meaning) {
        logger.info("开始计算语义占比");
        Meaning.initialize();
        int count = objects.size();
        for (Object object : objects) {
            if (isObjectMatchMeaning(object, meaning)) {
                Meaning.addNum(meaning);
            }
        }
        double proportion = Meaning.getNum(meaning) / (double) count;
        logger.info("meaning == " + meaning + ",有效占比 == " + proportion);
        logger.info("meaning == " + meaning + ",无效占比 == " + (1 - proportion));
        return proportion;
    }

    /**
     * 语义自动检测
     *
     * @param objects
     */
    public static Integer autoDetectMeanings(List<Object> objects) {
        Meaning.initialize();
        int count = objects.size();
        HashSet<Integer> meaningIndexs = new HashSet<>();
        for (Object object : objects) {
            int meaningIndex = autoDetectMeaning(object);
            Meaning.addNum(meaningIndex);
            meaningIndexs.add(meaningIndex);
        }
        if (!meaningIndexs.isEmpty()) {
            Map<Integer, Double> map = new HashMap<>();
            for (Integer meaningIndex : meaningIndexs) {
                if (null != meaningIndex) {
                    double proportion = Meaning.getNum(meaningIndex) / (double) count;
                    map.put(meaningIndex, proportion);
                }
            }
            double max = Collections.max(map.values());
            if (THRESHOLD <= max) {
                Integer maxMeaning = -1;
                for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                    if (max == entry.getValue()) {
                        maxMeaning = entry.getKey();
                        break;
                    }
                }
                logger.info("自动检测结果：语义：" + maxMeaning + ",占比：" + max);
                return maxMeaning;
            } else {
                return 1;
            }
        }
        return null;
    }


    /**
     * 推测单条数据的语义，根据正则表达式
     *
     * @param object
     * @return
     */
    public static int autoDetectMeaning(Object object) {
        if (null != object) {
            String objStr = object.toString();
            if (RegexValidation.isText(objStr)) {
                if (RegexValidation.isPhoneNumber(objStr)){ // 手机号暂时识别为Text语义
                    return Meaning.TEXT.getIndex();
                }
                if (RegexValidation.isDecimal(objStr)) {
//                    if (RegexValidation.isLatitude(objStr)) {
//                        return Meaning.LATITUDE.getIndex();
//                    }
//                    if (RegexValidation.isLongitude(objStr)) {
//                        return Meaning.LONGITUDE.getIndex();
//                    }
                    return Meaning.DECIMAL.getIndex();
                }
                if (RegexValidation.isInteger(objStr)) {
//                    if (RegexValidation.isBoolean(objStr)) { // 0 1
//                        return Meaning.BOOLEAN.getIndex();
//                    }
                    return Meaning.INTEGER.getIndex();
                }
                if (RegexValidation.isDate(objStr)) {
                    return Meaning.DATE.getIndex();
                }
                if (RegexValidation.isEmail(objStr)) {
                    return Meaning.EMAIL.getIndex();
                }
                if (RegexValidation.isUrl(objStr)) {
                    return Meaning.URL.getIndex();
                }
                if (RegexValidation.isIPAddress(objStr)) {
                    return Meaning.IP.getIndex();
                }
                if (RegexValidation.isBoolean(objStr)) { // true false
                    return Meaning.BOOLEAN.getIndex();
                }
                if (RegexValidation.isArray(objStr)) {
                    return Meaning.ARRAY.getIndex();
                }
                if (RegexValidation.isObject(objStr)) {
                    return Meaning.OBJECT.getIndex();
                }
                return Meaning.TEXT.getIndex();
            }
        }
        return -1;
    }


    /**
     * 判断object是否符合某个语义
     *
     * @param object
     * @param meaning
     * @return true符合 false不符
     */
    public static boolean isObjectMatchMeaning(Object object, int meaning) {
        boolean result = false;
        if (null != object){
            String objStr = object.toString();
            switch (meaning) {
                case 1://TEXT
                    result = RegexValidation.isText(objStr);
                    break;
                case 2://DECIMAL
                    result = RegexValidation.isDecimal(objStr);
                    break;
                case 3://INTEGER
                    result = RegexValidation.isInteger(objStr);
                    break;
                case 4://BOOLEAN
                    result = RegexValidation.isBoolean(objStr);
                    break;
                case 5://DATE
                    result = RegexValidation.isDate(objStr);
                    break;
                case 6://OBJECT
                    result = RegexValidation.isObject(objStr);
                    break;
                case 7://ARRAY
                    result = RegexValidation.isArray(objStr);
                    break;
                case 80://LONGITUDE
                    result = RegexValidation.isLongitude(objStr);
                    break;
                case 81://LATITUDE
                    result = RegexValidation.isLatitude(objStr);
                    break;
//            case 82://COORDINATE todo 坐标点的正则判断
//                return RegexValidation.is(objStr);
//                break;
                case 93://IP
                    result = RegexValidation.isIPAddress(objStr);
                    break;
                case 94://URL
                    result = RegexValidation.isUrl(objStr);
                    break;
                case 95://EMAIL
                    result = RegexValidation.isEmail(objStr);
                    break;
                case 0:
                    logger.info("0");
                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    private enum Meaning {
        LONGITUDE(80, 0), LATITUDE(81, 0), COORDINATE(82, 0), IP(93, 0), URL(94, 0), EMAIL(95, 0),
        TEXT(1, 0), DECIMAL(2, 0), INTEGER(3, 0), BOOLEAN(4, 0), DATE(5, 0), OBJECT(6, 0), ARRAY(7, 0),
        NULL(-1, 0);

        private Integer index; //meaning的编号
        private Integer num; //meaning的数量

        // 构造方法
        private Meaning(int index, int num) {
            this.index = index;
            this.num = num;
        }

        // 数量归零
        public static void initialize() {
            for (Meaning c : Meaning.values()) {
                if (0 != c.getNum()) {
                    c.setNum(0);
                }
            }
        }

        // 获取某个语义的数量
        public static Integer getNum(Integer index) {
            for (Meaning c : Meaning.values()) {
                if (c.getIndex() == index) {
                    return c.num;
                }
            }
            return null;
        }

        // 某个语义数量加一
        public static void addNum(int index) {
            for (Meaning c : Meaning.values()) {
                if (c.getIndex() == index) {
                    c.num++;
                }
            }
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    /**
     * test
     */
    public static void main(String[] args) {
//        int a = 0; // boolean
//        String a = "192.168.7.1:12345"; //ip
//        float a = 123.123f; //longitude
//         String a = "http://192.168.7.45:8080/ajax/device/delete?id=2"; // url
//        String a = "KobeBryant@thundersoft.com"; //email
//        long a = 123456789L; //integer
//        System.out.println(autoDetectMeaning(a));
        List<Object> list = new ArrayList<>();
        list.add("192.168.7.1:12345");
        list.add("192.168.7.1:12345");
        list.add("192.168.7.1:12345");
        list.add("192.168.7.1:12345");
        list.add("192.168.7.1:12345");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("KobeBryant@thundersoft.com");
        list.add("12345");
        list.add("2312");
        list.add("3213");
        list.add("312");
        list.add("534");
        list.add("543");
        list.add("14");
        list.add("543");
        list.add("rqr");
        list.add("rq");
        list.add("21");
        list.add("43");
        list.add("44");
        list.add("444");
        list.add("");
        list.add(null);

//        autoDetectMeanings(list);
//        validProportion(list,-1);

//        System.out.println(Meaning.getNum(93));
//        System.out.println(Meaning.getNum(95));
//        System.out.println(Meaning.getNum(3));
//        System.out.println(Meaning.getNum(1));
        System.out.println(new Date().toString());
    }
}
