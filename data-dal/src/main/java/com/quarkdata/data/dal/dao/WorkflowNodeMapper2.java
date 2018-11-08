package com.quarkdata.data.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.quarkdata.data.model.dataobj.WorkflowNode;
import com.quarkdata.data.model.vo.WorkFlowNodeVO;

public interface WorkflowNodeMapper2 {

	/**
	 * 查询一个数据集的父处理节点信息，至多有一个父处理节点
	 *
	 * @param dataSetId
	 *            数据集id
	 * @return
	 */
	WorkFlowNodeVO getDataSetPNode(@Param("dataSetId") Long dataSetId);

	/**
	 * 查询一个数据集的子处理节点信息，可能有0个或多个
	 *
	 * @param dataSetId
	 *            数据集id
	 * @return
	 */
	List<WorkFlowNodeVO> getDataSetCNode(@Param("dataSetId") Long dataSetId);

	List<WorkflowNode> getProcessNodeList(
			@Param("projectId") Long projectId,
			@Param("workflowId") Long workflowId,
			@Param("nodeName") String nodeName,
			@Param("limitStart") int limitStart,
			@Param("limitEnd") int limitEnd);
	Long getProcessNodeListCount(
			@Param("projectId") Long projectId,
			@Param("workflowId") Long workflowId,
			@Param("nodeName") String nodeName);

	List<Long> getDatasetId(
			@Param("tenantId") Long tenantId,
			@Param("projectId") Long projectId,
			@Param("workflowId") Long workflowId);
}
