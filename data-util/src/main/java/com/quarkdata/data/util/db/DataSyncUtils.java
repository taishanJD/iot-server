package com.quarkdata.data.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;

/**
 * mysql与cassandra之间同步处理
 * 
 * @author typ 2018年6月11日
 */
public class DataSyncUtils {

	public static void syncDataFromMysqlToMysql(String inputHost, Integer inputPort,
			String inputDb, String inputUsername, String inputPassword,
			String inputTableName, String outputHost, Integer outputPort,
			String outputDb, String outputUsername, String outputPassword,
			String outputTableName, String bizDateParam, String sysTimeParam,
			String isAppend) {
		// input
		Connection inputConnection = null;

		Statement inputStatement = null;

		ResultSet inputRs = null;

		// output
		Connection outputConnection = null;

		Statement outputStatement = null;

		ResultSet outputRs = null;

		PreparedStatement outputPstmt = null;

		try {

			inputConnection = MySqlUtils2.getConnection(inputHost,
					String.valueOf(inputPort), inputDb, inputUsername,
					inputPassword);

			inputStatement = inputConnection.createStatement();

			// 查询总记录数

			String inputCountSql = "select count(*) from " + inputTableName;

			inputRs = inputStatement.executeQuery(inputCountSql);

			Long totalRecords = 0L;

			if (inputRs.next()) {
				totalRecords = inputRs.getLong(1);
			}

			inputRs.close();
			inputRs = null;

			// 查询输入的column
			String inputTableColSQL = "select * from " + inputTableName
					+ " limit 0";
			inputRs = inputStatement.executeQuery(inputTableColSQL);

			// 获取输入元数据
			int inputColCount = inputRs.getMetaData().getColumnCount();
			Set<String> inputColNames = new HashSet<>();
			for (int i = 1; i <= inputColCount; i++) {
				String columnName = inputRs.getMetaData().getColumnName(i);
				inputColNames.add(columnName);
			}
			inputRs.close();
			inputRs = null;

			// 输出
			outputConnection = MySqlUtils2.getConnection(outputHost,
					String.valueOf(outputPort), outputDb, outputUsername,
					outputPassword);

			outputStatement = outputConnection.createStatement();
			outputRs = outputStatement.executeQuery("select * from "
					+ outputTableName + " limit 0");

			int columnCount = outputRs.getMetaData().getColumnCount();

			// 查询输出的所有列名称
			String[] columnNames = new String[columnCount];

			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = outputRs.getMetaData().getColumnName(i);
				sb.append(columnName).append(",");
				columnNames[i - 1] = columnName;
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			String allColumnNames = sb.toString();

			String placeholders = getPlaceholders(columnCount);

			String insertSql = "insert into " + outputTableName + " ("
					+ allColumnNames + ") values (" + placeholders + ")";

			outputPstmt = outputConnection.prepareStatement(insertSql);

			// 分页获取数据
			int size = 1000;
			int limitStart = 0;
			int limitEnd = size;

			outputConnection.setAutoCommit(false);
			
			if(isAppend == null){
				isAppend = "1";
			}
			if(isAppend.equals("0")){
				// 覆盖
				// 先清空表
				MySqlUtils2.truncateTable(outputConnection, outputTableName);
			}

			while (true) {
				String pageQuerySQL = getLimitSQL(inputTableName, limitStart,
						limitEnd);
				inputRs = inputStatement.executeQuery(pageQuerySQL);

				while (inputRs.next()) {
					for (int i = 0; i < columnNames.length - 2; i++) {
						String colName = columnNames[i];
						if (colName.equals("biz_date_param")
								|| colName.equals("sys_time_param")) {
							continue;
						}

						Object value = null;
						if (inputColNames.contains(colName)) {
							value = inputRs.getObject(colName);
						}
						outputPstmt.setObject(i + 1, value);
					}

					outputPstmt.setObject(columnCount - 1, bizDateParam);
					outputPstmt.setObject(columnCount, sysTimeParam);

					outputPstmt.addBatch();
				}
				outputPstmt.executeBatch();
				outputPstmt.clearBatch();

				inputRs.close();
				inputRs = null;

				limitStart += limitEnd;
				if (limitStart >= totalRecords) {
					break;
				}
			}

			outputConnection.commit();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (outputPstmt != null) {
				try {
					outputPstmt.close();
				} catch (SQLException e) {
				}
			}
			if (outputRs != null) {
				try {
					outputRs.close();
				} catch (SQLException e) {
				}
			}
			if (outputStatement != null) {
				try {
					outputStatement.close();
				} catch (SQLException e) {
				}
			}
			if (outputConnection != null) {
				try {
					outputConnection.close();
				} catch (SQLException e) {
				}
			}
			if (inputRs != null) {
				try {
					inputRs.close();
				} catch (SQLException e) {
				}
			}
			if (inputStatement != null) {
				try {
					inputStatement.close();
				} catch (SQLException e) {
				}
			}
			if (inputConnection != null) {
				try {
					inputConnection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public static void syncDataFromMysqlToCassandra(String inputHost, Integer inputPort,
			String inputDb, String inputUsername, String inputPassword,
			String inputTableName, String outputHost, Integer outputPort,
			String outputDb, String outputUsername, String outputPassword,
			String outputTableName, String bizDateParam, String sysTimeParam,
			String isAppend) {
		// input
		Connection inputConnection = null;

		Statement inputStatement = null;

		ResultSet inputRs = null;

		// output

		Cluster cluster = null;

		Session session = null;

		try {
			inputConnection = MySqlUtils2.getConnection(inputHost,
					String.valueOf(inputPort), inputDb, inputUsername,
					inputPassword);
			inputStatement = inputConnection.createStatement();

			// 查询总记录数
			String inputCountSql = "select count(*) from " + inputTableName;

			inputRs = inputStatement.executeQuery(inputCountSql);

			Long totalRecords = 0L;

			if (inputRs.next()) {
				totalRecords = inputRs.getLong(1);
			}

			inputRs.close();
			inputRs = null;

			// 查询输入的column
			String inputTableColSQL = "select * from " + inputTableName
					+ " limit 0";
			inputRs = inputStatement.executeQuery(inputTableColSQL);

			// 获取输入元数据
			int inputColCount = inputRs.getMetaData().getColumnCount();
			Set<String> inputColNames = new HashSet<>();
			for (int i = 1; i <= inputColCount; i++) {
				String columnName = inputRs.getMetaData().getColumnName(i);
				inputColNames.add(columnName);
			}
			inputRs.close();
			inputRs = null;

			// 输出

			// 获取cassandra链接
			cluster = CassandraUtil2.getCluster(outputHost, outputPort + "",
					outputUsername, outputPassword);
			session = cluster.connect(outputDb);

			String outputQuerySql = "select * from " + outputTableName
					+ " limit 1;";

			com.datastax.driver.core.ResultSet outputRs = session
					.execute(outputQuerySql);

			ColumnDefinitions columnDefinitions = outputRs
					.getColumnDefinitions();

			List<Definition> colDefs = columnDefinitions.asList();

			StringBuilder sb = new StringBuilder();

			for (Definition colDef : colDefs) {
				sb.append(colDef.getName()).append(",");
			}

			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			String outputAllCols = sb.toString();

			String placeholders = getPlaceholders(colDefs.size());

			String insertSql = "insert into " + outputTableName + " ("
					+ outputAllCols + ") values (" + placeholders + ")";

			BatchStatement batch = new BatchStatement();

			com.datastax.driver.core.PreparedStatement prepare = session
					.prepare(insertSql);
			
			if(isAppend == null){
				isAppend="1";
			}
			if(isAppend.equals("0")){
				// 覆盖
				CassandraUtil2.truncateTable(session, outputTableName);
			}

			// 分页获取数据
			int size = 1000;
			int limitStart = 0;
			int limitEnd = size;

			while (true) {
				String pageQuerySQL = getLimitSQL(inputTableName, limitStart,
						limitEnd);
				inputRs = inputStatement.executeQuery(pageQuerySQL);

				while (inputRs.next()) {

					Object[] values = new Object[colDefs.size()];

					for (int i = 0; i < colDefs.size() - 2; i++) {
						String colName = colDefs.get(i).getName();
						if (colName.equals("biz_date_param")
								|| colName.equals("sys_time_param")) {
							continue;
						}
						Object value = null;
						if (inputColNames.contains(colName)) {
							value = inputRs.getObject(colName);
						}
						values[i] = value;
					}
					values[colDefs.size() - 2] = bizDateParam;
					values[colDefs.size() - 1] = sysTimeParam;

					BoundStatement bind = prepare.bind(values);

					batch.add(bind);
				}
				session.execute(batch);
				batch.clear();

				inputRs.close();
				inputRs = null;

				limitStart += limitEnd;
				if (limitStart >= totalRecords) {
					break;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (inputRs != null) {
				try {
					inputRs.close();
				} catch (SQLException e) {
				}
			}
			if (inputStatement != null) {
				try {
					inputStatement.close();
				} catch (SQLException e) {
				}
			}
			if (inputConnection != null) {
				try {
					inputConnection.close();
				} catch (SQLException e) {
				}
			}
			if (session != null) {
				session.close();
			}
			if (cluster != null) {
				cluster.close();
			}
		}

	}

	// cassandra to mysql
	public static void syncDataFromCassandraToMysql(String inputHost,
			Integer inputPort, String inputDb, String inputUsername,
			String inputPassword, String inputTableName, String outputHost,
			Integer outputPort, String outputDb, String outputUsername,
			String outputPassword, String outputTableName, String bizDateParam,
			String sysTimeParam,String isAppend) {
		// input

		Cluster cluster = null;

		Session session = null;

		// output
		Connection outputConnection = null;

		Statement outputStatement = null;

		ResultSet outputRs = null;

		PreparedStatement outputPstmt = null;

		cluster = CassandraUtil2.getCluster(inputHost, inputPort + "",
				inputUsername, inputPassword);

		session = cluster.connect(inputDb);

		String inputQuerySql = "select * from " + inputTableName;

		com.datastax.driver.core.ResultSet inptRs = session
				.execute(inputQuerySql);

		// 获取元数据
		ColumnDefinitions columnDefinitions = inptRs.getColumnDefinitions();
		List<Definition> defs = columnDefinitions.asList();
		Set<String> inputAllColName = new HashSet<String>();

		for (Definition def : defs) {
			inputAllColName.add(def.getName());
		}

		List<Row> inputAllRow = inptRs.all();

		// output
		try {
			outputConnection = MySqlUtils2.getConnection(outputHost,
					String.valueOf(outputPort), outputDb, outputUsername,
					outputPassword);
			outputStatement = outputConnection.createStatement();

			String outputQuerySql = "select * from " + outputTableName
					+ " limit 0";

			outputRs = outputStatement.executeQuery(outputQuerySql);

			int columnCount = outputRs.getMetaData().getColumnCount();

			// 查询输出的所有列名称
			String[] columnNames = new String[columnCount];

			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = outputRs.getMetaData().getColumnName(i);
				sb.append(columnName).append(",");
				columnNames[i - 1] = columnName;
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			String allColumnNames = sb.toString();

			String placeholders = getPlaceholders(columnCount);

			String insertSql = "insert into " + outputTableName + " ("
					+ allColumnNames + ") values (" + placeholders + ")";

			outputPstmt = outputConnection.prepareStatement(insertSql);

			outputConnection.setAutoCommit(false);
			
			if(isAppend == null){
				isAppend = "1";
			}
			if(isAppend.equals("0")){
				// 覆盖
				MySqlUtils2.truncateTable(outputConnection, outputTableName);
			}

			// exec sync data
			int insertCount = 0;
			int batchSize = 1000;

			for (Row row : inputAllRow) {

				insertCount++;

				for (int i = 0; i < columnNames.length - 2; i++) {
					String colName = columnNames[i];
					if (colName.equals("biz_date_param")
							|| colName.equals("sys_time_param")) {
						continue;
					}

					Object value = null;
					if (inputAllColName.contains(colName)) {
						value = row.getObject(colName);
					}
					outputPstmt.setObject(i + 1, value);
				}

				outputPstmt.setObject(columnCount - 1, bizDateParam);
				outputPstmt.setObject(columnCount, sysTimeParam);

				outputPstmt.addBatch();

				// 每一千条插入一次
				if (insertCount % batchSize == 0) {
					outputPstmt.executeBatch();
					outputPstmt.clearBatch();
				}
			}

			outputPstmt.executeBatch();
			outputConnection.commit();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (outputRs != null) {
				try {
					outputRs.close();
				} catch (SQLException e) {
				}
			}
			if (outputStatement != null) {
				try {
					outputStatement.close();
				} catch (SQLException e) {
				}
			}
			if (outputPstmt != null) {
				try {
					outputPstmt.close();
				} catch (SQLException e) {
				}
			}
			if (outputConnection != null) {
				try {
					outputConnection.close();
				} catch (SQLException e) {
				}
			}
			if (session != null) {
				session.close();
			}
			if (cluster != null) {
				cluster.close();
			}
		}
	}

	// cassandra 到 cassandra 数据同步
	public static void syncDataFromCassandraToCassandra(String inputHost,
			Integer inputPort, String inputDb, String inputUsername,
			String inputPassword, String inputTableName, String outputHost,
			Integer outputPort, String outputDb, String outputUsername,
			String outputPassword, String outputTableName, String bizDateParam,
			String sysTimeParam,String isAppend) {

		// input

		Cluster inputCluster = null;

		Session inputSession = null;

		// output

		Cluster outputCluster = null;

		Session outputSession = null;

		try {
			inputCluster = CassandraUtil2.getCluster(inputHost, inputPort + "",
					inputUsername, inputPassword);

			inputSession = inputCluster.connect(inputDb);

			String inputQuerySql = "select * from " + inputTableName;

			com.datastax.driver.core.ResultSet inputRs = inputSession
					.execute(inputQuerySql);

			// 获取元数据
			ColumnDefinitions columnDefinitions = inputRs
					.getColumnDefinitions();
			List<Definition> defs = columnDefinitions.asList();
			Set<String> inputAllColName = new HashSet<String>();

			for (Definition def : defs) {
				inputAllColName.add(def.getName());
			}

			List<Row> inputAllRow = inputRs.all();

			// 输出
			outputCluster = CassandraUtil2.getCluster(outputHost, outputPort
					+ "", outputUsername, outputPassword);
			outputSession = outputCluster.connect(outputDb);

			String outputQuerySql = "select * from " + outputTableName
					+ " limit 1;";

			com.datastax.driver.core.ResultSet outputRs = outputSession
					.execute(outputQuerySql);

			ColumnDefinitions outputColumnDefinitions = outputRs
					.getColumnDefinitions();

			List<Definition> outputColDefs = outputColumnDefinitions.asList();

			StringBuilder sb = new StringBuilder();

			for (Definition colDef : outputColDefs) {
				sb.append(colDef.getName()).append(",");
			}

			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			String outputAllCols = sb.toString();

			String placeholders = getPlaceholders(outputColDefs.size());

			String insertSql = "insert into " + outputTableName + " ("
					+ outputAllCols + ") values (" + placeholders + ")";

			BatchStatement batch = new BatchStatement();

			com.datastax.driver.core.PreparedStatement outputPstmt = outputSession
					.prepare(insertSql);
			
			if(isAppend == null){
				isAppend = "1";
			}
			if(isAppend.equals("0")){
				CassandraUtil2.truncateTable(outputSession, outputTableName);
			}

			// exec sync data
			int insertCount = 0;
			int batchSize = 1000;

			for (Row row : inputAllRow) {

				insertCount++;

				Object[] values = new Object[outputColDefs.size()];

				for (int i = 0; i < outputColDefs.size() - 2; i++) {
					String colName = outputColDefs.get(i).getName();
					if (colName.equals("biz_date_param")
							|| colName.equals("sys_time_param")) {
						continue;
					}
					Object value = null;
					if (inputAllColName.contains(colName)) {
						value = row.getObject(colName);
					}
					values[i] = value;
				}
				values[outputColDefs.size() - 2] = bizDateParam;
				values[outputColDefs.size() - 1] = sysTimeParam;

				BoundStatement bind = outputPstmt.bind(values);

				batch.add(bind);

				// 每一千条插入一次
				if (insertCount % batchSize == 0) {
					outputSession.execute(batch);
					batch.clear();
				}
			}
			outputSession.execute(batch);
		} finally {
			if (outputSession != null) {
				outputSession.close();
			}
			if (outputCluster != null) {
				outputCluster.close();
			}
			if (inputSession != null) {
				inputSession.close();
			}
			if (inputCluster != null) {
				inputCluster.close();
			}
		}
	}

	private static String getPlaceholders(int columnCount) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= columnCount; i++) {
			sb.append("?,");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private static String getLimitSQL(String tableName, int limitStart,
			int limitEnd) {
		return "select * from " + tableName + " limit " + limitStart + ","
				+ limitEnd;
	}
}
