package com.quarkdata.data.model.common;

/**
 * project_timeline
 * 
 * `operate_object_type` char(1) NOT NULL COMMENT '操作对象（
 * 0：project 项目、1：workflow 工作流、
 * 2：workflowNode 工作流处理节点(不包括dataset节点)、
 * 3：dataset 数据集、4：chart 图表、
 * 5：dashboard 仪表板、6：comment 评论、
 * 7：job 作业、8：task 任务）'
 * 
 * @author typ 2018年5月4日
 */
public class OperateObjectTypeConstants {
	public static final String PROJECT = "0";
	public static final String WORKFLOW = "1";
	public static final String WORKFLOWNODE = "2";
	public static final String DATASET = "3";
	public static final String CHART = "4";
	public static final String DASHBOARD = "5";
	public static final String COMMENT = "6";
	public static final String JOB = "7";
	public static final String TASK = "8";
	
	// 子类型
	public static final String WORKFLOWNODE_SYNC = "0"; // 同步
	public static final String WORKFLOWNODE_PRE_PROCESS = "1"; // 预处理
	public static final String WORKFLOWNODE_GROUP = "2"; // 分组
	
	public static final String DATASET_CASSANDRA = "0"; // Cassandra
	public static final String DATASET_MYSQL = "1"; // mysql
	public static final String DATASET_HIVE = "2"; // hive
}
