package com.quarkdata.data.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.quarkdata.data.model.vo.SchedulerTaskVO;
public interface SchedulerTaskMapper2 {

	/**
	 * 
	 * @param tenantId
	 * @param isManual
	 *            是否手动执行（0：否、1：是）
	 * @param userId 责任人
	 * @return
	 */
	List<SchedulerTaskVO> getTaskList(
			@Param("tenantId") Long tenantId,
			@Param("projectId") Long projectId,
			@Param("name") String name,
			@Param("isManual") String isManual,
			@Param("taskStatus") String taskStatus,
			@Param("bizDate") String bizDate,
			@Param("userId") Long userId,
			@Param("jobId") Long jobId,
			@Param("pageNum") Integer pageNum,
			@Param("pageSize") Integer pageSize);
	
	
	Long getTaskListCount(
			@Param("tenantId") Long tenantId,
			@Param("projectId") Long projectId,
			@Param("name") String name,
			@Param("isManual") String isManual,
			@Param("taskStatus") String taskStatus,
			@Param("bizDate") String bizDate,
			@Param("userId") Long userId,
			@Param("jobId") Long jobId);
	/**
	 *
	 * @param jobId
	 * @return
	 */
	Long deleteByJobId (@Param("jobId") Long jobId);

    /**
     *
     * @param jobId
     * @param statusOne 运行中的状态标识
     *
     * @return statusTwo 停止中的状态标识
     */
    Long getcantDelTasknumber (@Param("jobId") Long jobId,
                               @Param("statusOne") String statusOne,
                               @Param("statusTwo") String statusTwo);
	/**
	 *
	 * @param taskId
	 * @param statusOne 运行中的状态标识
	 *
	 * @return statusTwo 停止中的状态标识
	 */
    Long getcantDelTask (@Param("taskId") Long taskId,
						 @Param("statusOne") String statusOne,
						 @Param("statusTwo") String statusTwo);
}
