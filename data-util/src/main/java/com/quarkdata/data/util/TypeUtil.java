package com.quarkdata.data.util;

import org.apache.log4j.Logger;

import java.sql.Types;

/**
 * 数据类型转换
 *
 * @author luohl
 */
public class TypeUtil {

    static Logger logger = Logger.getLogger(TypeUtil.class);

    /**
     * Java和Cassandra数据类型转换
     *
     * @param type Java数据类型
     * @return Cassandra数据类型
     * @author luohl
     */
    public static String typeConversion(String type) {
        switch (type) {
            case "0":
                // float
                type = "float";
                break;
            case "1":
                // double
                type = "double";
                break;
            case "2":
                // short 16位有符号长
                type = "smallint";
                break;
            case "3":
                // int 32位有符号长
                type = "int";
                break;
            case "4":
                // long 64位有符号长
                type = "bigint";
                break;
            case "5":
                // boolean
                type = "boolean";
                break;
            case "6":
            case "7":
                // String : UTF8编码字符串
                type = "text";
                break;
            case "8":
                // date
                type = "datetime";
                break;
            default:
                type = "unKnow";
                break;
        }
        return type;
    }

    /**
     * mysql数据类型映射到dataset
     *
     * @param type
     * @return
     */
    public static String mysql2DataSet(String type) {

        String dataSetType = "";
        switch (type) {
            case "TINYINT":
                dataSetType = "0"; // tinyint(8 bit)
                break;
            case "SMALLINT":
                dataSetType = "1"; // smallint(16 bit)
                break;
            case "INT":
                dataSetType = "2"; // int
                break;
            case "BIGINT":
                dataSetType = "3"; // bigint(64 bit)
                break;
            case "FLOAT":
                dataSetType = "4"; // float
                break;
            case "DOUBLE":
            case "DECIMAL":
                dataSetType = "5"; // double
                break;
            // case "5":
            // dataSetType = "6"; //boolean
            // break;
            case "VARCHAR":
            case "CHAR":
            case "LONGTEXT":
            case "TEXT":
                dataSetType = "7"; // string
                break;
            case "DATE":
            case "DATETIME":
            case "TIME":
            case "TIMESTAMP":
                dataSetType = "8"; // date
                break;
            default:
                dataSetType = "7"; // string
                break;
        }
        logger.info("类型映射 [" + type + " ==> " + dataSetTypeConv(dataSetType) + "]");
        return dataSetType;
    }

    /**
     * dataset映射到mysql数据类型
     *
     * @param type
     * @return
     */
    public static String dataSet2Mysql(String type) {
        String mysqlType = "";
        switch (type) {
            case "0":
                mysqlType = "TINYINT"; // tinyint(8 bit)
                break;
            case "1":
                mysqlType = "SMALLINT"; // smallint(16 bit)
                break;
            case "2":
                mysqlType = "INT"; // int
                break;
            case "3":
                mysqlType = "BIGINT"; // bigint(64 bit)
                break;
            case "4":
                mysqlType = "FLOAT"; // float
                break;
            case "5":
                mysqlType = "DOUBLE"; // double
                break;
            // case "5":
            // dataSetType = "6"; //boolean
            // break;
            case "7":
                mysqlType = "VARCHAR"; // string
                break;
            case "8":
                mysqlType = "DATETIME"; // date
                break;
            default:
                mysqlType = "VARCHAR"; // string
                break;
        }
        return mysqlType;
    }


