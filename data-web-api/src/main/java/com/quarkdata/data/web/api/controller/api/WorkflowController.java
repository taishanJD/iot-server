package com.quarkdata.data.web.api.controller.api;

import java.util.List;
import java.util.Map;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.WorkflowNode;
import com.quarkdata.data.model.vo.AddNodeRelVO;
import com.quarkdata.data.model.vo.NodeDetail;
import com.quarkdata.data.model.vo.WorkflowDetail;
import com.quarkdata.data.model.vo.WorkflowExtend;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;
import com.quarkdata.data.service.WorkflowService;
import com.quarkdata.data.web.api.controller.RouteKey;

import javax.xml.transform.Result;

/**
 * workflow
 *
 * @author typ 2018年5月15日
 */
@RequestMapping(RouteKey.PREFIX_API + "/workflow")
@RestController
public class WorkflowController {

    @Autowired
    WorkflowService workflowService;

    /**
     * 创建workflow
     *
     * @param projectId
     * @param name
     * @param schedulerType 作业类型（0：周期性、1：手动）
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultCode<String> create(Long projectId, String name,
                                     String schedulerType) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = workflowService.createWorkflow(
                tenantId, userId, userName, projectId, name, schedulerType);
        return resultCode;
    }

    /**
     * 添加数据集节点
     *
     * @param createDatasetParam 中添加 workflowId参数 { "workflowId":1, "projectId": 1,
     *                           "dataSourceId": 2, "dataSetName": "alarm_config_ds",
     *                           "tableName": "alarm_config", "isFloatToInt": "1",
     *                           "tableStructure": [{ "index": 1, "columnTypeName": "3",
     *                           "columnName": "id", "meaning": "0" }, { "index": 2,
     *                           "columnTypeName": "3", "columnName": "product_id", "meaning":
     *                           "1" } ] }
     * @return 数据集id
     */
    @RequestMapping(value = "/add_dataset_node", method = RequestMethod.POST)
    public ResultCode<Long> addDatasetNode(
            @RequestBody Map<String, Object> createDatasetParam) {
        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode<Long> resultCode = workflowService.addDatasetNode(
                createDatasetParam, createUser);

        return resultCode;
    }

    /**
     * 从添加处理节点对话框中添加数据集节点
     *
     * @param projectId
     * @param dataSourceId
     * @param workFlowId
     * @param inputDataSetId
     * @param dataSetName
     * @return
     */
    @RequestMapping(value = "/add_dataset_node_by_process_dialog", method = RequestMethod.POST)
    public ResultCode<Long> addDatasetNodeByProcessDialog(Long projectId,
                                                          Long dataSourceId, Long workFlowId, Long inputDataSetId,
                                                          String dataSetName) {
        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode<Long> resultCode = workflowService
                .addDatasetNodeByProcessDialog(projectId, workFlowId,
                        inputDataSetId, dataSourceId, dataSetName, createUser);
        return resultCode;
    }

    /**
     * 从已经存在的数据集中添加
     *
     * @param workflowId
     * @param datasetId
     * @return
     */
    @RequestMapping(value = "/add_datasetnode_by_exist", method = RequestMethod.POST)
    public ResultCode<Long> addDatasetNodeByExist(Long workflowId,
                                                  Long datasetId) {
        ResultCode<Long> resultCode = workflowService.addDatasetNodeByExist(
                workflowId, datasetId);
        return resultCode;
    }

