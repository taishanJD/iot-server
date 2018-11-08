package com.quarkdata.data.model.common;

/**
 * project_timeline 
 * 
 * `operate_type` char(1) NOT NULL COMMENT '操作类型
 * （0：created 创建、1： edited 编辑、
 *  2： deleted 删除、3：execute 执行、
 *  4：stop 停止、5：cancel 取消、
 *  6：commented 评论）'
 * 
 * @author typ 2018年5月4日
 */
public class OperateTypeConstants {
	public static final String CREATED ="0";
	public static final String EDITED ="1";
	public static final String DELETED ="2";
	public static final String EXECUTE ="3";
	public static final String STOP ="4";
	public static final String CANCEL ="5";
	public static final String COMMENTED ="6";
}