    /**
     * 输入列类型编号，输出列类型名称
     *
     * @param typeCode
     * @return
     * @see java.sql.Types
     */
    public static String sqlTypesConvert(int typeCode) {

        String columnTypeName = "";
        switch (typeCode) {
            case Types.TINYINT:
            case Types.BIT:
                columnTypeName = "TINYINT"; // tinyint(8 bit)
                break;
            case Types.SMALLINT:
                columnTypeName = "SMALLINT"; // smallint(16 bit)
                break;
            case Types.INTEGER:
                columnTypeName = "INT"; // int
                break;
            case Types.BIGINT:
                columnTypeName = "BIGINT"; // bigint(64 bit)
                break;
            case Types.FLOAT:
            case Types.REAL:
                columnTypeName = "FLOAT"; // float
                break;
            case Types.DOUBLE:
                columnTypeName = "DOUBLE"; // double
                break;
            case Types.DECIMAL:
                columnTypeName = "DECIMAL";
                break;
            case Types.VARCHAR:
                columnTypeName = "VARCHAR";
                break;
            case Types.CHAR:
                columnTypeName = "CHAR";
                break;
            case Types.LONGVARCHAR:
                columnTypeName = "LONGTEXT";
                break;
            case Types.DATE:
                columnTypeName = "DATE";
                break;
            case Types.TIME:
                columnTypeName = "DATETIME";
                break;
            case Types.TIMESTAMP:
                columnTypeName = "TIMESTAMP";
                break;
            case Types.BLOB:
            case Types.LONGVARBINARY:
                columnTypeName = "BLOB";
                break;
        }
        return columnTypeName;
    }


    /**
     * cassandra数据类型映射到dataset
     *
     * @param type
     * @return
     */
    public static String cassandra2DataSet(String type) {

        String dataSetType = "";
        switch (type) {
            case "int":
                dataSetType = "2";
                break;
            case "varint":
            case "counter":
            case "bigint":
                dataSetType = "3";
                break;
            case "float":
                dataSetType = "4";
                break;
            case "double":
            case "decimal":
                dataSetType = "5";
                break;
            case "boolean":
                dataSetType = "6";
                break;
            case "timeuuid":
            case "blob":
            case "uuid":
            case "text":
            case "varchar":
            case "ascii":
            case "inet":
                dataSetType = "7";
                break;
            case "timestamp":
                dataSetType = "8";
                break;
            case "list":
                dataSetType = "9";
                break;
            case "set":
                dataSetType = "10";
                break;
            case "map":
                dataSetType = "11";
                break;
            default:
                dataSetType = "7";
                break;
        }
        return dataSetType;
    }

    /**
     * dataset映射到cassandra数据类型
     *
     * @param type
     * @return
     */
    public static String dataSet2Cassandra(String type) {

        String cassandraType = "";
        switch (type) {
            case "2":
                cassandraType = "int";
                break;
            case "3":
                cassandraType = "bigint";
                break;
            case "4":
                cassandraType = "float";
                break;
            case "5":
                cassandraType = "double";
                break;
            case "6":
                cassandraType = "boolean";
                break;
            case "7":
                cassandraType = "text";
                break;
            case "8":
                cassandraType = "timestamp";
                break;
            case "9":
                cassandraType = "list";
                break;
            case "10":
                cassandraType = "set";
                break;
            case "11":
                cassandraType = "map";
                break;
            default:
                cassandraType = "text";
                break;
        }
        return cassandraType;
    }

    /**
     * dataSetType 自身映射
     *
     * @param typeCode 编号
     * @return type name
     */
    public static String dataSetTypeConv(String typeCode) {

        String typeName = "";
        switch (typeCode) {
            case "0":
                typeName = "tinyint(8 bit)";
                break;
            case "1":
                typeName = "smallint(16 bit)";
                break;
            case "2":
                typeName = "int";
                break;
            case "3":
                typeName = "bigint(64 bit)";
                break;
            case "4":
                typeName = "float";
                break;
            case "5":
                typeName = "double";
                break;
            case "6":
                typeName = "boolean";
                break;
            case "7":
                typeName = "string";
                break;
            case "8":
                typeName = "date";
                break;
            default:
                typeName = "string";
                break;
        }
        return typeName;
    }