    /**
     * （添加处理节点）
     *
     * @param addNodeRelVO
     * @return
     */
    @RequestMapping(value = "/add_node_rel", method = RequestMethod.POST)
    public ResultCode<Long> addNodeRel(@RequestBody AddNodeRelVO addNodeRelVO) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<Long> resultCode = workflowService
                .addNodeRel(addNodeRelVO, tenantId, userId, userName);
        return resultCode;
    }

    /**
     * 修改处理节点
     *
     * @param
     */
    @RequestMapping(value = "/upd_node_rel", method = RequestMethod.POST)
    public ResultCode<Long> updNodeRel(@RequestBody AddNodeRelVO addNodeRelVO) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<Long> resultCode = workflowService
                .updNodeRel(addNodeRelVO, tenantId, userId, userName);
        return resultCode;
    }

    /**
     * 删除workflow
     *
     * @param workflowId
     * @return
     */
    @RequestMapping(value = "/del_workflow", method = RequestMethod.GET)
    public ResultCode<String> delWorkflow(String workflowIds, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = workflowService.delWorkflow(
                workflowIds, tenantId, userId, userName, projectId);
        return resultCode;
    }

    /**
     * 删除节点
     *
     * @param nodeId
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/del_node", method = RequestMethod.GET)
    public ResultCode<String> delNode(Long nodeId, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();

        ResultCode<String> resultCode = workflowService.delNode(nodeId, tenantId, userId, userName, projectId);
        return resultCode;
    }

    /**
     * 移除节点   只有数据集节点可以移除  移除不会删除数据集 只是取消关系
     *
     * @param nodeId
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/remove_node", method = RequestMethod.GET)
    public ResultCode<String> removeNode(Long nodeId, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = workflowService.removeNode(nodeId, tenantId, userId, userName, projectId);
        return resultCode;
    }

    /**
     * @param name 查询名称
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultCode<ListResult<WorkflowExtend>> list(
            Long projectId,
            String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        ResultCode<ListResult<WorkflowExtend>> resultCode = workflowService
                .getWorkflowJobList(tenantId, projectId, name, page, pageSize);
        return resultCode;
    }

    /**
     * 详情
     *
     * @param workflowId
     * @return
     */
    @RequestMapping(value = "/detail/{workflowId}", method = RequestMethod.GET)
    public ResultCode<WorkflowDetail> detail(
            @PathVariable("workflowId") Long workflowId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        ResultCode<WorkflowDetail> resultCode = workflowService.detail(
                workflowId, tenantId);
        return resultCode;
    }

    /**
     * 节点间关系 (预览工作流)
     *
     * @param workflowId
     * @return
     */
    @RequestMapping(value = "/node_rel_list/{workflowId}", method = RequestMethod.GET)
    public ResultCode<List<WorkflowNodeRelVO>> nodeRelList(
            @PathVariable("workflowId") Long workflowId) {
        ResultCode<List<WorkflowNodeRelVO>> resultCode = workflowService
                .getNodeRelList(workflowId);
        return resultCode;
    }

    /**
     * 修改工作流描述
     *
     * @return
     */
    @RequestMapping(value = "/set_workflow_desc", method = RequestMethod.POST)
    public ResultCode<String> setWorkflowDesc(Long workflowId, String desc, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();

        ResultCode<String> resultCode = workflowService.setWorkflowDesc(
                workflowId, tenantId, desc, userId, userName, projectId);
        return resultCode;
    }

    /**
     * rename
     *
     * @return
     */
    @RequestMapping(value = "/rename_workflow", method = RequestMethod.POST)
    public ResultCode<String> renameWorkflow(Long workflowId, String newName, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();

        ResultCode<String> resultCode = workflowService.renameWorkflow(
                workflowId, tenantId, newName, userId, userName, projectId);
        return resultCode;
    }

    /**
     * 节点详情
     *
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "/get_node_detail/{nodeId}", method = RequestMethod.GET)
    public ResultCode<NodeDetail> getNodeDetail(
            @PathVariable("nodeId") Long nodeId) {
        ResultCode<NodeDetail> resultCode = workflowService
                .getNodeDetail(nodeId);
        return resultCode;
    }

    /**
     * 清楚数据
     *
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "/clear_datasetnode_data/{nodeId}", method = RequestMethod.GET)
    public ResultCode<String> clearDatasetnodeData(
            @PathVariable("nodeId") Long nodeId) {
        ResultCode<String> resultCode = workflowService
                .clearDatasetnodeData(nodeId);
        return resultCode;
    }

    /**
     * 处理节点列表
     *
     * @param projectId
     * @param workflowId
     * @param nodeName
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/get_processnode_list", method = RequestMethod.GET)
    public ResultCode<ListResult<WorkflowNode>> getProcessNodeList(
            Long projectId,
            Long workflowId,
            String nodeName,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        ResultCode<ListResult<WorkflowNode>> resultCode = workflowService
                .getProcessNodeList(projectId, workflowId, nodeName, page,
                        pageSize);
        return resultCode;
    }

    /**
     * 测试节点同步处理
     *
     * @return
     */
    @RequestMapping(value = "/test_node_sync_process", method = RequestMethod.GET)
    public ResultCode<String> testNodeSyncProcess(
            Long processNodeId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        String name = UserInfoUtil.getUserInfoVO().getUser().getName();
        MDC.put("username",name);
        ResultCode<String> resultCode = workflowService.testNodeSyncProcess(processNodeId, tenantId, userId, userName, null, null);
        return resultCode;
    }

    /**
     * 立即执行工作流
     *
     * @param workflowId
     * @return
     */
    @RequestMapping(value = "/exec_workflow", method = RequestMethod.GET)
    public ResultCode<String> execWorkflow(Long jobId,
                                           @RequestParam(value = "projectId", required = false, defaultValue ="")  Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = workflowService.execWorkflow(jobId, projectId, tenantId, userId, userName);
        return resultCode;
    }

    /**
     * @ Description:
     * @ Param: 多个任务id重跑
     * @ return: 执行后的信息，成功or失败
     * @ Author: ning
     * @ Date: 2018/7/3
     */
    @RequestMapping(value = "/rerun_workflows", method = RequestMethod.GET)
    public ResultCode<String> reRunWorkflow(String taskIds) {
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode<String> resultCode = workflowService.reRunWorkflows(taskIds, userId);
        return resultCode;
    }

    /**
     * @Description: 单节点测试的重跑
     * @Param: singNodeId 单节点测试条目的ID , 给日志中添加当前用户的标识
     * @return:
     * @Author: ning
     * @Date: 2018/7/4
     */
    @RequestMapping(value = "/single_node_re_process", method = RequestMethod.GET)
    public ResultCode<String> singleNodeTestRun(String singNodeIds) {
        System.err.println(singNodeIds);
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String name = UserInfoUtil.getUserInfoVO().getUser().getName();
        MDC.put("username",name);
        ResultCode<String> resultCode = workflowService.singleNodeTestRuns(singNodeIds, userId,name);
        return resultCode;
    }

    @RequestMapping(value = "/shotdown_workflows", method = RequestMethod.GET)
    public ResultCode<String> shotdownWorkflows(String taskId) {
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode<String> resultCode = workflowService.shotdownWorkflows(taskId, userId);
        return resultCode;
    }
    @RequestMapping(value = "/cancle_workflows", method = RequestMethod.GET)
    public ResultCode<String> cancleworkflows(String taskId) {
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode<String> resultCode = workflowService.cancleworkflows(taskId, userId);
        return resultCode;
    }

}
