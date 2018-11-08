package com.quarkdata.data.dal.dao;

import java.util.List;
import com.quarkdata.data.model.dataobj.SchedulerJob;
import org.apache.ibatis.annotations.Param;
import com.quarkdata.data.model.vo.JobVO;

public interface SchedulerJobMapper2 {
	
	/**
	 * 
	 * @param tenantId
	 * @param search 查询job名称
	 * @param type	0：周期性、1：手动
	 * @return
	 */
	List<JobVO> getJobList (@Param("tenantId") Long tenantId,@Param("search") String search,@Param("type") String type);
    /**
     *
     * @param jobId
     * @return
     */
	Integer deleteJob(@Param("jobId") Long jobId);

	/**
	 * 查询单个项目下的作业列表
	 * @param tenantId 租户ID
	 * @param projectId 项目ID
	 * @return
	 */
	List<SchedulerJob> getJobListByProjectId(@Param("tenantId") Long tenantId,
												   @Param("projectId") Long projectId,
												   @Param("limitStart") Integer limitStart,
												   @Param("limitEnd") Integer limitEnd);

	/**
	 * 查询单个项目下的作业数量
	 * @param tenantId 租户ID
	 * @param projectId 项目ID
	 * @return
	 */
	Integer getJobCountByProjectId(@Param("tenantId") Long tenantId, @Param("projectId") Long projectId);
}
