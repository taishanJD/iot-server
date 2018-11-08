package com.quarkdata.data.service;

import java.util.List;
import java.util.Map;

import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.SingleNodeTestExecuteLog;
import com.quarkdata.data.model.dataobj.Workflow;
import com.quarkdata.data.model.dataobj.WorkflowNode;
import com.quarkdata.data.model.vo.AddNodeRelVO;
import com.quarkdata.data.model.vo.NodeDetail;
import com.quarkdata.data.model.vo.WorkflowDetail;
import com.quarkdata.data.model.vo.WorkflowExtend;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;

public interface WorkflowService {

	// 是否依赖父节点执行状态（0：否、1：是）默认1
	public static final String IS_DEPEND_PARENT_NO = "0";
	public static final String IS_DEPEND_PARENT_YES = "1";

	/**
	 * 节点类型（ 0：dataset 数据集节点、1：process 处理节点）
	 * 值为0时，dataset_id为dataset的主键；
	 * 值为1时，node_process_type、node_process_sub_type才有值
	 */
	public static final String NODE_TYPE_DATASET = "0";
	public static final String NODE_TYPE_PROCESS = "1";
	
	
	
	/**
	 * 创建工作流
	 * @param tenantId
	 * @param userId
	 * @param userName
	 * @param projectId
	 * @param name
	 * @param schedulerType
	 * @return
	 */
	ResultCode<String> createWorkflow(Long tenantId, Long userId,String userName,
			Long projectId, String name, String schedulerType);

	/**
	 * 添加数据集节点
	 * @param createDatasetParam
	 * @param createUser
	 * @return
	 */
	ResultCode<Long> addDatasetNode(Map<String, Object> createDatasetParam,Long createUser);

	/**
	 * 删除工作流
	 * @param workflowIds
	 * @param tenantId
	 * @param userId
	 * @param userName
	 * @param projectId
	 * @return
	 */
	ResultCode<String> delWorkflow(String workflowIds, Long tenantId, Long userId, String userName, Long projectId);

	ResultCode<ListResult<Workflow>> getList(Long tenantId, String name,
			int page, int pageSize);

	/**
	 * 工作流详情
	 * @param workflowId
	 * @param tenantId
	 * @return
	 */
	ResultCode<WorkflowDetail> detail(Long workflowId, Long tenantId);

	/**
	 * 工作流预览（节点间关系）
	 * @param workflowId
	 * @return
	 */
	ResultCode<List<WorkflowNodeRelVO>> getNodeRelList(Long workflowId);

	/**
	 * 工作流列表
	 * @param tenantId
	 * @param projectId
	 * @param name
	 * @param page
	 * @param pageSize
	 * @return
	 */
	ResultCode<ListResult<WorkflowExtend>> getWorkflowJobList(Long tenantId,
			Long projectId, String name, int page, int pageSize);
	/**
	 * 添加处理节点
	 * @param addNodeRelVO
	 * @param tenantId
	 * @param userId
	 * @param userName
	 * @return
	 */
	ResultCode<Long> addNodeRel(AddNodeRelVO addNodeRelVO, Long tenantId, Long userId, String userName);
	
	
	/**
	 * 修改处理节点
	 * @param
	 */
	ResultCode<Long> updNodeRel(AddNodeRelVO addNodeRelVO, Long tenantId, Long userId, String userName);

	/**
	 * 删除节点
	 * @param nodeId
	 * @param tenantId
	 * @param userId
	 * @param userName
	 * @param projectId
	 * @return
	 */
	ResultCode<String> delNode(Long nodeId, Long tenantId, Long userId, String userName, Long projectId);

	/**
	 * 从已经存在的数据集中添加
	 * @param workflowId
	 * @param datasetId
	 * @return
	 */
	ResultCode<Long> addDatasetNodeByExist(Long workflowId, Long datasetId);

	/**
	 * 修改工作流描述
	 * @param workflowId
	 * @param tenantId
	 * @param desc
	 * @param userId
	 * @param userName
	 * @param projectId
	 * @return
	 */
	ResultCode<String> setWorkflowDesc(Long workflowId, Long tenantId, String desc, Long userId, String userName, Long projectId);

	/**
	 * 重命名
	 * @param workflowId
	 * @param tenantId
	 * @param newName
	 * @param userId
	 * @param userName
	 * @param projectId
	 * @return
	 */
	ResultCode<String> renameWorkflow(Long workflowId, Long tenantId, String newName, Long userId, String userName, Long projectId);

	/**
	 * 节点详情
	 * @param nodeId
	 * @return
	 */
	ResultCode<NodeDetail> getNodeDetail(Long nodeId);

	/**
	 * 清空数据集节点
	 * @param nodeId
	 * @return
	 */
	ResultCode<String> clearDatasetnodeData(Long nodeId);
	
	/**
	 * 从添加处理节点对话框中添加数据集节点
	 * @param projectId
	 * @param workFlowId
	 * @param inputDataSetId
	 * @param dataSourceId
	 * @param dataSetName
	 * @param createUser
	 * @return
	 */
	ResultCode<Long> addDatasetNodeByProcessDialog(Long projectId,
			Long workFlowId, Long inputDataSetId, Long dataSourceId,
			String dataSetName, Long createUser);

	/**
	 * 处理节点列表
	 * @param projectId
	 * @param workflowId
	 * @param nodeName
	 * @param page
	 * @param pageSize
	 * @return
	 */
	ResultCode<ListResult<WorkflowNode>> getProcessNodeList(Long projectId,
			Long workflowId, String nodeName, int page, int pageSize);

	/**
	 * 测试节点同步处理
	 * @param processNodeId
	 * @param tenantId
	 * @param userId
	 * @param userName
	 * @return
	 */
	ResultCode<String> testNodeSyncProcess(Long processNodeId, Long tenantId, Long userId, String userName,String flag,SingleNodeTestExecuteLog singexecLog);

	/**
	 * 执行工作流
	 * @param workflowId
	 * @return
	 */
	ResultCode<String> execWorkflow(Long jobId,Long projectId,Long tenantId,Long userId,String userName);

	/**
	 * 移除节点
	 * @param
	 */
	ResultCode<String> removeNode(Long nodeId, Long tenantId, Long userId, String userName, Long projectId);

	/**
	* @Description: 重跑
	* @Author: ning
	* @Date: 2018/7/3
	*/
	ResultCode<String> reRunWorkflows(String taskIds,Long userId);
	/**
	* @Description: 单节点测试的重跑
	* @Author: ning
	* @Date: 2018/7/4
	*/


	/**
	* @Description: 单节点重跑
	* @Param:   单节点的id  用户id
	* @return:
	* @Author: ning
	* @Date: 2018/7/18
	*/
	ResultCode<String> singleNodeTestRuns(String singNodeId,Long userId,String name);

	/**
	* @Description:  终止工作流
	* @Param:        工作流的id  用户id
	* @return:
	* @Author: ning
	* @Date: 2018/7/18
	*/
	ResultCode<String> shotdownWorkflows (String taskId,Long userId);

	/**
	* @Description:  待执行状态工作流的取消
	* @Param:		 工作流的id  用户id
	* @return:
	* @Author: ning
	* @Date: 2018/7/19
	*/
	ResultCode<String> cancleworkflows (String taskId,Long userId);
}

