package com.quarkdata.data.util.db;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;
import com.mongodb.client.*;
import com.quarkdata.data.util.StringUtils;
import org.apache.log4j.Logger;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.quarkdata.data.util.PropertiesUtils;

public class MongoUtils {

	private static Logger logger = Logger.getLogger(MongoUtils.class);
	private static MongoUtils mongoUtils;
	public static Map<String, String> props = PropertiesUtils.prop;

	/**
	 * query filter column type
	 */
	private enum VALUE_TYPE {
		/**
		 * now type: number(0)、string(1)、date(2)
		 */
		NUMBER("0"), STRING("1"), DATE("2");

		private String type;

		VALUE_TYPE(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}


	@Autowired
	private MongoOperations mongoOperations;

	// private String address;
	// static {
	// String address = props.get("mongo.host");
	// Integer port = Integer.getInteger(props.get("mongo.port"));
	//
	// }
	// mongoDB服务器
	// @Value("${mongo.host}")
	// private String address;
	//
	// // mongoDB端口
	// @Value("${mongo.port}")
	// private int port;
	//
	// // mongoDB库名称
	// @Value("${mongo.dbname}")
	// private String dbName;
	//
	// // 连接池设置为20个连接,默认为100
	// @Value("${mongo.options.connections-per-host}")
	// private int connectionsPerHost;
	//
	// // 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误
	// @Value("${mongo.options.threads-allowed-to-block-for-connection-multiplier}")
	// private int multiplier;
	//
	// // 连接超时
	// @Value("${mongo.options.connect-timeout}")
	// private int connectTimeout;
	//
	// @Value("${mongo.options.max-wait-time}")
	// private int maxWaitTime;
	//
	// @Value("${mongo.options.socket-keep-alive}")
	// private int socketKeepAlive;
	//
	// // 套接字超时时间，0无限制
	// @Value("${mongo.options.socket-timeout}")
	// private int socketTimeout;

	private String collection; // mongo表名

	MongoClient mongoClient;
	MongoDatabase mongoDatabase;
	MongoCollection<Document> mongoCol;

	private void getMongoCon() {

		String address = props.get("mongo.host");
		Integer port = Integer.parseInt(props.get("mongo.port"));
		String dbName = props.get("mongo.dbname");
		String username = props.get("mongo.username");
		String password = props.get("mongo.password");

		logger.info("开始连接mongodb:: " + address + ":" + port + ",dbName == " + dbName);

		MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
		MongoClientOptions.Builder options = new MongoClientOptions.Builder(); // mongoDB配置
		options.connectionsPerHost(Integer.parseInt(props.get("mongo.options.connections-per-host")));
		options.connectTimeout(Integer.parseInt(props.get("mongo.options.connect-timeout")));
		options.maxWaitTime(Integer.parseInt(props.get("mongo.options.max-wait-time")));
		options.socketTimeout(Integer.parseInt(props.get("mongo.options.socket-timeout")));
		options.threadsAllowedToBlockForConnectionMultiplier(
				Integer.parseInt(props.get("mongo.options.threads-allowed-to-block-for-connection-multiplier")));
		options.writeConcern(WriteConcern.SAFE);
		ServerAddress serverAddress = new ServerAddress(address, port); // 单节点mongoDB服务器
		mongoClient = new MongoClient(serverAddress, credential, options.build());
		if (null != mongoClient) {
			logger.info("获取mongodb连接成功！");
		}
		mongoDatabase = mongoClient.getDatabase(dbName);
	}

	// 单利
	public static MongoUtils getInstance() {
		if (mongoUtils != null) {
			return mongoUtils;
		} else {
			return new MongoUtils();
		}

	}

	/**
	 * 根据地址，数据库及连接名称连接到数据库
	 *
	 */
	public MongoUtils() {
		// 实例化
		getMongoCon();
	}

	// ------------------------------------共用方法---------------------------------------------------
	/**
	 * 获取DB实例 - 指定DB
	 *
	 * @param dbName
	 * @return
	 */
	public MongoDatabase getDB(String dbName) {
		if (dbName != null && !"".equals(dbName)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			return database;
		}
		return null;
	}

	/**
	 * 获取默认库中的collection对象 - 指定Collection
	 *
	 * @param collName
	 * @return
	 */
	public MongoCollection<Document> getCollection(String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		logger.info("mongo--get collection:: " + props.get("mongo.dbname") + "." + collName);
		MongoCollection<Document> collection = this.mongoDatabase.getCollection(collName);
		return collection;
	}

