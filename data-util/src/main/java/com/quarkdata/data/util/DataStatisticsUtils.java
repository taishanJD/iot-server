package com.quarkdata.data.util;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * 数据统计工具类，统计数据的语义占比，最值，均值，最大长度等
 */
public class DataStatisticsUtils {

    static Logger logger = Logger.getLogger(DataStatisticsUtils.class);

    public static Map<String, Object> dataStatistics(List<Object> objects) {

        Map<String, Object> result = new HashMap<>();

        int totalCount = objects.size(); //总数量

        // object长度集合
        List<Integer> objLengths = new ArrayList<>();
        String maxLength = ""; //最大长度

        double nullProportion = 1; //空值占比
        int nullCount = 0; //空值数量

        // 计算最大最小平均值时只取有效值，即从objects中过滤出整数或小数来
        List<Double> validNumberValues = new ArrayList<>();
        String maxValue = "";
        String minValue = "";
        String avgValue = "";

        if (null != objects && !objects.isEmpty()) {
            for (Object object : objects) {
                if (null == object) {
                    nullCount++;
                } else {
                    String objStr = object.toString();

                    Integer objStrLength = objStr.length();
                    objLengths.add(objStrLength);

                    if (StringUtils.isBlank(objStr)) {
                        nullCount++;
                    }

                    if (RegexValidation.isInteger(objStr) || RegexValidation.isDecimal(objStr)) {
                        Double validNumberValue = Double.parseDouble(objStr);
                        validNumberValues.add(validNumberValue);
                    }

                }
            }

            if (objLengths.size() > 0){
                maxLength = Collections.max(objLengths).toString();
            } else {
                maxLength = "0";
            }

            nullProportion = nullCount / (double) totalCount;

            /***********计算最值与平均值开始*****************************************************/
            int validNumberCount = validNumberValues.size();
            if (0 != validNumberCount){
                double sum = 0;
                for (Double validValue : validNumberValues) {
                    sum += validValue;
                }
                maxValue = Collections.max(validNumberValues).toString();
                minValue = Collections.min(validNumberValues).toString();
                avgValue = String.valueOf(sum / (double) validNumberCount);
            }
            /***********计算最值与平均值结束******************************************************/
        }

        result.put("maxLength",maxLength);
        result.put("maxValue",maxValue);
        result.put("minValue",minValue);
        result.put("avgValue",avgValue);
        result.put("nullProportion",nullProportion);
        logger.info("统计结果：：" + result);

        return result;
    }

}
