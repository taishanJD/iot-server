
package com.quarkdata.data.util.common.cassandra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.quarkdata.data.util.PropertiesUtils;
import com.quarkdata.data.util.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.AlreadyExistsException;
import com.quarkdata.data.util.DateCalculate;
import com.quarkdata.data.util.DateUtil;

/**
 * 
 * @author hadoop
 *create a keyspace for every tenant
 *create a table for every product
 */
public class CassandraUtil {

	private static final String DB_PREFIX = "db_";
	private static final String TABLE_PREFIX = "table_";
	private static final String COL_DEVICEID = "device_id";
	private static final String COL_CREATE_TIME = "create_time";
	private static final String COL_DATE = "date";
	private static Logger logger = Logger.getLogger(CassandraUtil.class);
	/**
	 * 类加载  加载池
	 */
	private static PoolingOptions poolingOptions =new PoolingOptions();
	private  static Cluster cluster;
	static{
		 poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL,32);
		poolingOptions.
				setCoreConnectionsPerHost(HostDistance.LOCAL, 2).
				setMaxConnectionsPerHost(HostDistance.LOCAL, 4).
				setCoreConnectionsPerHost(HostDistance.REMOTE, 2).
				setMaxConnectionsPerHost(HostDistance.REMOTE, 4);
		 cluster= Cluster.builder().
				    addContactPoints(PropertiesUtils.prop.get("cassandra.servers").split(",")).
					withCredentials(PropertiesUtils.prop.get("cassandra.username"),
							PropertiesUtils.prop.get("cassandra.password")).withPoolingOptions(poolingOptions).build();
	}
	 
	/**
	 * 
	 * 只允许静态方法调用
	 */
	private CassandraUtil() {
	}
	/**
	 * 
	 * 提供库名 创建库 如果库存在则创建，不存在则不作操作
	 * db格式： db_tenantId
	 * @param dbName   
	 */
	public static boolean createDb(String dbName) {
		
		try {
			Session session = cluster.connect();
			String cql_create = "CREATE KEYSPACE if not EXISTS "+ dbName +" WITH replication ={'class': 'SimpleStrategy','replication_factor':"+PropertiesUtils.prop.get("cassandra.serverNum")+"};";
			session.execute(cql_create);
			session.close();
		}catch (AlreadyExistsException aee){
			aee.printStackTrace();
			System.err.println("数据库已存在");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 根据库名 判断库是否存在
	 * @param dbName
	 * @return
	 */
	public static boolean isDb(String dbName) {
		Session session = cluster.connect("system_schema");
		String cql="select keyspace_name from keyspaces where keyspace_name ='"+dbName+"';";
		if(session.execute(cql).all().size()==1) {
			session.close();
			return true;
		}
		session.close();
		return false;
	}
	//python
	/**
	 * 通过库名，表名，数据点信息 创建数据库表，如果表不存在创建，存在不做操作
	 * @param dbName  数据库名
	 * @param tableName 数据库表名: table_productId
	 * @param paramList 动态添加数据点属性 例如：[{"name":"pressure","type":"text"},{"name":"temperature","type":"int"},"……"]
	 */
	public static boolean createTable(String dbName, String tableName,  List<Map<String, String>> paramList) {
		boolean result = false;
		try {
			Session session = cluster.connect(dbName);
			StringBuilder cqlSb = new StringBuilder();
			cqlSb.append("CREATE TABLE ");
			cqlSb.append(tableName);
			cqlSb.append(" (device_id bigint,date text,create_time timestamp,");
			for (Map<String, String> map : paramList) {
				cqlSb.append(map.get("name"));
				cqlSb.append(" " + map.get("type") + ",");
			}
			cqlSb.append(" PRIMARY KEY ((device_id,date),create_time)) WITH CLUSTERING ORDER BY (create_time ASC);");
			System.out.println("cqlSb is : " + cqlSb.toString());
			session.execute(cqlSb.toString());
			
			session.close();
			result = true;
		} catch (AlreadyExistsException e) {
			e.printStackTrace();
			System.err.println("数据库表已存在");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("创建数据库表异常");
		}
		return result;
	}

	
	/**
	 * 动态向数据库表中添加数据点
	 * @param dbName  数据库名
	 * @param tableName 数据库表名
	 * @param paramList 动态添加数据点属性 例如：[{"name":"pressure","type":"text"},{"name":"temperature","type":"int"},"……"]
	 */
	public static boolean addTable(String dbName, String tableName,  List<Map<String, String>> paramList) {
		boolean result = false;
//		try {
			Session session = cluster.connect(dbName);
			for (Map<String, String> map : paramList) {
				StringBuilder cqlSb = new StringBuilder();
				cqlSb.append(" ALTER TABLE ");
				cqlSb.append(tableName);
				cqlSb.append(" ADD ");
				cqlSb.append(map.get("name"));
				cqlSb.append(" ");
				cqlSb.append(map.get("type"));
				cqlSb.append(";");
				System.out.println("cqlSb is : " + cqlSb.toString());
				session.execute(cqlSb.toString());
			}
			session.close();
			result = true;
//		}  catch (Exception e1) {
//			e1.printStackTrace();
//			System.err.println("数据库表添加数据点异常");
//		}
		return result;
	}
	
	/**
	 * 动态向数据库表中删除数据点
	 * @param dbName  数据库名
	 * @param tableName 数据库表名
	 * @param paramList 动态删除数据点属性 例如：["pressure","temperature","……"]
	 */
	public static boolean deleteTable(String dbName, String tableName,  List<String> paramList) {
		boolean result = false;
//		try {
			Session session = cluster.connect(dbName);
			for (String str : paramList) {
				StringBuilder cqlSb = new StringBuilder();
				cqlSb.append(" ALTER TABLE ");
				cqlSb.append(tableName);
				cqlSb.append(" DROP ");
				cqlSb.append(str);
				cqlSb.append(";");
				System.out.println("cqlSb is : " + cqlSb.toString());
				session.execute(cqlSb.toString());
			}
			
			session.close();
			result = true;
//		}  catch (Exception e1) {
//			e1.printStackTrace();
//			System.err.println("数据库表删除数据点异常");
//		}
		return result;
	}
	
	/**
	 * 向数据库表中添加数据
	 * @param dbName  数据库名
	 * @param tableName 数据库表名
	 * @param paramList 动态添加数据点属性
	 */
	public static boolean insertData(String dbName, String tableName,  List<Map<String, Object>> paramList) {
		boolean result = false;
		try {
			Session session = cluster.connect(dbName);
			StringBuilder cqlSb = new StringBuilder();
			cqlSb.append(" BEGIN BATCH ");
			for (Map<String, Object> map : paramList) {
				cqlSb.append(" INSERT INTO ");
				cqlSb.append(tableName);
				cqlSb.append(" ( device_id,date,create_time,");
				cqlSb.append(map.get("name"));
				cqlSb.append(" ) values (");
				cqlSb.append(map.get("deviceId") + ",'");
				cqlSb.append(DateUtil.formatDate(new Date()) + "','");
				cqlSb.append(DateUtil.formatDateMilliSecond(new Date()) + "',");
				if ("text".equals(map.get("type"))) {
					cqlSb.append("'");
					cqlSb.append(map.get("data"));
					cqlSb.append("'");
				} else {
					cqlSb.append(map.get("data"));
				}
				cqlSb.append(");");
			}
			cqlSb.append(" APPLY BATCH; ");
			System.out.println("cqlSb is : " + cqlSb.toString());
			session.execute(cqlSb.toString());
			
			session.close();
			result = true;
		}  catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("数据库表添加数据点异常");
		}
		return result;
	}
	

	public static List<Row> queryDeviceData2(String dbName, String tableName, Long deviceId, String startTime, String endTime, String[] dataPointNames, Long period) {
		Set<String> dateList = DateCalculate.cutDate("D", startTime, endTime);
		int dateLen = dateList.size();
		List<Row> resultRowList = new ArrayList<Row>();
		Session session = cluster.connect(dbName);
		for (int i = 0; i < dateLen-1; i++) {
			StringBuilder cqlSb = new StringBuilder();
			cqlSb.append(" select create_time");
			for (String dataPointName : dataPointNames) {
				cqlSb.append(",");
				cqlSb.append(dataPointName);
			}
			cqlSb.append(" from ");
			cqlSb.append(tableName);
			cqlSb.append(" where device_id=");
			cqlSb.append(deviceId);
			cqlSb.append(" and date='");
//			cqlSb.append(dateList.get(i));
			cqlSb.append("'");
			if (0 == i) {
				cqlSb.append(" and create_time>'");
				cqlSb.append(startTime);
				cqlSb.append("'");
			}
			if ((dateLen - 2) == i) {
				cqlSb.append(" and create_time<'");
				cqlSb.append(endTime);
				cqlSb.append("';");
			}
			System.out.println("cqlSb is : " + cqlSb.toString());
			ResultSet resultSet = session.execute(cqlSb.toString());
			for (Row row : resultSet) {
				resultRowList.add(row);
			}
		}
		session.close();
		return resultRowList;
	}

	/**
	 * 根据设备编号获取监控数据
	 * @param dbName  数据库名
	 * @param tableName 数据库表名
	 * @param deviceId   设备ID
	 * @param startTime 起始时间
	 * @param endTime   结束时间
	 * @param dataPointNames 一组待查询的数据点名称（传入格式：["zqssll", "zqwd", "..."]）
	 * @param period 时间长度（单位为秒，表示该时间长度内某设备只会返回一条有效数据；如果不填，默认返回所有数据）
	 * @return
	 */
	public static List<Row> queryDeviceData(String dbName, String tableName, Long deviceId, String startTime, String endTime, String[] dataPointNames, Long period) {
		Set<String> dateSet = DateCalculate.cutDate("D", startTime, endTime);
		List<Row> resultRowList = new ArrayList<>();
		Session session = cluster.connect(dbName);
		StringBuilder cqlSb = new StringBuilder();
		cqlSb.append(" select create_time");
		for (String dataPointName : dataPointNames) {
			cqlSb.append(",");
			cqlSb.append(dataPointName);
		}
		cqlSb.append(" from ");
		cqlSb.append(tableName);
		cqlSb.append(" where device_id=");
		cqlSb.append(deviceId);

		String dates = new String();
		for (String date : dateSet) {
			dates += "'";
			dates += date;
			dates += "',";
		}
		String datesSub = dates.substring(0, dates.length() - 1);
		cqlSb.append(" and date in (" + datesSub + ")");
		cqlSb.append(" and create_time>'");
		cqlSb.append(startTime);
		cqlSb.append("'");
		cqlSb.append(" and create_time<'");
		cqlSb.append(endTime);
		cqlSb.append("';");
		logger.info("queryDeviceData cql is : " + cqlSb.toString());
		ResultSet resultSet = session.execute(cqlSb.toString());
		for (Row row : resultSet) {
			resultRowList.add(row);
		}
		session.close();
		return resultRowList;
	}

	
	/**
	 * 
	 * 根据 数据库_表名 判断表是否存在；
	 * @param tableName
	 * @return
	 */
	public static boolean isTable(String dbName,String tableName) {
		//通过异常检测 判断表是否存在  ？
		Session session = cluster.connect(dbName);
		String cql = "SELECT table_name FROM system_schema.tables WHERE keyspace_name ='"+dbName+"'and table_name='"+tableName+"';";
		if(session.execute(cql).all().size()==1) {
			session.close();
			return true;
		}
		session.close();
		return false;
	}
	/**
	 * 根据库名创建与库的链接
	 * @param dBName
	 * @return
	 */
	public static Session getSessionWithDbName(String dBName) {
		return cluster.connect(dBName);
	}
	/**
	 * 获得不连接到任何数据库的链接
	 * @return
	 */
	public static Session getSession() {
		return cluster.connect();
	}
	/**
	 * 关闭session
	 * @param session
	 */
	public static void closeSession(Session session) {
		session.close();
	}
	/**
	 * 关闭与Cassandra集群的连接
	 */
	public static void trashUtil() {
		cluster.close();
	}
	
	public static void main(String[] args) {
		System.out.println(CassandraUtil.isDb("testkeyspace"));
		System.out.println(CassandraUtil.isTable("testkeyspace","person"));
		CassandraUtil.createDb("dog");
		System.out.println(CassandraUtil.isDb("dog"));

//		System.out.println(CassandraUtil.isDb("testkeyspace"));
//		System.out.println(CassandraUtil.isTable("testkeyspace","person"));
//		CassandraUtil.createDb("dog");
//		System.out.println(CassandraUtil.isDb("dog"));

//		System.out.println(CassandraUtil.isDb("testkeyspace"));
//		System.out.println(CassandraUtil.isTable("testkeyspace","person"));
//		CassandraUtil.createDb("dog");
//		System.out.println(CassandraUtil.isDb("dog"));
//		List<String> paramList = new ArrayList<String>();
//		paramList.add("temperature");
//		paramList.add("pressure");
//		createTabel("db1", "table2", paramList);
//		addTable("db1", "table2", paramList);
//		deleteTable("db1", "table2", paramList);
//		for (int i = 0; i < 100; i++) {
//			List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
//			Map<String, String> map = new HashMap<String, String>();
//			Map<String, String> map1 = new HashMap<String, String>();
//			map.put("deviceId", "1");
//			map.put("name", "pressure");
//			map.put("data", i + "");
//			paramList.add(map);
//			map1.put("deviceId", "1");
//			map1.put("name", "temperature");
//			map1.put("data", i + "");
//			paramList.add(map1);
//			insertData("db1", "table2", paramList);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		CassandraUtil.trashUtil();
	}


	/**
	 * 从kafka向数据库表中添加数据
	 * @param dbName  数据库名
	 * @param tableName 数据库表名
	 * @param deviceId
	 * @param paramList 动态添加数据点属性
	 */
	public static boolean insertDataForKafka(String dbName, String tableName, Long deviceId, List<Map<String, String>> paramList, String createTime) {
		boolean result = false;
		try {
			Session session = cluster.connect(dbName);
			StringBuilder cqlSb = new StringBuilder();
			cqlSb.append(" BEGIN BATCH ");
			for (Map<String, String> map : paramList) {
				cqlSb.append(" INSERT INTO ");
				cqlSb.append(tableName);
				cqlSb.append(" ( device_id,date,create_time,");
				cqlSb.append(map.get("key"));
				cqlSb.append(") values (");
				cqlSb.append(deviceId + ",'");
				cqlSb.append(createTime.substring(0,10) + "','");
				cqlSb.append(createTime + "',");
				switch (map.get("type")){
					case "0":
					case "1":
					case "2":
					case "3":
					case "4":
					case "5":
						cqlSb.append(map.get("value"));
						cqlSb.append(" );");
						break;
					case "6":
						cqlSb.append(createTime + "','");
						cqlSb.append(map.get("value"));
						cqlSb.append("' );");
						break;
					default:
						break;
				}

			}
			cqlSb.append(" APPLY BATCH; ");
			logger.debug("cqlSb is : " + cqlSb.toString());
			session.execute(cqlSb.toString());

			session.close();
			result = true;
		}  catch (Exception e1) {
			logger.error("数据库表添加数据点异常",e1);
		}
		return result;
	}
	
	/**
	 * 删除cassandra表
	 * @param dbName
	 * @param tableName
	 * @return
	 */
	public static boolean deleteTable2(String dbName, String tableName) {
		boolean result = false;
		Session session = cluster.connect(dbName);
		StringBuilder cqlSb = new StringBuilder();
		cqlSb.append("DROP TABLE " + tableName);
		logger.debug("cqlSb is : " + cqlSb.toString());
		session.execute(cqlSb.toString());
		session.close();
		result = true;
		return result;
	}
	
	public static boolean createTable2(String dbName, String tableName) {
		boolean result = false;
		Session session = cluster.connect(dbName);
		StringBuilder cqlSb = new StringBuilder();
		cqlSb.append("CREATE TABLE " + tableName + " (device_id bigint,date text,create_time timestamp,PRIMARY KEY ((device_id,date),create_time)) "
				+ "WITH CLUSTERING ORDER BY (create_time ASC);");
		logger.debug("cqlSb is : " + cqlSb.toString());
		session.execute(cqlSb.toString());
		session.close();
		result = true;
		return result;
	}
	/**
	 * 查询设备某一天的倒序
	 * 
	 * 	设置分页大小
	 * @param dbName
	 * @param tableName
	 * @param deviceId
	 * @param date
	 * @param orderingAscOrDesc
	 * @param pageSize
	 * @return
	 */
	public static List<JSONObject> getDeviceData(Long tenantId,Long productId,Long deviceId,String date,String orderingAscOrDesc,Integer pageSize){
		
		String dbName=DB_PREFIX+tenantId;
		String tableName=TABLE_PREFIX+productId;
		
		String orderBy=null;
		if(StringUtils.isNotBlank(orderingAscOrDesc)){
			orderBy="ORDER BY create_time "+orderingAscOrDesc;
		}
		
		StringBuilder cqlSb = new StringBuilder();
		cqlSb.append(" select *");
		cqlSb.append(" from ");
		cqlSb.append(tableName);
		cqlSb.append(" where device_id=");
		cqlSb.append(deviceId);
		cqlSb.append(" and date = '"+date+"'");
		
		if(orderBy!=null){
			cqlSb.append(" "+orderBy);
		}
		
		logger.debug("getDeviceData cql:"+cqlSb.toString());
		
		//设置分页 
		if(pageSize==null){
			pageSize=100;
		}
		Statement st = new SimpleStatement(cqlSb.toString());
		st.setFetchSize(pageSize);
		
		Session session = cluster.connect(dbName);
		ResultSet resultSet = session.execute(st);
		ColumnDefinitions columnDefinitions = resultSet.getColumnDefinitions();
		List<JSONObject> resultRowList = new ArrayList<>();
		int remaining = resultSet.getAvailableWithoutFetching();
		
		logger.debug("remaining:"+remaining);
		
	    for (Row row : resultSet)
	    {
	    	JSONObject jo=new JSONObject();
	    	for(ColumnDefinitions.Definition definition:columnDefinitions){
	    		String colName = definition.getName();
	    		if (colName.equals(COL_DEVICEID) || colName.equals(COL_DATE)) {
	    			continue;
	    		}
	    		Object val = row.getObject(colName);
	    		jo.put(colName, val);
	    	}
	    	resultRowList.add(jo);
	       
	      if (--remaining == 0)
	      {
	        break;
	      }
	    }
		
		session.close();
		
		return resultRowList;
	}
	
	public static List<Row> queryDeviceData(Long tenantId, Long productId, Long deviceId, String startTime, String endTime, String[] dataPointNames) {
		
		String dbName=DB_PREFIX+tenantId;
		String tableName=TABLE_PREFIX+productId;
		
		Set<String> dateSet = DateCalculate.cutDate("D", startTime, endTime);
		Session session = cluster.connect(dbName);
		StringBuilder cqlSb = new StringBuilder();
		cqlSb.append(" select create_time");
		for (String dataPointName : dataPointNames) {
			cqlSb.append(",");
			cqlSb.append(dataPointName);
		}
		cqlSb.append(" from ");
		cqlSb.append(tableName);
		cqlSb.append(" where device_id=");
		cqlSb.append(deviceId);

		String dates = new String();
		for (String date : dateSet) {
			dates += "'";
			dates += date;
			dates += "',";
		}
		String datesSub = dates.substring(0, dates.length() - 1);
		cqlSb.append(" and date in (" + datesSub + ")");
		cqlSb.append(" and create_time>'");
		cqlSb.append(startTime);
		cqlSb.append("'");
		cqlSb.append(" and create_time<'");
		cqlSb.append(endTime);
		cqlSb.append("';");
		
		logger.info("queryDeviceData cql is : " + cqlSb.toString());
		
		ResultSet resultSet = session.execute(cqlSb.toString());
		List<Row> resultRowList = new ArrayList<>();
		for (Row row : resultSet) {
			resultRowList.add(row);
		}
		session.close();
		return resultRowList;
	}
	public static Long totalCount(Long deviceId, Long productId, Long tenantId) {
		String dbName=DB_PREFIX+tenantId;
		String tableName=TABLE_PREFIX+productId;
		Session session = cluster.connect(dbName);
		
//		String cql="select count(device_id) from "+tableName+" where device_id = "+deviceId+" ALLOW FILTERING";
//		String cql="select count(device_id) from table_138 where device_id = 192 and date <= '2018-04-10' ALLOW FILTERING";
		String cql="select count(device_id) from table_138 where device_id = 192 ALLOW FILTERING";
		ResultSet resultSet = session.execute(cql);
		Long count = 0L;
		for(Row row: resultSet){
			count = row.getLong(0);
		}
		return count;
	}
	public static Long oneDayCount(String date, Long deviceId,
			Long productId, Long tenantId) {
		String dbName=DB_PREFIX+tenantId;
		String tableName=TABLE_PREFIX+productId;
		Session session = cluster.connect(dbName);
		
		String cql="select count(device_id) from "+tableName+" where device_id = "+deviceId+" and date ='"+date+"'";
		ResultSet resultSet = session.execute(cql);
		Long count = 0L;
		for(Row row: resultSet){
			count = row.getLong(0);
		}
		return count;
	}
}
