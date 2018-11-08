package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Workflow;
import com.quarkdata.data.model.dataobj.WorkflowNodeRel;
import com.quarkdata.data.model.vo.ExecNodeTaskVO;

public interface WorkflowExecutorService {

	/**
	 * 用于定时调度执行工作流接口
	 * @param schedulerJobId
	 * @param schedulerTaskId
	 * @param bizDateParam
	 * @param sysTimeParam
	 * @return
	 */
	ResultCode<String> execute(Long schedulerJobId, Long schedulerTaskId,
			String bizDateParam, String sysTimeParam);

	/**
	 * 执行下一个处理节点准备方法
	 * @param firstNode
	 * @param workflow
	 * @param instId
	 * @param schedulerJobId
	 * @param schedulerTaskId
	 * @param bizDateParam
	 * @param sysTimeParam
	 * @return
	 */
	ExecNodeTaskVO preExecuteNextProcess(WorkflowNodeRel firstNode,
			Workflow workflow, Long instId, Long schedulerJobId,
			Long schedulerTaskId, String bizDateParam, String sysTimeParam);

	/**
	 * 节点任务处理失败
	 * @param nodeInstId
	 * @param schedulerTaskId
	 * @param schedulerJobId 
	 * @param workflowId 
	 * @param workflowId 
	 * 
	 * @return 是否继续向下执行
	 */
	boolean handlerNodeTaskFail(Long nodeInstId, Long schedulerTaskId, Long schedulerJobId, Long workflowId);
	
	/**
	 * 节点任务执行成功，流转入下一处理节点
	 * @param workflowId
	 * @param instId
	 * @param nodeInstId
	 * @param outputNodeRel
	 */
	void handlerNodeTaskSucTransNextTask(Long workflowId, Long instId,
			Long nodeInstId, WorkflowNodeRel outputNodeRel);
	
	/**
	 * 检查当前节点是否是可执行节点
	 * @param nodeRel
	 * @return
	 */
	boolean checkNextProcess(WorkflowNodeRel nodeRel);
	
	/**
	 * 检查所有节点任务是否处理成功，修改task状态
	 * @param instId
	 * @param schedulerTaskId
	 */
	void checkAllNodeTaskIsSuccess(Long instId,Long schedulerTaskId,Long schedulerJobId);
	
	/**
	 * 节点任务处理完成
	 * @param instId
	 * @param nodeInstId
	 * @param schedulerTaskId
	 * @param schedulerJobId 
	 */
	void handlerNodeTaskSucComplete(Long instId, Long nodeInstId,Long schedulerTaskId, Long schedulerJobId);
	
	/**
	 * 回调任务状态接口
	 * @param schedulerTaskId
	 * @param status
	 */
	void callbackTask(Long schedulerTaskId, String status);
	
	/**
	 * 获取当前节点的下一个节点id
	 * @param nodeId
	 * @return
	 */
	Long getNextNodeId(Long nodeId);

	/**
	 * 提交给手动执行工作流的线程池
	 */

	public void run4manual(WorkflowNodeRel nodeRel, Workflow workflow, Long instId,
						   Long schedulerJobId, Long schedulerTaskId, String bizDateParam,
						   String sysTimeParam);

	void notify(Long jobId, Long taskId, String reasonType);

	void reRun4manual(WorkflowNodeRel nodeRel, Workflow workflow, Long instId,
					  Long schedulerJobId, Long schedulerTaskId, String bizDateParam,
					  String sysTimeParam);

}
