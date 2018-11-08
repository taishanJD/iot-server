package com.quarkdata.data.util.db;


import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.util.PropertiesUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MySQL,Cassandra,Hive数据导入到mongo中
 * @author jiadao
 */
public class DB2MongoUtil {

    private static Logger logger = Logger.getLogger(DB2MongoUtil.class);



    public static Map<String, String> props = PropertiesUtils.prop;
    public static void main(String[] args) {

    }

    /**
     * 从mysql中抽取数据存到mongo中
     * @param dataset 取源数据table名
     * @param datasource 取源数据连接
     * @param selectType 抽样方式：0==前n条记录;1==随机n条记录;2==随机百分比
     * @param selectValue 抽样值
     * @param filter //todo 过滤条件待完善
     * @param mongoDBName 存入到的mongo库名
     * @param mongoColName 存入到的mongo集合名
     *
     */
    public static List<Map<String,Object>> mysql2Mongo(Dataset dataset, Datasource datasource, int selectType, int selectValue, String filter, String mongoDBName,String mongoColName){
        String localhost = datasource.getHost();
        int port = datasource.getPort();
        String dbName = datasource.getDb();
        String userName = datasource.getUsername();
        String password = datasource.getPassword();

        String tableName = dataset.getTableName();

        MySqlUtils db = new MySqlUtils(localhost,Integer.toString(port),dbName,userName,password);
//        db.getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM ");
        sql.append(tableName);

        // todo 解析filter 暂时不加入查询语句中
//        sql.append("WHERE id IN(SELECT id from product where tenant_id = ?)");

        switch (selectType){
            case 0 : //前n条记录
                sql.append(" limit ");
                sql.append(selectValue);
                break;
            case 1 : //todo 随机n条记录
                break;
            case 2 : //todo 随机百分比
                break;
        }

//        String sql = "SELECT * FROM product WHERE id IN(SELECT id from product where tenant_id = ?)";

        List<Map<String,Object>> resList = new ArrayList<>(); //结果集

//        List<Object> list = new ArrayList<Object>();//参数集合
//        list.add(1);

        try {
            resList = db.executeQuery(sql.toString(),null); //mysql查询结果集合
//            for(Map<String,Object> ac:resList){
//                System.out.println(ac);
//            }

            MongoUtils mongoUtils = MongoUtils.getInstance();
            mongoUtils.insertDatas(mongoDBName,mongoColName,resList); //插入到mongo中
            logger.info("MySQL::["+sql.toString()+"] =====>>> Mongo::[dbName == "+mongoDBName+", collection == "+mongoColName+"]");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return resList;
    }

    /**
     * mysql导出数据到csv中的命令行语句
     * 命令示例：
     * " select id,product_id,model_id,name,description,unit_name,unit_sign,frequency,type,length,create_by,create_date,update_by,update_date,type_value
     * from data_point
     * into outfile '/var/lib/mysql-files/data_point_prev.csv'
     * fields terminated by ',' optionally enclosed by "" escaped by "" lines terminated by '\r\n' ";
     *
     */
    private static String getMysqlExportCommand(Datasource mysqlDS, String tableName) {

        StringBuffer command = new StringBuffer();
//
//        String username = mysqlDS.getUsername();//用户名
//        String password = mysqlDS.getPassword();//用户密码
//        String exportDatabaseName = mysqlDS.getDb();//需要导出的数据库名
//        String host = mysqlDS.getHost();//从哪个主机导出数据库，如果没有指定这个值，则默认取localhost
//        Integer port = mysqlDS.getPort();//使用的端口号
//        String exportPath = "\'/var/lib/mysql-files/"+tableName+"_prev_1"+".csv\'";//导出路径
////        String MysqlPath = properties.getProperty("MysqlPath"); //  路径是mysql中 bin 文件 的位置
//
//        // dataset --> datasetschema --> 列名 --> 查
//        String selectSql = "select id,product_id,model_id,name,description,unit_name,unit_sign,frequency,type,length,create_by,create_date,update_by,update_date,type_value from data_point";
//        String outfileCmd = "into outfile ";
//
//        //注意哪些地方要空格，哪些不要空格
//        command.append(MysqlPath).append("mysqldump -u").append(username).append(" -p").append(password)//密码是用的小p，而端口是用的大P。
//                .append(" -h").append(host).append(" -P").append(port).append(" ").append(exportDatabaseName).append(" -r ").append(exportPath);

        return command.toString();
    }
}
