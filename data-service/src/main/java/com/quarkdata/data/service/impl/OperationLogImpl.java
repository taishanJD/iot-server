package com.quarkdata.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.*;
import com.quarkdata.data.model.vo.TaskLogDetail;
import com.quarkdata.data.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.quarkdata.data.util.ResultUtil;
import java.util.*;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OperationLogImpl implements OperationLogService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    OperationLogMapper operationLogMapper;
    @Autowired
    OperationLogMapper2 operationLogMapper2;
    @Autowired
    WorkflowNodeMapper  workflowNodeMapper;
    @Autowired
    WorkflowInstMapper workflowInstMapper;
    @Autowired
    WorkflowNodeInstMapper workflowNodeInstMapper;
    @Autowired
    SingleNodeTestExecuteLogMapper singleNodeTestExecuteLogMapper;
    @Override
    public void InsertOPerationLog(int type, Long user, int content, Long typeId) {
        OperationLog record = new OperationLog();
        record.setOperationType(type);
        record.setOperationUser(user);
        record.setOperayionContent(content);
        record.setOperationTypeId(typeId);
        record.setOperationDate(new Date());
        operationLogMapper.insert(record);
        logger.info("add operation log,obj is : {}",JSON.toJSONString(record));
    }

    @Override
    public ResultCode getOperationLog(Long type, Long typeId) {
        ResultCode resultCode;
        Map<String, Object> oneGroup;
        List<Map<String, Object>> groupList = new ArrayList<>();
        List<String> createDays = operationLogMapper2.getLogCreatDay(type, typeId);
        for(String createDay:createDays){
            List<TaskLogDetail> oneGruopOperatLog = operationLogMapper2.getOneGruopOperatLog(type, typeId, createDay);
            // 有序的map，便于传回前端用于循环
            oneGroup=new LinkedHashMap<>();
            oneGroup.put("createDay",createDay);
            oneGroup.put("oneGruopOperatLog",oneGruopOperatLog);
            groupList.add(oneGroup);
        }
        resultCode=ResultUtil.success(groupList);
        return resultCode;
    }

    @Override
    public ResultCode<TaskLogDetail> getNodeDetail(Long taskId, Long nodeId) {
        TaskLogDetail taskLogDetail = new TaskLogDetail();
        // 获得workFlowInsts
        WorkflowNode workflowNode = workflowNodeMapper.selectByPrimaryKey(nodeId);
        WorkflowInstExample example = new WorkflowInstExample();
        example.createCriteria().andWorkflowIdEqualTo(workflowNode.getWorkflowId()).andSchedulerTaskIdEqualTo(taskId);
        List<WorkflowInst> workflowInsts = workflowInstMapper.selectByExample(example);
        // 获得node_inst
        WorkflowNodeInstExample instExample = new WorkflowNodeInstExample();
        instExample.createCriteria().andWorkflowIdEqualTo(workflowNode.getWorkflowId())
                .andWorkflowInstIdEqualTo(workflowInsts.get(0).getId())
                .andWorkflowNodeIdEqualTo(nodeId);

        List<WorkflowNodeInst> workflowNodeInsts = workflowNodeInstMapper.selectByExample(instExample);
        // 填充查询的数值
        taskLogDetail.setStartTime(workflowNodeInsts.get(0).getStartTime());
        taskLogDetail.setEndTime(workflowNodeInsts.get(0).getEndTime());
        taskLogDetail.setTaskType(workflowNode.getNodeProcessSubType());

        ResultCode<TaskLogDetail> resultCode = new ResultCode<>();
        resultCode.setData(taskLogDetail);
        return resultCode;
    }

    @Override
    public ResultCode<TaskLogDetail> getSingNodeLogDetail(Long singNodeId) {
        TaskLogDetail taskLogDetail = new TaskLogDetail();
        // 获得单节点的详情
        SingleNodeTestExecuteLog executeLog = singleNodeTestExecuteLogMapper.selectByPrimaryKey(singNodeId);
        taskLogDetail.setStartTime(executeLog.getStartTime());
        taskLogDetail.setEndTime(executeLog.getEndTime());
        taskLogDetail.setTaskStatus(executeLog.getStatus());
        taskLogDetail.setCreateUser(executeLog.getCreateUsername());
        taskLogDetail.setTaskType(executeLog.getType());
        ResultCode resultCode = new ResultCode();
        resultCode.setData(taskLogDetail);
        return resultCode;
    }
}
