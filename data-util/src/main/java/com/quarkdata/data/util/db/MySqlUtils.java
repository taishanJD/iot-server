package com.quarkdata.data.util.db;

import com.quarkdata.data.util.StringUtils;
import com.quarkdata.data.util.TypeUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class MySqlUtils {

	private static Logger logger = Logger.getLogger(MySqlUtils.class);

	// 表示定义数据库的用户名
	private String USERNAME;
	// 定义数据库的密码
	private String PASSWORD;
	// 定义数据库的驱动信息
	private String DRIVER = "com.mysql.jdbc.Driver";
	// 定义访问数据库的地址
	private String URL;

	private static MySqlUtils per = null;
	// 定义数据库的链接
	private Connection con = null;
	// 定义sql语句的执行对象
	private PreparedStatement pstmt = null;
	// 定义查询返回的结果集合
	private ResultSet resultSet = null;

	public MySqlUtils(String host, String port, String dbName, String userName, String password) {
		this.USERNAME = userName;
		this.PASSWORD = password;
		this.URL = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
		this.registeredDriver();
		this.getConnection();
	}

	/**
	 * 单例模式，获得工具类的一个对象
	 *
	 * @return
	 */
	// public static MySqlUtils getInstance(String host, String port, String
	// dbName, String userName, String password) {
	// if (per == null) {
	// per = new MySqlUtils(host, port, dbName, userName, password);
	// per.registeredDriver();
	// }
	// return per;
	// }
	private void registeredDriver() {
		try {
			Class.forName(DRIVER);
			logger.info("注册mysql驱动成功！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得数据库的连接
	 *
	 * @return
	 */
	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("连接mysql数据库成功! url == " + URL);
		return con;
	}

	/**
	 * 完成对数据库的表的添加删除和修改的操作
	 *
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean executeUpdate(String sql, List<Object> params) throws SQLException {
		logger.info("执行sql：：" + sql);
		boolean flag = false;

		int result = -1; // 表示当用户执行添加删除和修改的时候所影响数据库的行数

		pstmt = con.prepareStatement(sql);

		if (params != null && !params.isEmpty()) {
			int index = 1;
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, i);
			}
		}

		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;

		return flag;
	}

	/**
	 * 从数据库中查询数据
	 *
	 * @param sql
	 *            sql语句
	 * @param params
	 *            sql语句中查询参数列表(?)
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> executeQuery(String sql, List<Object> params) throws SQLException {
		logger.info("执行sql：：" + sql);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = con.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				// String cols_name = metaData.getColumnName(i + 1);
				String cols_name = metaData.getColumnLabel(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;

	}

	/**
	 * jdbc的封装可以用反射机制来封装,把从数据库中获取的数据封装到一个类的对象里
	 *
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> executeQueryByRef(String sql, List<Object> params, Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = con.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			T resultObject = cls.newInstance(); // 通过反射机制创建实例
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); // 打开javabean的访问private权限
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;

	}

	/**
	 * 获取某表的建表语句
	 *
	 * @param dbName
	 *            库名
	 * @param tableName
	 *            表名
	 * @return 完整的建表语句 sql执行结果： { "table" : "tableName"; "Create table":
	 *         "完整的建表语句" }
	 */
	public String getTableCreateSQL(String dbName, String tableName) throws SQLException {
		logger.info("开始查询table [" + dbName + "." + tableName + "] 的建表语句");
		String createSql = "";
		// 查询建表语句: 例子：show create table `oneiot`.`data_point`;
		StringBuffer querySql = new StringBuffer();
		querySql.append("show create table ");
		querySql.append("`");
		querySql.append(dbName);
		querySql.append("`");
		querySql.append(".");
		querySql.append("`");
		querySql.append(tableName);
		querySql.append("`");
		List<Map<String, Object>> resList = null;
		resList = executeQuery(querySql.toString(), null);

		// for (Map<String, Object> res : resList) {
		// System.out.println(res.get("Create Table"));
		// }
		if (null != resList && !resList.isEmpty()) {
			Map<String, Object> res = resList.get(0);
			createSql = res.get("Create Table").toString();
		}
		logger.info("查询完成！");
		return createSql;
	}

	/**
	 * 执行建表语句建表
	 *
	 * @param sql
	 *            建表语句
	 * @return 是否成功
	 */
	public boolean executeCreateTableSQL(String sql) throws SQLException {
		logger.info("开始执行建表sql：：");
		logger.info(sql);
		boolean res = false;
		if (StringUtils.isNotBlank(sql)) {
			pstmt = con.prepareStatement(sql);
			res = 0 == pstmt.executeUpdate();
			logger.info("执行完成！");
		}
		return res;
	}

	// /**
	// * 获取某表的主键名
	// *
	// * @param tableName 表名
	// * @return 主键名
	// */
	// public ArrayList<String> getTablePrimaryKey(String tableName) throws
	// SQLException {
	//
	// ArrayList<String> primaryKeys = new ArrayList<>();
	// ResultSet databaseMetaData = con.getMetaData().getPrimaryKeys(null, null,
	// tableName);
	// while (databaseMetaData.next()) {
	// primaryKeys.add(databaseMetaData.getString(4));
	// }
	// return primaryKeys;
	// }

	/**
	 * 获取表所有字段名
	 *
	 * @param tableName
	 * @return
	 */
	public List<String> getTableFieldNames(String tableName) throws SQLException {
		logger.info("开始查询表 " + tableName + " 下所有字段名");
		List<String> names = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(tableName);
		pstmt = con.prepareStatement(sql.toString());
		resultSet = pstmt.executeQuery();

		ResultSetMetaData data = resultSet.getMetaData();
		int columnCount = data.getColumnCount();// 获得所有列的数目及实际列数

		for (int i = 1; i <= columnCount; i++) {
			String columnName = data.getColumnName(i);// 获得指定列的列名
			logger.info("获得列" + i + "的字段名称:" + columnName);
			names.add(columnName);
		}
		return names;
	}

	/**
	 * 获取表结构信息,包括字段名,字段类型,类型长度等
	 *
	 * @param tableName
	 * @return
	 */
	public List<Map<String, Object>> getTableStructure(String tableName) throws SQLException {
		logger.info("开始查询表 " + tableName + " 的结构");
		List<Map<String, Object>> tableStructures = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(tableName);
		pstmt = con.prepareStatement(sql.toString());
		resultSet = pstmt.executeQuery();

		ResultSetMetaData data = resultSet.getMetaData();
		// int columnCount = data.getColumnCount();//获得所有列的数目及实际列数
		// System.out.println(columnCount);
		// while (resultSet.next()) {
		for (int i = 1; i <= data.getColumnCount(); i++) {
			Map<String, Object> map = new HashMap<>();

			String columnName = data.getColumnName(i);// 获得指定列的列名

			// String columnValue = resultSet.getString(i);//获得指定列的列值

			int columnType = data.getColumnType(i);// 获得指定列的数据类型编号

			// String columnTypeName = data.getColumnTypeName(i);//获得指定列的数据类型名
			//
			// String catalogName = data.getCatalogName(i);//所在的Catalog名字
			//
			// String columnClassName = data.getColumnClassName(i);//对应数据类型的类
			//
			// int columnDisplaySize =
			// data.getColumnDisplaySize(i);//在数据库中类型的最大字符个数
			//
			// String columnLabel = data.getColumnLabel(i);//默认的列的标题
			//
			// String schemaName = data.getSchemaName(i);//获得列的模式

			int precision = data.getPrecision(i);// 某列类型的精确度(类型的长度)

			int scale = data.getScale(i);// 小数点后的位数

			// String tableNamea = data.getTableName(i);//获取某列对应的表名

			// boolean isAutoInctement = data.isAutoIncrement(i);// 是否自动递增

			// boolean isCurrency = data.isCurrency(i);//在数据库中是否为货币型

			// int isNullable = data.isNullable(i);//是否为空

			// boolean isReadOnly = data.isReadOnly(i);//是否为只读

			// boolean isSearchable = data.isSearchable(i);//能否出现在where中

			// System.out.println("获得列" + i + "的字段名称:" + columnName);
			// System.out.println("获得列" + i + "的字段值:" + columnValue);
			// System.out.println("获得列" + i + "的类型,返回SqlType中的编号:" +
			// columnType);
			// System.out.println("获得列" + i + "的数据类型名:" + columnTypeName);
			// System.out.println("获得列" + i + "所在的Catalog名字:" + catalogName);
			// System.out.println("获得列" + i + "对应数据类型的类:" + columnClassName);
			// System.out.println("获得列" + i + "在数据库中类型的最大字符个数:" +
			// columnDisplaySize);
			// System.out.println("获得列" + i + "的默认的列的标题:" + columnLabel);
			// System.out.println("获得列" + i + "的模式:" + schemaName);
			// System.out.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
			// System.out.println("获得列" + i + "小数点后的位数:" + scale);
			// System.out.println("获得列" + i + "对应的表名:" + tableNamea);
			// System.out.println("获得列" + i + "是否自动递增:" + isAutoInctement);
			// System.out.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
			// System.out.println("获得列" + i + "是否为空:" + isNullable);
			// System.out.println("获得列" + i + "是否为只读:" + isReadOnly);
			// System.out.println("获得列" + i + "能否出现在where中:" + isSearchable);
			// }
			// System.out.println();
			map.put("index", i);
			map.put("columnName", columnName);
			map.put("columnType", TypeUtil.sqlTypesConvert(columnType));
			map.put("precision", precision);
			map.put("scale", scale);
			// map.put("precision",precision);
			tableStructures.add(map);
		}
		return tableStructures;
	}

	/**
	 * 获取主键
	 *
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getTablePrimaryKey(String tableName) throws SQLException {
		String res = "";
		ResultSet rs = con.getMetaData().getPrimaryKeys(null, null, tableName);
		if (rs.next()) {
			// System.out.println(rs.getString(4));
			res = rs.getString(4);
		}
		return res;
	}

	/**
	 * 同一个mysql服务器下，复制一个存在的表的表结构到一个新表，不包括表数据, CREATE TABLE
	 * newDBName.newTableName LIKE existDBName.existTableName
	 *
	 * @param existDBName
	 *            存在的表所在的库名
	 * @param existTableName
	 *            存在的表表名
	 * @param newDBName
	 *            待新建的表所在库名
	 * @param newTableName
	 *            待新表表名
	 */
	public boolean createTableFromExist(String newDBName, String newTableName, String existDBName,
			String existTableName) throws SQLException {
		logger.info(
				"开始复制表 [" + existDBName + "." + existTableName + "] 表结构到新表 [" + newDBName + "." + newTableName + "] ");
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		sql.append("create table ");
		sql.append(newDBName);
		sql.append(".");
		sql.append(newTableName);
		sql.append(" like ");
		sql.append(existDBName);
		sql.append(".");
		sql.append(existTableName);
		logger.info("执行sql :: " + sql.toString());
		pstmt = con.prepareStatement(sql.toString());
		result = 0 == pstmt.executeUpdate();
		if (result) {
			logger.info("复制完成！");
		}
		return result;
	}

	public boolean createTableFromParamList(String tableName, List<Map<String, Object>> tableStructures)
			throws SQLException {

		// 建表时必须要指定长度的字段类型
		String[] needPrecisionColumn = { "VARCHAR" };

		// // 建表时不需要长度的字段类型
		// String[] noPrecisionColumn =
		// {"DATETIME","DATE","TIME","TIMESTAMP","TINYTEXT","TEXT","LONGTEXT","MEDIUMINT","ENUM","SET"};
		// // 有小数位数的字段类型
		// String[] hasDecimals = {"FLOAT","DOUBLE","DECIMAL","REAL"};

		StringBuffer delSql = new StringBuffer();
		delSql.append("DROP TABLE IF EXISTS `" + tableName + "`");
		logger.info("delSql ::" + delSql);
		this.executeUpdate(delSql.toString(), null);

		StringBuffer executeSQL = new StringBuffer();
		executeSQL.append("CREATE TABLE `" + tableName + "` ( ");

		for (Map<String, Object> tableStructure : tableStructures) {
			String columnName = (String) tableStructure.get("columnName");
			String columnType = (String) tableStructure.get("columnType");
			Integer precision = (Integer) tableStructure.get("precision");

			if (Arrays.asList(needPrecisionColumn).contains(columnType.toUpperCase()) && null != precision) {
				String columnSql = "`" + columnName + "` " + columnType + "(" + precision + ")" + ",";
				executeSQL.append(columnSql);
			} else {
				String columnSql = "`" + columnName + "` " + columnType + ",";
				executeSQL.append(columnSql);
			}

			// if
			// (Arrays.asList(noPrecisionColumn).contains(columnType.toUpperCase())){
			// String columnSql = "`"+columnName + "` "+ columnType+",";
			// executeSQL.append(columnSql);
			// }else if (null != precision){
			// String columnSql = "`"+columnName + "` "+
			// columnType+"("+precision+")"+",";
			// executeSQL.append(columnSql);
			// }else {
			// String columnSql = "`"+columnName + "` "+ columnType+",";
			// executeSQL.append(columnSql);
			// }
		}
		executeSQL.deleteCharAt(executeSQL.lastIndexOf(","));
		executeSQL.append(");");
		return this.executeCreateTableSQL(executeSQL.toString());
	}

	public void closeDB() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
				logger.info("mysql已释放连接::" + URL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 使用示例
	public static void main(String[] args) {

		MySqlUtils db = new MySqlUtils("localhost", "3306", "oneiot", "root", "root");
		// db.getConnection();

		MySqlUtils db2 = new MySqlUtils("192.168.204.26", "3306", "test", "root", "lkLdgWL2tlJIovHS123");
		// db2.getConnection();
		// String sql = "SELECT * FROM product WHERE id IN(SELECT id from
		// product where tenant_id = ?)";
		//
		// List<Map<String, Object>> reslist = new ArrayList<>(); //结果集
		//
		// List<Object> list = new ArrayList<Object>();//参数集合
		// list.add(1);

		try {
			// reslist = db.executeQuery(createSql, null);
			// for (Map<String, Object> ac : reslist) {
			// System.out.println(ac.get("Create Table"));
			// }
			// db.getTableStructure("data_point");
			// db.createTableFromExist("iot_guolu","zz", "oneiot","data_point");
			// ArrayList<String> res = db.getTablePrimaryKey("data_point");
			// String s = db.getTableCreateSQL("oneiot", "data_point");
			//// System.out.println(s);
			//
			// int i = s.indexOf("(");
			// String s1 = s.substring(0, i);
			//// System.out.println(s1);
			// String s2 = s.replace(s1, "CREATE TABLE `BALABALA` ");
			// System.out.println(s2);
			System.out.println(db.getTablePrimaryKey("data_point"));

			// boolean res = db2.executeCreateTableSQL(s);
			// System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeDB();
			db2.closeDB();
		}
	}

	/**
	 * 根据sql语句获取sql执行后的临时表字段名
	 *
	 * @param sql
	 *            sql语句
	 * @return
	 * @throws SQLException
	 */
	public Set<String> getSqlColumns(String sql) throws SQLException {
		logger.info("执行sql：：" + sql);
		pstmt = con.prepareStatement(sql);
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		Set<String> sqlColumns = new HashSet<>();
		for (int i = 0; i < cols_len; i++) {
			String cols_name = metaData.getColumnLabel(i + 1);
			sqlColumns.add(cols_name.toLowerCase());
		}
		return sqlColumns;
	}

}
