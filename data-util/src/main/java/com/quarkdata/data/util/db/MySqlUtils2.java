package com.quarkdata.data.util.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;

public class MySqlUtils2 {

	private static Logger logger = Logger.getLogger(MySqlUtils2.class);

	private static final String SQL = "SELECT * FROM ";// 数据库操作

	/**
	 * 获取数据库连接
	 *
	 * @return
	 */
	public static Connection getConnection(String url, String userName,
			String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			logger.error("get connection failure", e);
		}
		return conn;
	}

	public static Connection getConnection(String host, String port,
			String dbName, String userName, String password) {
		Connection conn = null;
		if (classForName()) {
			conn = getConnection(getUrl(host, port, dbName), userName, password);
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("close connection failure", e);
			}
		}
	}

	/**
	 * 判断数据库的连接是否正常
	 * 
	 * @param host
	 * @param port
	 * @param dbName
	 * @param userName
	 * @param password
	 * @return（true：正常　false：异常）
	 */
	public boolean isConnection(String host, String port, String dbName,
			String userName, String password) {

		Connection conn = null;
		try {
			if (classForName()) {
				conn = DriverManager.getConnection(getUrl(host, port, dbName),
						userName, password);
				logger.info("成功连接数据库！！");
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex1) {
			logger.error("取得连接的时候有错误，请核对用户名和密码和其数据信息");
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("关闭连接异常");
			}
		}
	}

	/**
	 * 获取数据库下的所有库名
	 * 
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @return
	 */
	public static List<String> getDbNames(String host, String port,
			String userName, String password) {
		List<String> dbNames = new ArrayList<>();
		if (classForName()) {
			Connection conn = getConnection(getUrl(host, port, ""), userName,
					password);
			ResultSet rs = null;
			try {
				// 获取数据库的元数据
				DatabaseMetaData db = conn.getMetaData();
				// 从元数据中获取到所有的表名
				rs = db.getCatalogs();
				while (rs.next()) {
					dbNames.add(rs.getString("TABLE_CAT"));
				}
			} catch (SQLException e) {
				logger.error("getTableNames failure", e);
			} finally {
				try {
					rs.close();
					closeConnection(conn);
				} catch (SQLException e) {
					logger.error("close ResultSet failure", e);
				}
			}
		}
		return dbNames;
	}

	/**
	 * 获取数据库下的所有表名
	 * 
	 * @param host
	 * @param port
	 * @param dbName
	 * @param userName
	 * @param password
	 * @return
	 */
	public static List<String> getTableNames(String host, String port,
			String dbName, String userName, String password) {
		List<String> tableNames = new ArrayList<>();
		if (classForName()) {
			Connection conn = getConnection(getUrl(host, port, dbName),
					userName, password);
			ResultSet rs = null;
			try {
				if (null == conn){
					return null;
				}
				// 获取数据库的元数据
				DatabaseMetaData db = conn.getMetaData();
				// 从元数据中获取到所有的表名
				rs = db.getTables(null, null, null, new String[] { "TABLE" });
				while (rs.next()) {
					tableNames.add(rs.getString(3));
				}
			} catch (SQLException e) {
				logger.error("getTableNames failure", e);
				return null;
			} finally {
				try {
					if (null != rs){
						rs.close();
					}
					closeConnection(conn);
				} catch (SQLException e) {
					logger.error("close ResultSet failure", e);
				}
			}
		}
		return tableNames;
	}

	/**
	 * 获取表中所有字段名称、类型和注释
	 * 
	 * @param tableName
	 *            表名
	 * @param host
	 * @param port
	 * @param dbName
	 * @param userName
	 * @param password
	 * @return
	 */
	public static List<Map<String, String>> getColumnNamesList(
			String tableName, String host, String port, String dbName,
			String userName, String password) {
		List<Map<String, String>> columnNamesList = new ArrayList<Map<String, String>>();
		if (classForName()) {
			// 与数据库的连接
			Connection conn = getConnection(getUrl(host, port, dbName),
					userName, password);
			PreparedStatement pStemt = null;
			ResultSet rs = null;
			List<String> columnComments = new ArrayList<>();// 列名注释集合
			String tableSql = SQL + tableName;
			try {
				pStemt = conn.prepareStatement(tableSql);
				// 获取字段注释
				rs = pStemt.executeQuery("show full columns from " + tableName);
				while (rs.next()) {
					columnComments.add(rs.getString("Comment"));
				}
				// 结果集元数据
				ResultSetMetaData rsmd = pStemt.getMetaData();
				// 表列数
				int size = rsmd.getColumnCount();
				for (int i = 0; i < size; i++) {
					Map<String, String> columnMap = new HashMap<String, String>();
					columnMap.put("columnName", rsmd.getColumnName(i + 1));
					columnMap.put("columnType", rsmd.getColumnTypeName(i + 1));
					columnMap.put("columnComment", columnComments.get(i));
					columnNamesList.add(columnMap);
				}
			} catch (SQLException e) {
				logger.error("getColumnNames failure", e);
			} finally {
				if (pStemt != null) {
					try {
						pStemt.close();
						closeConnection(conn);
					} catch (SQLException e) {
						logger.error(
								"getColumnNames close pstem and connection failure",
								e);
					}
				}
			}
		}
		return columnNamesList;
	}

	/**
	 * 加载jdbc驱动
	 * 
	 * @return
	 */
	private static boolean classForName() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			logger.error("加载驱动程序有错误");
			return false;
		}
		return true;
	}

	/**
	 * 拼接数据库连接Url
	 * 
	 * @param host
	 * @param port
	 * @param dbName
	 * @return
	 */
	private static String getUrl(String host, String port, String dbName) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("jdbc:mysql://");
		sbUrl.append(host);
		sbUrl.append(":");
		sbUrl.append(port);
		sbUrl.append("/");
		sbUrl.append(dbName);
		sbUrl.append("?useUnicode=true&characterEncoding=utf-8");
		logger.info("Mysql Url =" + sbUrl.toString());
		return sbUrl.toString();
	}

	public static void dropTable(String host, String port, String dbName,
			String userName, String password, String tableName) {
		Connection conn = null;
		Statement stmt = null;
		if (classForName()) {
			// 与数据库的连接
			conn = getConnection(getUrl(host, port, dbName), userName, password);

			try {
				stmt = conn.createStatement();

				String sql = "DROP TABLE IF EXISTS " + tableName;

				stmt.executeUpdate(sql);

				logger.info("drop table success," + "host:" + host + ",port:"
						+ port + ",db:" + dbName + ",table:" + tableName);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				release(conn, stmt, null);
			}
		} else {
			throw new RuntimeException("load mysql driver fail");
		}
	}

	// 清空表数据
	public static void truncateTable(String host, String port, String dbName,
			String userName, String password, String tableName) {
		Connection conn = null;
		Statement stmt = null;
		if (classForName()) {
			// 与数据库的连接
			conn = getConnection(getUrl(host, port, dbName), userName, password);

			try {
				stmt = conn.createStatement();

				String sql = "truncate table " + tableName;

				stmt.executeUpdate(sql);

				logger.info("truncate table success," + "host:" + host
						+ ",port:" + port + ",db:" + dbName + ",table:"
						+ tableName);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				release(conn, stmt, null);
			}
		} else {
			throw new RuntimeException("load mysql driver fail");
		}
	}
	public static void truncateTable(Connection conn,String tableName){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();

			String sql = "truncate table " + tableName;

			stmt.executeUpdate(sql);

			logger.info("truncate table success," + 
						"table:"
					+ tableName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public static void release(Connection conn, Statement stmt,
			ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void main(String[] args) {
		/*
		 * String host = "localhost"; String port = "3306"; String dbName =
		 * "one_data"; String userName = "root"; String password = "root";
		 * 
		 * // List<String> dbNames = getDbNames(host, port, userName, password);
		 * // System.out.println("dbNames:" + dbNames); List<String> tableNames
		 * = getTableNames(host, port, dbName, userName, password); //
		 * System.out.println("tableNames:" + tableNames); for (String tableName
		 * : tableNames) { System.out.println("ColumnNames:" +
		 * getColumnNamesList(tableName, host, port, dbName, userName,
		 * password)); }
		 */

		try {
			dropTable("127.0.0.1", "3306", "one_data", "root", "316933", "t98");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