    public enum Type {
        TINYINT("0", "tinyint(8 bit)", new String[]{"TINYINT"}, new String[]{"tinyint"}),
        SMALLINT("1", "smallint(16 bit)", new String[]{"SMALLINT"}, new String[]{"smallint"}),
        INT("2", "int", new String[]{"INT"}, new String[]{"int"}),
        BIGINT("3", "bigint(64 bit)", new String[]{"BIGINT"}, new String[]{"bigint"}),
        FLOAT("4", "float", new String[]{"FLOAT"}, new String[]{"float"}),
        DOUBLE("5", "double", new String[]{"DOUBLE","DECIMAL"}, new String[]{"double"}),
        BOOLEAN("6", "boolean", new String[]{"BOOLEAN"}, new String[]{"boolean"}),
        STRING("7", "string", new String[]{"VARCHAR", "CHAR", "TEXT", "LONGTEXT","BLOB"}, new String[]{"text", "varchar"}),
        DATE("8", "date", new String[]{"TIMESTAMP","DATE", "DATETIME", "TIME"}, new String[]{"timestamp"});

        private String typeCode; // type在数据库中存储的值 0:tinyint(8 bit),
        // 1:smallint(16 bit), 2:int, 3:bigint(64
        // bit), 4:float, 5:double, 6:boolean,
        // 7:string, 8:date
        private String dataSetType; // 数据集的type名
        private String[] mysqlTypes;// mysql的type名
        private String[] cassandraTypes;// cassandra的type名

        // 构造方法
        private Type(String typeCode, String dataSetType, String[] mysqlTypes, String[] cassandraTypes) {
            this.typeCode = typeCode;
            this.dataSetType = dataSetType;
            this.mysqlTypes = mysqlTypes;
            this.cassandraTypes = cassandraTypes;
        }

        // 根据dataset类型代号获取dataset类型名称
        public static String getDatasetTypeByTypecode(String typeCode) {
            for (Type type : Type.values()) {
                if (typeCode.equals(type.getTypeCode())) {
                    return type.dataSetType;
                }
            }
            return null;
        }

        // 根据mysql类型名称获取dataset类型名称
        public static String getDatasetTypeByMysqlType(String mysqlType) {
            for (Type type : Type.values()) {
                for (String s : type.getMysqlTypes()) {
                    if (mysqlType.equals(s)) {
                        return type.dataSetType;
                    }
                }
            }
            return null;
        }

        // 根据mysql类型名称获取cassandra类型名称
        public static String getCassandraTypeByMysqlType(String mysqlType) {
            for (Type type : Type.values()) {
                for (String s : type.getMysqlTypes()) {
                    if (mysqlType.equals(s)) {
                        return type.cassandraTypes[0];
                    }
                }
            }
            return null;
        }

        // 根据cassandra类型名称获取dataset类型名称
        public static String getDatasetTypeByCassandraType(String cassandraType) {
            for (Type type : Type.values()) {
                for (String s : type.getCassandraTypes()) {
                    if (cassandraType.equals(s)) {
                        return type.dataSetType;
                    }
                }
            }
            return null;
        }

        // 根据cassandra类型名称获取mysql类型名称
        public static String getMysqlTypeByCassandraType(String cassandraType) {
            for (Type type : Type.values()) {
                for (String s : type.getCassandraTypes()) {
                    if (cassandraType.equals(s)) {
                        return type.mysqlTypes[0];
                    }
                }
            }
            return null;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getDataSetType() {
            return dataSetType;
        }

        public void setDataSetType(String dataSetType) {
            this.dataSetType = dataSetType;
        }

        public String[] getMysqlTypes() {
            return mysqlTypes;
        }

        public void setMysqlTypes(String[] mysqlTypes) {
            this.mysqlTypes = mysqlTypes;
        }

        public String[] getCassandraTypes() {
            return cassandraTypes;
        }

        public void setCassandraTypes(String[] cassandraTypes) {
            this.cassandraTypes = cassandraTypes;
        }
    }

    public static void main(String[] args) {

//        logger.info(Type.getDatasetTypeByMysqlType("TEXT"));
//        logger.info(Type.getCassandraTypeByMysqlType("VARCHAR"));
//
//        logger.info(Type.getDatasetTypeByCassandraType("bigint"));
//        logger.info(Type.getMysqlTypeByCassandraType("varchar"));
    }
}