	/**
	 * 获取指定库中的collection对象 - 指定db和Collection
	 *
	 * @param dbName
	 * @param collName
	 * @return
	 */
	public MongoCollection<Document> getCollection(String dbName, String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		if (null == dbName || "".equals(dbName)) {
			return null;
		}
		logger.info("mongo--get collection:: " + dbName + "." + collName);
		MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
		return collection;
	}

	/**
	 * 查询DB下的所有表名
	 */
	public List<String> getAllCollectionNames(String dbName) {
		MongoIterable<String> colls = getDB(dbName).listCollectionNames();
		List<String> _list = new ArrayList<String>();
		for (String s : colls) {
			_list.add(s);
		}
		return _list;
	}

	/**
	 * 获取所有数据库名称列表
	 *
	 * @return
	 */
	public MongoIterable<String> getAllDBNames() {
		MongoIterable<String> s = mongoClient.listDatabaseNames();
		return s;
	}

	/**
	 * 判断默认库中是否存在某个collection
	 *
	 * @param collectionName
	 * @return 存在 true
	 */
	public boolean isCollectionExist(String collectionName) {
		logger.info("mongo--is collection exist:: " + props.get("mongo.dbname") + "." + collectionName);
		return null != this.getCollection(collectionName);
	}

	/**
	 * 插入数据,向默认库中的某个collection中插入
	 * 
	 * @param collectionName
	 *            指定collection名
	 * @param datas
	 *            待插入的数据
	 * @return
	 */
	public void insertDatas(String collectionName, List<Map<String, Object>> datas) {

		List<Document> list = new ArrayList<>();

		// 使用工具类，获取到指定数据库的MongoCollection对象
		MongoCollection mongoCollection = getCollection(collectionName);
		logger.info("mongo--insert datas to " + props.get("mongo.dbname") + "." + collectionName);
		for (Map<String, Object> data : datas) {
			Document document = new Document(data);
			list.add(document);
		}
		mongoCollection.insertMany(list);
	}

	/**
	 * 插入数据,向制定db的指定collection中插入
	 * 
	 * @param dbName
	 *            指定数据库名
	 * @param collectionName
	 *            指定collection名
	 * @param datas
	 *            待插入的数据
	 * @return
	 */
	public void insertDatas(String dbName, String collectionName, List<Map<String, Object>> datas) {

		List<Document> list = new ArrayList<>();

		// 使用工具类，获取到指定数据库的MongoCollection对象
		MongoCollection mongoCollection = getCollection(dbName, collectionName);
		logger.info("mongo--insert datas to " + dbName + "." + collectionName);
		for (Map<String, Object> data : datas) {
			Document document = new Document(data);
			list.add(document);
		}
		mongoCollection.insertMany(list);
	}

	/**
	 * 删除一个数据库
	 */
	public void dropDB(String dbName) {
		getDB(dbName).drop();
	}

	/**
	 * 查找对象 - 根据主键_id
	 *
	 * @param
	 * @param id
	 * @return
	 */
	public Document findById(MongoCollection<Document> coll, String id) {
		ObjectId _idobj = null;
		try {
			_idobj = new ObjectId(id);
		} catch (Exception e) {
			return null;
		}
		Document myDoc = coll.find(Filters.eq("_id", _idobj)).first();
		return myDoc;
	}

	/** 计数 */
	public int getCount(MongoCollection<Document> coll) {
		int count = (int) coll.count();
		return count;
	}

	/** 计数,过滤后的 */
	public int getCount(MongoCollection<Document> coll,Bson filter) {
		int sum = 0;
		MongoCursor<Document> cursor = coll.find(filter).iterator();
		while (cursor.hasNext()){
			sum = sum+ 1;
			cursor.next();
		}
		return sum;
	}

	/** 条件查询 */
	public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
		return coll.find(filter).iterator();
	}

