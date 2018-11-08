package com.quarkdata.data.util.db;


import com.quarkdata.data.model.dataobj.DatasetFilter;

import java.util.List;

/**
 * dataset_filter转查询语句
 */
public class QueryStatementUtils {

//    /**
//     * 转换成mysql的查询语句
//     *
//     * @param tableName        表名称
//     * @param sampleType       采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））
//     * @param sampleTypeValue  采样方式对应值（采样个数、采样比例）默认采样个数10000
//     * @param sampleFilterType 采样过滤方式（0：以下情况均符合，1：至少有一种情况符合）
//     * @param datasetFilters   具体过滤方案
//     * @return
//     */
//    public static String convertToMysqlQueryStatement(String tableName, String sampleType, Integer sampleTypeValue, String sampleFilterType, List<DatasetFilter> datasetFilters) {
//        StringBuffer query = new StringBuffer();
//
//        query.append("select * from ");
//        query.append(tableName);
//
//        // 组装Where语句
//        query.append(makeWheres(sampleFilterType,datasetFilters));
//
//
//
//
//        return query.toString();
//    }


    /**
     * 数据抽样
     *
     * @param sampleType      采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））
     * @param sampleTypeValue 采样方式对应值（采样个数、采样比例）默认采样个数10000 比例先转化为数量
     * @return
     */
    public static StringBuffer makeLimit(String sampleType, Integer sampleTypeValue) {
        StringBuffer query = new StringBuffer();

         /*
        mysql随机取数据示例：
        1、SELECT *
                FROM `table` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `table`)-(SELECT MIN(id) FROM `table`))+(SELECT MIN(id) FROM `table`)) AS id) AS t2
        WHERE t1.id >= t2.id
        ORDER BY t1.id LIMIT 10;

        2、SELECT *
                FROM `table`
        WHERE id >= (SELECT FLOOR( MAX(id) * RAND()) FROM `table` ) LIMIT 10;

        3、SELECT * FROM `table` ORDER BY RAND() LIMIT 10;效率最低*/
        //组装抽样语句
        switch (sampleType) {
            case "0": // 前n条记录
                query.append(" limit ");
                query.append(sampleTypeValue);
                break;
            case "1": // 随机n条记录（按个数）
            case "2": // 随机n条记录（按比例），由比例转换成数量
                query.append(" order by rand() limit ");
                query.append(sampleTypeValue);
                break;
            default:
                break;
        }

        return query;
    }

    /**
     * 数据过滤，
     * 组合多条where查询语句，用and或or连接
     *
     * @param sampleFilterType 采样过滤方式（0：以下情况均符合，1：至少有一种情况符合）
     * @param datasetFilters
     * @return
     */
    public static StringBuffer makeWheres(String sampleFilterType, List<DatasetFilter> datasetFilters) {

        StringBuffer where = new StringBuffer();
        // 组装Where语句
        if (null != datasetFilters && !datasetFilters.isEmpty()) {
            where.append(" where ");
            for (DatasetFilter filter : datasetFilters) {
                String columnName = filter.getColumnName(); // 列名
                String operator = filter.getOperator(); // 条件(0：不为空，1：为空，2：==，3：!=，4：>，5：<，6：>=，7：<=，8：等于，9：不等于，10：包含，11：介于，12：早于，13：晚于，14：true，15：false)；
                String value1 = filter.getValue1();
                String value2 = filter.getValue2();
                where.append(makeOneWhere(columnName, operator, value1, value2));
                if ("0".equals(sampleFilterType)) {// 查询情况均符合
                    where.append(" and ");
                } else if ("1".equals(sampleFilterType)) {// 至少有一种情况符合
                    where.append(" or ");
                }
            }
            if ("0".equals(sampleFilterType)) {// 去掉尾部的and
                where.delete(where.length() - 5, where.length());
            } else if ("1".equals(sampleFilterType)) {// 去掉尾部的or
                where.delete(where.length() - 4, where.length());
            }
        }

        return where;
    }

    /**
     * 拼装一条where查询条件
     *
     * @param columnName 字段名
     * @param condition  查询条件运算符代号
     * @param value1
     * @param value2
     * @return where语句
     */
    private static StringBuffer makeOneWhere(String columnName, String condition, String value1, String value2) {
        StringBuffer where = new StringBuffer();
        where.append(columnName);
        switch (condition) {
            case "0": // 不为空
                where.append(" is not null and ");
                where.append(columnName);
                where.append(" != '' ");
                break;
            case "1": // 为空
                where.append(" is null or ");
                where.append(columnName);
                where.append(" = '' ");
                break;
            case "2": // ==
            case "8": // 等于
                where.append(" = ");
                where.append(value1);
                break;
            case "3": // !=
            case "9": // 不等于
                where.append(" != ");
                where.append(value1);
                break;
            case "4": // >
                where.append(" > ");
                where.append(value1);
                break;
            case "5": // <
                where.append(" < ");
                where.append(value1);
                break;
            case "6": // >=
                where.append(" >= ");
                where.append(value1);
                break;
            case "7": // <=
                where.append(" <= ");
                where.append(value1);
                break;
            case "10":// 字符串包含
                where.append(" like '%");
                where.append(value1);
                where.append("%' ");
                break;
            case "11":// 介于
                where.append(" between '");
                where.append(value1);
                where.append("' and '");
                where.append(value2);
                where.append("'");
                break;
            case "12":// 早于
                where.append(" < '");
                where.append(value1);
                where.append("'");
                break;
            case "13":// 晚于
                where.append(" > '");
                where.append(value1);
                where.append("'");
                break;
            case "14":// true
                break;
            case "15":// false
                break;
            default:
                break;
        }
        return where;
    }
}
