package com.quarkdata.data.dal.dao;

import java.util.List;

import com.quarkdata.data.model.dataobj.SingleNodeTestExecuteLog;
import org.apache.ibatis.annotations.Param;


public interface SingleNodeTestExecuteLogMapper2 {

	List<SingleNodeTestExecuteLog> getList(
			@Param("tenantId") Long tenantId,
			@Param("schedulerJobId") Long schedulerJobId,
			@Param("name") String name, @Param("status") String status,
			@Param("type") String type, @Param("userId") Long userId,
			@Param("pageNum") Integer pageNum,
			@Param("pageSize") Integer pageSize);
	Long getListCount(
			@Param("tenantId") Long tenantId,
			@Param("schedulerJobId") Long schedulerJobId,
			@Param("name") String name, @Param("status") String status,
			@Param("type") String type, @Param("userId") Long userId);

	 void insertSelective(SingleNodeTestExecuteLog record);
}