	/** 分页查询,单一条件 */
	public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
		logger.info("mongo--query datas by page from " + coll + ", pageNo == " + pageNo + ", pageSize == " + pageSize);
		Bson orderBy = new BasicDBObject("_id", 1);
		return filter == null ? coll.find().skip((pageNo - 1) * pageSize).limit(pageSize).iterator()
				: coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
	}

	/**
	 * 分页查询,多条件,只是个示例
	 */
	public MongoCursor<Document> aggregateByPage(MongoCollection<Document> coll, List<Bson> filters, int pageNo,
			int pageSize) {

		/**
		 * 例子： db.user.aggregate([ {$match: {"age":{$lt:30},"firstName":/^J/}},
		 * {$sort: {_id: 1}}, {$skip:1}, {$limit: 100} ])
		 */

		Pattern pattern = Pattern.compile("^J", CASE_INSENSITIVE);
		BasicDBObject cond = new BasicDBObject();
		cond.put("age", new BasicDBObject("$lt", 30));
		cond.put("firstName", pattern);
		Bson match = new BasicDBObject("$match", cond);

		// group
		// DBObject groupFields = new BasicDBObject( "_id", "$lastEvent");
		// groupFields.put("count", new BasicDBObject( "$sum", 1));
		// DBObject group = new BasicDBObject("$group", groupFields);

		// sort
		Bson sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));
		// skip
		Bson skip = new BasicDBObject("$skip", pageNo);
		// limit
		Bson limit = new BasicDBObject("$limit", pageSize);

		filters.add(match);
		filters.add(sort);
		filters.add(skip);
		filters.add(limit);

		return coll.aggregate(filters).iterator();
	}

	/**
	 * 通过ID删除
	 *
	 * @param coll
	 * @param id
	 * @return
	 */
	public int deleteById(MongoCollection<Document> coll, String id) {
		int count = 0;
		ObjectId _id = null;
		try {
			_id = new ObjectId(id);
		} catch (Exception e) {
			return 0;
		}
		Bson filter = Filters.eq("_id", _id);
		DeleteResult deleteResult = coll.deleteOne(filter);
		count = (int) deleteResult.getDeletedCount();
		return count;
	}

	/**
	 *
	 *
	 * @param coll
	 * @param id
	 * @param newdoc
	 * @return
	 */
	public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
		ObjectId _idobj = null;
		try {
			_idobj = new ObjectId(id);
		} catch (Exception e) {
			return null;
		}
		Bson filter = Filters.eq("_id", _idobj);
		// coll.replaceOne(filter, newdoc); // 完全替代
		coll.updateOne(filter, new Document("$set", newdoc));
		return newdoc;
	}

	public void dropCollection(String collName) {
		getCollection(collName).drop();
		logger.info("mongo--dropped collection:: " + collName);
	}

	public void dropCollection(String dbName, String collName) {
		getDB(dbName).getCollection(collName).drop();
	}

	/**
	 * 关闭Mongodb
	 */
	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
			logger.info("mongo--closed");
		}
	}

	/**
	 * 针对所有字段的模糊查询，
	 *
	 * @param filter
	 */
	public Bson makeAllColumnFilter(List<String> columnNames,String filter){

		List<Bson> filters = new ArrayList<>();
		Pattern pattern = Pattern.compile(filter, CASE_INSENSITIVE);

		for (String columnName:columnNames) {
			BasicDBObject oneColumnFilter = new BasicDBObject();
			oneColumnFilter.put(columnName,pattern);
			filters.add(oneColumnFilter);
		}

		Bson res = new BasicDBObject("$or", filters);
		return res;
	}

	public static void main(String[] args) {
		MongoUtils mongoUtils = MongoUtils.getInstance();

		MongoCollection<Document> collection = mongoUtils.getCollection("db_1", "proj_1_ds_9");

		System.out.println(collection.count());

		BasicDBObject filter = new BasicDBObject();
		BasicDBObject column = new BasicDBObject("_id", 1);
		BsonArray array = new BsonArray();
		// MongoCursor<Document> cursor = mongoUtils.find(collection,column);

		Query query = new BasicQuery(filter, column);

		List<Object> list = mongoUtils.mongoOperations.find(query, null, "proj_1_ds_9");
		logger.info(list);
		// MongoCursor<Document> cursor = collection.find(array);
		// while (cursor.hasNext()){
		// Document object = cursor.next();
		// logger.info(object.toJson());
		// //do something.
		// }
		// MongoIterable<String> cols = mongoUtils.getAllDBNames();
		// for (String s : cols) {
		//// _list.add(s);
		// System.out.println(s);
		// }

		// List<String> collections = mongoUtils.getAllCollections("School");
		// for (String s : collections) {
		//// _list.add(s);
		// System.out.println(s);
		// }
		// Bson filter1 = Filters.lt("age",30); //过滤age小于30的记录
		// Bson filter2 = Filters.regex("firstName","/^J/");
		// List<Bson> filters = new ArrayList<>();
		// filters.add(filter1);
		// filters.add(filter2);
		// MongoCursor<Document> cursor =
		// mongoUtils.findByPage(mongoUtils.getCollection("School","user"),filters,1,10);
		// while (cursor.hasNext()){
		// Document object = cursor.next();
		// logger.info(object.toJson());
		// //do something.
		// }

		// MongoCursor<Document> cursor =
		// mongoUtils.aggregateByPage(mongoUtils.getCollection("School","user"),filters,1,10);
		// while (cursor.hasNext()){
		// Document object = cursor.next();
		// logger.info(object.toJson());
		// //do something.
		// }

	}

	/**
	 * parse String Filter to MongoDB Filter
	 *
	 * @param filter String Filter
	 *    	{
	 * 			"sampleFilterType":"0", //多个条件的逻辑运算（ &、 || ）
	 * 			"filters":[
	 * 				{"column":{"name":"ID","type":0},"operator":"2","value1":"1"},
	 * 				{"column":{"name":"USER_ID","type":0},"operator":"2","value1":"123","value2":""},
	 * 				{"column":{"name":"ADDRESS","type":1},"operator":"9","value1":"123","value2":""},
	 * 			    {"column":{"name":"CREATE_TIME","type":2},"operator":"11","value1":"2018-8-15","value2":"2018-8-23"}
	 * 	 		]
	 *  	}
	 *
	 * @return bson
	 */
	public static Bson parseFilter(String filter) {
		if (StringUtils.isEmpty(filter)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.parseObject(filter);
		// 获取过滤条件数组
		JSONArray filters = (JSONArray)jsonObject.get("filters");
		// 多条件逻辑运算
		int sampleFilterType = Integer.parseInt(jsonObject.getString("sampleFilterType"));
		String logic = sampleFilterType == 0 ? "$and" : "$or";
		// 日期格式化
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Bson条件对象数组
		BasicDBObject[] basicDBObjects = new BasicDBObject[filters.size()];

		for (int i = 0; i < filters.size(); i++) {
			JSONObject condition = filters.getJSONObject(i);
			JSONObject column = condition.getJSONObject("column");
			String colName = column.getString("name");
			String colType = column.getString("type");
			Object colValue = colType.equals(VALUE_TYPE.STRING.getType()) ? condition.get("value1")
					: (colType.equals(VALUE_TYPE.DATE.getType()) || StringUtils.isEmpty(condition.get("value1") + "")) ? 0
					: Integer.parseInt(condition.getString("value1"));

			switch (condition.getString("operator")) {
				case "0":
					// 不为空
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$ne", null));
					break;
				case "1":
					// 为空
					basicDBObjects[i] = new BasicDBObject().append(colName, null);
					break;
				case "2":
					// ==
					basicDBObjects[i] = new BasicDBObject().append(colName, colValue);
					break;
				case "3":
					// !=
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$ne", colValue));
					break;
				case "4":
					// >
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$gt", colValue));
					break;
				case "5":
					// <
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$lt", colValue));
					break;
				case "6":
					// >=
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$gte", colValue));
					break;
				case "7":
					// <=
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$lte", colValue));
					break;
				case "8":
					// 等于
					basicDBObjects[i] = new BasicDBObject().append(colName, condition.get("value1"));
					break;
				case "9":
					// 不等于
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$ne", colValue));
					break;
				case "10":
					// 包含
					Pattern pattern = Pattern.compile("^.*" + colValue +".*$", Pattern.CASE_INSENSITIVE);
					basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$regex", pattern));
					break;
				case "11":
					// 介于
					String startDate = condition.getString("value1");
					String endDate = condition.getString("value2");
					try {
						basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$gte", dateFormat.parse(startDate)).append("$lte",  dateFormat.parse(endDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
				case "12":
					// 早于
					String lateDate = condition.getString("value1");
					try {
						basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$lt", dateFormat.parse(lateDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
				case "13":
					// 晚于
					String earlyDate = condition.getString("value1");
					try {
						basicDBObjects[i] = new BasicDBObject().append(colName, new BasicDBObject("$gt", dateFormat.parse(earlyDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
				default: break;
			}
		}
		return basicDBObjects.length > 0 ? new BasicDBObject().append(logic, basicDBObjects) : null;
	}
}
