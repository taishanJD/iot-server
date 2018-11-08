package com.quarkdata.data.model.common;

import java.util.HashMap;
import java.util.Map;




public class Constants {
	
	public static String SYSTEM="SYSTEM";
	public static String SUCCESS="SUCCESS";
	public static String FAIL="FAIL";

	public static final String ONEIOT_REDIS_PREFIX = "oi";
	public static final String ONEIOT_REDIS_DELIMITER = ":";
	public static final String ONEIOT_REDIS_USER_INFO_VO = "userInfoVO";
	public static final String ONEIOT_REDIS_TEN = "ten";
	public static final String ONEIOT_REDIS_LATEST_STATE = "latestState";

	public static final String ONEIOT_REDIS_TOKEN = "token";
	public static final String ONEIOT_REDIS_SECRET_ID = "secretId";

	public static final String ONEIOT_REDIS_DEVICE_MODEL = "deviceModel";
	public static final String ONEIOT_REDIS_DEVICE = "device";
	
	public static final String ONEIOT_REDIS_TRIGGERS_INFO = "triggersInfo";
	public static final String ONEIOT_REDIS_TRIGGER_TIMES = "triggerTimes";

	public static final String ONEIOT_DEVICE_DATA_PREFIX_PRODUCT_ID = "product_id";
	public static final String ONEIOT_DEVICE_DATA_PREFIX_DEVICE_ID = "device_id";
	public static final String ONEIOT_DEVICE_DATA_PREFIX_CLOUD_TIME = "cloud_time";

	public static final String ONEIOT_DEVICE_DATA_KAFKA_TOPIC = "device_data";

	//触发器
	public static final Byte DEVICE_TRIGGER_OBJECT_OBJECT_TYPE_DEVICE = 0;//类型0-设备,1-设备组,2-全部设备
	public static final Byte DEVICE_TRIGGER_OBJECT_OBJECT_TYPE_DEVICEGROUP = 1;
	public static final Byte DEVICE_TRIGGER_OBJECT_OBJECT_TYPE_ALLDEVICE = 2;
	public static final String TRIGGER_CONDITION_EXPRESS_PREFIX = "${";
	public static final String TRIGGER_CONDITION_EXPRESS_SUFFIX = "}";
	public static final Byte TRIGGER_RESULT_RESULT_TYPE_EMAIL = 0;//结果类型(0:邮件通知,1:url)
	public static final Byte TRIGGER_RESULT_RESULT_TYPE_URL = 1;
	public static final Byte TRIGGER_CONDITION_CONNECT_TYPE_0 = 0;//连接方式(0:与,1:或)
	public static final Byte TRIGGER_CONDITION_CONNECT_TYPE_1 = 1;

	public static final String ONEIOT_REDIS_VALIDATE_CODE = "validateCode";

	public static final String ONEIOT_REDIS_RESET_EMAIL_LINK = "resetEmailLink";

	//触发条件运算符
	public static final Map<Byte,String> TRIGGER_CONDITION_OPERATOR_MAP = new HashMap<Byte,String>();
	static{
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 0, "=");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 1, ">");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 2, "<");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 3, ">=");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 4, "<=");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 5, "<>");
	}
	
	// job责任人类型
	public static final String JOB_RESPONSIBLE_TYPE_ALL ="0";// 全部
	public static final String JOB_RESPONSIBLE_TYPE_MY ="1";// 我的
	// 是否已删除（0：否、1：是）
	public static final String IS_DEL_NO ="0";
	public static final String IS_DEL_YES ="1";

	public static final String TABLE_NAME_DELIMITER = "_"; // 新建数据库表名分隔符
	public static final Integer PREVIEW_PAGE_SIZE = 20;
	
	public static final Map<String,Map<String,String>> processTypeDict = new HashMap<String,Map<String,String>>();
	static{
		Map<String,String> subType = new HashMap<String,String>();
		subType.put("0", "预处理");
		subType.put("1", "数据同步");
		subType.put("2", "SQL");
		processTypeDict.put("0", subType);// 0：visual 可视化处理
		processTypeDict.put("1", subType);// 1：code 编码处理
	}
	
	public static final String NODE_PROCESS_SUB_TYPE_PREPROCESS= "0";
	public static final String NODE_PROCESS_SUB_TYPE_SYNC= "1";
	
	// dataSource 
	// 是否允许对此连接的数据集进行写操作（0：否、1：是）
	public static final String DATASOURCE_IS_WRITE_NO = "0";
	public static final String DATASOURCE_IS_WRITE_YES = "1";

	/**
	 * 数据类型
	 * （0：MySQL、1：Oracle、2：PostgreSQL、
	 * 	3：MS SQL Server、4：HANA、5：MongoDB、6：Cassandra、
	 * 	7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、
	 * 	11：HTTP、12：FTP、13：SFTP）
	 */
	public static final String DATASOURCE_DATA_TYPE_MYSQL = "0";
	public static final String DATASOURCE_DATA_TYPE_CASSANDRA = "6";
	
	// 调度任务状态（0：待执行、1：已取消、2：执行中、3：已停止、4：执行成功、5：执行失败）
	public static final String TASK_STATUS_WAIT = "0";
	public static final String TASK_STATUS_CANCEL = "1";
	public static final String TASK_STATUS_RUNNING = "2";
	public static final String TASK_STATUS_STOP = "3";
	public static final String TASK_STATUS_SUCCESS = "4";
	public static final String TASK_STATUS_FAIL = "5";
	public static final String TASK_STATUS_TERMINATING = "6";
	
	// 节点执行状态（0：未执行、1：执行中、2：执行成功、3：执行失败）
	public static final String WORKFLOW_NODE_INST_STATUS_WAIT = "0";
	public static final String WORKFLOW_NODE_INST_STATUS_RUNNING = "1";
	public static final String WORKFLOW_NODE_INST_STATUS_SUCCESS = "2";
	public static final String WORKFLOW_NODE_INST_STATUS_FAIL = "3";
	
	// 是否手动执行（0：否、1：是）
	public static final String TASK_IS_MANUAL_NO = "0";
	public static final String TASK_IS_MANUAL_YES = "1";

	//处理节点名称前缀
	public static final String PROCESSNODE_NAME_PREFIX = "compute-";

	//数据集对应的mongo collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
    public static final String MONGO_COL_NAME_PREFIX_PROJ = "proj_";
    public static final String MONGO_COL_NAME_PREFIX_DS = "_ds_";

    // 操作日志（0 工作流 1单节点）
	public static final int  WORKFLOW_OPERATION = 0;
	public static final int  SINGLE_TEST_OPERATION = 1;
    // 操作的类型（0 重跑 1 终止 2 取消）
	public static final int OPERATION_BY_RERUN = 0;
	public static final int OPERATION_BY_SHOTDOEN = 1;
	public static final int OPERATION_BY_CANCLE = 2;
}
