package com.quarkdata.data.util.common.cassandra;

import java.util.*;

import com.datastax.driver.core.*;
import com.datastax.driver.core.exceptions.AlreadyExistsException;
import com.quarkdata.data.util.StringUtils;
import com.quarkdata.data.util.TypeUtil;
import org.apache.log4j.Logger;


/**
 * 
 * @author WangHao
 */
public class CassandraUtil2 {

	private static Logger logger = Logger.getLogger(CassandraUtil2.class);
	private static PoolingOptions poolingOptions = new PoolingOptions();

	static {
		poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, 32)
				      .setCoreConnectionsPerHost(HostDistance.LOCAL, 2)
					  .setMaxConnectionsPerHost(HostDistance.LOCAL, 4)
					  .setCoreConnectionsPerHost(HostDistance.REMOTE, 2)
					  .setMaxConnectionsPerHost(HostDistance.REMOTE, 4)
					  .setHeartbeatIntervalSeconds(60);
	}

	/**
	 * cassandra数据库连接池信息组
	 */
	private static HashMap<String, Cluster> cassandraPools = new HashMap<>();

	/**
	 *  创建Cluster实例
	 *
	 * @param host
	 * 		   主机地址
	 * @param port
	 * 		   端口，可选参数
	 * @param userName
	 * 		   用户名
	 * @param password
	 * 		   密码
	 * @return
	 * 		   Session
	 */
	public static Cluster createCluster(String host, String port, String userName, String password) {
		Cluster cluster = null;
		if (!cassandraPools.containsKey(host)) {
			cluster = null == port ?
					 Cluster.builder().addContactPoints(host)
							.withCredentials(userName, password)
							.withPoolingOptions(poolingOptions)
							.build() :
					 Cluster.builder().addContactPoints(host)
							.withPort(Integer.parseInt(port))
							.withCredentials(userName, password)
							.withPoolingOptions(poolingOptions)
							.build();
			try {
				logger.info("获取cassandra连接...");
				cluster.connect();
				cassandraPools.put(host, cluster);
			} catch (Exception e) {
				logger.error("连接cassandra数据库失败， host: [" + host + "]");
			}
		} else {
			cluster = cassandraPools.get(host);
		}
		return cluster;
	}
	

	/**
	 * 
	 * 根据库名 判断库是否存在
	 * 
	 * @param dbName
	 * @return
	 */
	public static boolean isDb(Cluster cluster, String dbName) {
		Session session = cluster.connect("system_schema");
		String cql = "select keyspace_name from keyspaces where keyspace_name ='" + dbName + "';";
		if (session.execute(cql).all().size() == 1) {
			session.close();
			return true;
		}
		closeSession(session);
		return false;
	}

	/**
	 * 获取某一个库下所有的表名
	 */
	public static List<String> getTablesNameByDBName(String ipAdress, String userName, String passWord, String dbName) {
		List<Row> result;
		List<String> resultList = null;
		Session session = null;
		try {
			Cluster cluster = createCluster(ipAdress, null, userName, passWord);
			session = cluster.connect("system_schema");
			String cql = "select table_name from tables where keyspace_name = '" + dbName + "';";
			ResultSet execute = session.execute(cql);
			result = execute.all();
			if (result.size() > 0) {
				resultList = new ArrayList<>();
				for (Row row : result) {
					resultList.add(row.get(0, String.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return resultList;
	}

	/**
	 * 获取某一个库中表里所有的字段
	 */
	public static List<Map<String, String>> getTableColumn(String ipAdress, String userName, String passWord, String dbName,
			String tableName) {
		List<Row> result;
		List<Map<String, String>> resultList = null;
		Session session = null;
		try {
			Cluster cluster = createCluster(ipAdress, null, userName, passWord);
			session = cluster.connect("system_schema");
			String cql = "select column_name,type from columns where keyspace_name = '" + dbName
					+ "' and table_name = '" + tableName + "';";
			ResultSet execute = session.execute(cql);
			result = execute.all();
			if (result.size() > 0) {
				resultList = new ArrayList<Map<String, String>>();
				for (Row row : result) {
					String columnName = row.get(0, String.class);
					String columnType = row.get(1, String.class);
					Map<String, String> resultMap = new HashMap<String, String>();
					resultMap.put("columnName", columnName);
					resultMap.put("columnType", columnType);
					resultMap.put("columnComment", null);
					resultList.add(resultMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return resultList;
	}

	/**
	 * 根据库名创建与库的链接
	 * 
	 * @param dBName
	 * @return
	 */
	public static Session getSessionWithDbName(Cluster cluster, String dBName) {
		return cluster.connect(dBName);
	}

	/**
	 * 关闭session
	 * 
	 * @param session
	 */
	public static void closeSession(Session session) {
		if (null != session) {
			session.close();
		}
	}

	/**
	 * 关闭与Cassandra集群的连接
	 */
	public static void closeCluster(Cluster cluster) {
		if (null != cluster) {
			cluster.close();
		}
	}

	public static void main(String[] args) {
//		getTablesNameByDBName("192.168.204.19", "cassandra", "cassandra", "db_2");
//
//		List<Row> all;
//		try {
//			createCluster("192.168.204.19", "cassandra", "cassandra");
//			Session session = getSessionWithDbName("system_schema");
//			String cql = "select column_name,type from columns where keyspace_name = 'db_2' and table_name = 'table_136';";
//			ResultSet execute = session.execute(cql);
//			all = execute.all();
//			for (int i = 0; i < all.size(); i++) {
//				String columnName = all.get(i).get(0, String.class);
//				String columnType = all.get(i).get(1, String.class);
//				System.out.println(columnName + ":" + columnType);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeCluster();
//		}
		String cql = "select count(*) from table_135";
		executeQueryCql("192.168.204.19","9042", "cassandra", "cassandra","db_1",cql);
	}

	public static void dropTable(String host, String port, String dbName, String userName, String password, String tableName) {
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);

			session = cluster.connect(dbName);

			String dropCql = "DROP TABLE IF EXISTS " + tableName + ";";

			session.execute(dropCql);

			logger.info("drop table success,"
					+ "host:" + host + ",port:" + port +
					",dbName:" + dbName + ",tableName:" + tableName);
		} finally {
			closeSession(session);
		}
	}

	public static void truncateTable(String host, String port, String dbName, String userName, String password, String tableName) {
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);

			session = cluster.connect(dbName);

			String dropCql = "Truncate " + tableName + ";";

			session.execute(dropCql);

			logger.info("truncate table success,"
					+ "host:" + host + ",port:" + port +
					",dbName:" + dbName + ",tableName:" + tableName);
		} finally {
			closeSession(session);
		}
	}

	public static void truncateTable(Session session,String tableName){
		String dropCql = "Truncate "+tableName+";";
		
		session.execute(dropCql);
		
		logger.info("truncate table success,"
				+ "tableName:"+tableName);
	}

	public static Cluster getCluster(String host,String port,String username,String password){
		return Cluster.builder()
				.addContactPoints(host)
				.withCredentials(username, password)
				.withPort(Integer.parseInt(port))
				.build();
	}


	/**
	 * 根据mysql的表结构创建cassandra表
	 * @param dbName
	 * @param tableName
	 * @param paramList mysql的表结构
	 * @return
	 */
	public static boolean createTableFromMysql(String host, String port, String userName, String password,String dbName, String tableName, String primaryKey, List<Map<String, Object>> paramList) {
		logger.info("开始创建cassandra table, dbName =="+dbName);
		boolean result = false;
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);
			session = cluster.connect(dbName);
			StringBuilder delCql = new StringBuilder();
			delCql.append("DROP TABLE IF EXISTS ");
			delCql.append(tableName);
			logger.info("delCql is : " + delCql.toString());
			session.execute(delCql.toString());

			StringBuilder cqlSb = new StringBuilder();
			cqlSb.append("CREATE TABLE ");
			cqlSb.append(tableName);
			cqlSb.append(" ( ");

			// 输入的mysql数据集没有主键，则指定第一个字段为主键
			if (StringUtils.isBlank(primaryKey)){

				for (int i = 0;i<paramList.size();i++){
					cqlSb.append(paramList.get(i).get("columnName")+" ");
					cqlSb.append(TypeUtil.Type.getCassandraTypeByMysqlType((String) paramList.get(i).get("columnType")));
					if (i == 0){
						cqlSb.append(" PRIMARY KEY");
					}
					cqlSb.append(",");
				}
			} else {
				for (Map<String, Object> map : paramList) {
					cqlSb.append(map.get("columnName")+" ");
					cqlSb.append(TypeUtil.Type.getCassandraTypeByMysqlType((String) map.get("columnType")));
					if (map.get("columnName").equals(primaryKey)){
						cqlSb.append(" PRIMARY KEY");
					}
					cqlSb.append(",");
				}
			}

			cqlSb.append(" );");
			logger.info("cql is : " + cqlSb.toString());
			session.execute(cqlSb.toString());

			result = true;
		} catch (AlreadyExistsException e) {
			e.printStackTrace();
			System.err.println("数据库表已存在");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("创建数据库表异常");
		}finally {
			closeSession(session);
		}
		return result;
	}

	/**
	 * 根据paramList创建cassandra表
	 * @param dbName
	 * @param tableName
	 * @param paramList
	 * @return
	 */
	public static boolean createTableFromParamList(String host, String port, String userName, String password,String dbName, String tableName, String primaryKey, List<Map<String, Object>> paramList) {
		logger.info("开始创建cassandra table, dbName =="+dbName);
		boolean result = false;
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);
			session = cluster.connect(dbName);
			StringBuilder delCql = new StringBuilder();
			delCql.append("DROP TABLE IF EXISTS ");
			delCql.append(tableName);
			logger.info("delCql is : " + delCql.toString());
			session.execute(delCql.toString());

			StringBuilder cqlSb = new StringBuilder();
			cqlSb.append("CREATE TABLE ");
			cqlSb.append(tableName);
			cqlSb.append(" ( ");

			// 输入的数据集没有主键，则指定第一个字段为主键
			if (StringUtils.isBlank(primaryKey)){

				for (int i = 0;i<paramList.size();i++){
					cqlSb.append(paramList.get(i).get("columnName")+" ");
					cqlSb.append((String) paramList.get(i).get("columnType"));
					if (i == 0){
						cqlSb.append(" PRIMARY KEY");
					}
					cqlSb.append(",");
				}
			} else {
				for (Map<String, Object> map : paramList) {
					cqlSb.append(map.get("columnName") + " ");
					cqlSb.append((String) map.get("columnType"));
					if (map.get("columnName").equals(primaryKey)) {
						cqlSb.append(" PRIMARY KEY");
					}
					cqlSb.append(",");
				}
			}
			cqlSb.append(" );");
			logger.info("cql is : " + cqlSb.toString());
			session.execute(cqlSb.toString());

			result = true;
		} catch (AlreadyExistsException e) {
			e.printStackTrace();
			System.err.println("数据库表已存在");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("创建数据库表异常");
		}finally {
			closeSession(session);
		}
		return result;
	}
	/**
	 * 执行cql语句
	 *
	 * @param dbName
	 * @param cql
	 */
	public static void executeCql(String host, String port, String userName, String password, String dbName, String cql){
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);
			session = cluster.connect(dbName);
			logger.info("cql is : " + cql);
			session.execute(cql);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("执行cql异常");
		}finally {
			closeSession(session);
		}
	}

	/**
	 * 获取cassandra主键，暂时通过kind值为partition_key判断，todo 获取cassandra主键更好的方式
	 *
	 * @param host
	 * @param userName
	 * @param password
	 * @param dbName
	 * @param tableName
	 * @return
	 */
	public static String getPrimaryKey(String host, String userName, String password, String dbName, String tableName){
		String primaryKeyName = "";
		Session session = null;
		try {
			Cluster cluster = createCluster(host, null, userName, password);
			session = cluster.connect("system_schema");
			String cql = "select column_name,kind from columns where keyspace_name = '"+dbName+"' and table_name = '"+tableName+"';";
			logger.info("执行cql :: "+cql);
			ResultSet execute = session.execute(cql);
			List<Row> all = execute.all();
			for (Row row : all) {
				String columnName = row.get(0, String.class);
				String kind = row.get(1, String.class);
				if (StringUtils.isNotBlank(kind)) {
					if (kind.toLowerCase().equals("partition_key") || kind.toLowerCase().equals("primary_key")) {
						primaryKeyName = columnName;
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			closeSession(session);
		}
		return primaryKeyName;
	}

	/**
	 * 执行数据查询cql
	 *
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @param dbName
	 * @param cql
	 * @return map key : 字段名 value：字段值
	 */
	public static List<Map<String, Object>> executeQueryCql(String host, String port, String userName, String password, String dbName, String cql){

		List<Map<String, Object>> datas = new ArrayList<>();
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);
			session = cluster.connect(dbName);
			logger.info("cql is : " + cql);
			ResultSet resultSet = session.execute(cql);

			//列信息，包含列名称、类型等
			ColumnDefinitions columnDefinitions =  resultSet.getColumnDefinitions();

			for (Row row : resultSet) {
				Map<String,Object> oneRow = new HashMap<>();
				for (int i=0;i<columnDefinitions.size();i++){
					oneRow.put(columnDefinitions.getName(i),row.getObject(i));
				}
				datas.add(oneRow);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("执行cql异常");
		}finally {
			closeSession(session);
		}
		return datas;
	}

	/**
	 * 获取表结构信息,包括字段名,字段类型等
	 *
	 * @param tableName
	 * @return
	 */
	public static List<Map<String, Object>> getTableStructure(String host, String port, String userName, String password, String dbName, String tableName) {
		logger.info("开始查询表 " + tableName + " 的结构");
		List<Map<String, Object>> tableStructures = new ArrayList<>();

		List<Row> result;
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);
			session = cluster.connect("system_schema");
			String cql = "select column_name,type from columns where keyspace_name = '" + dbName
					+ "' and table_name = '" + tableName + "';";
			ResultSet execute = session.execute(cql);
			result = execute.all();
			if (result.size() > 0) {
				for (int i = 1; i <= result.size(); i++) {
					String columnName = result.get(i-1).get(0, String.class);
					String columnType = result.get(i-1).get(1, String.class);
					Map<String, Object> resultMap = new HashMap<>();
					resultMap.put("index",i);
					resultMap.put("columnName", columnName);
					resultMap.put("columnType", columnType);
					tableStructures.add(resultMap);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			closeSession(session);
		}
		return tableStructures;
	}

	/**
	 * 根据sql语句获取sql执行后的临时表字段名
	 *
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @param dbName
	 * @param cql
	 */
	public static Set<String> getSqlColumns(String host, String port, String userName, String password, String dbName, String cql){
		Set<String> sqlColumns = new HashSet<>();
		Session session = null;
		try {
			Cluster cluster = createCluster(host, port, userName, password);
			session = cluster.connect(dbName);
			logger.info("cql is : " + cql);
			ResultSet resultSet = session.execute(cql);

			//列信息，包含列名称、类型等
			ColumnDefinitions columnDefinitions =  resultSet.getColumnDefinitions();
			for (int i=0;i<columnDefinitions.size();i++){
				sqlColumns.add(columnDefinitions.getName(i).toLowerCase());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.err.println("执行cql异常");
		}finally {
			closeSession(session);
		}
		return sqlColumns;
	}
}
