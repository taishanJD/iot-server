package com.quarkdata.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.model.dataobj.*;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.common.Logget.AppThread;
import com.quarkdata.data.util.common.Logget.RootLoggerUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.OperateObjectTypeConstants;
import com.quarkdata.data.model.common.OperateTypeConstants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.WorkflowExample.Criteria;
import com.quarkdata.data.model.vo.AddNodeRelVO;
import com.quarkdata.data.model.vo.DatasetNode;
import com.quarkdata.data.model.vo.DatasetNodeDTO;
import com.quarkdata.data.model.vo.NodeDetail;
import com.quarkdata.data.model.vo.ProcessNode;
import com.quarkdata.data.model.vo.WorkflowDetail;
import com.quarkdata.data.model.vo.WorkflowExtend;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.service.JobService;
import com.quarkdata.data.service.OperationLogService;
import com.quarkdata.data.service.ProjectTimelineService;
import com.quarkdata.data.service.WorkflowExecutorService;
import com.quarkdata.data.service.WorkflowService;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;
import com.quarkdata.data.util.db.DataSyncUtils;
import com.quarkdata.data.util.db.MySqlUtils2;

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class WorkflowServiceImpl implements WorkflowService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SchedulerJobMapper schedulerJobMapper;
    @Autowired
    WorkflowMapper workflowMapper;
    @Autowired
    WorkflowNodeMapper workflowNodeMapper;
    @Autowired
    WorkflowNodeMapper2 workflowNodeMapper2;
    @Autowired
    WorkflowNodeRelMapper workflowNodeRelMapper;
    @Autowired
    WorkflowInstMapper workflowInstMapper;
    @Autowired
    WorkflowNodeInstMapper workflowNodeInstMapper;
    @Autowired
    DatasetMapper datasetMapper;
    @Autowired
    DatasourceMapper datasourceMapper;
    @Autowired
    WorkflowNodeRelMapper2 workflowNodeRelMapper2;
    @Autowired
    WorkflowMapper2 workflowMapper2;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    SingleNodeTestExecuteLogMapper singleNodeTestExecuteLogMapper;
    @Autowired
    SingleNodeTestExecuteLogMapper2 singleNodeTestExecuteLogMapper2;
    @Autowired
    DataSetService dataSetService;
    @Autowired
    ProjectTimelineService projectTimelineService;
    @Autowired
    SchedulerTaskMapper schedulerTaskMapper;
    @Autowired
    WorkflowExecutorService workflowExecutorService;
    @Autowired
    OperationLogService operationLogService;
    @Autowired
    RunningLogMapper runningLogMapper;
    @Autowired
    RunningLogMapper2 runningLogMapper2;

    @Override
    public ResultCode<String> createWorkflow(Long tenantId, Long userId, String userName, Long projectId, String name,
                                             String schedulerType) {
        ResultCode<String> resultCode = new ResultCode<>();
        Date now = new Date();

        logger.info(
                "start create workflow,params --> " + "tenantId:{},userId:{},userName:{},"
                        + "projectId:{},name:{},schedulerType:{},date:{}",
                tenantId, userId, userName, projectId, name, schedulerType, now);

        // 创建job
        SchedulerJob job = new SchedulerJob();

        job.setTenantId(tenantId);
        job.setJobName(name);
        job.setJobType(schedulerType);
        job.setJobBizType(JobService.JOB_BIZ_TYPE_WORKFLOW);
        job.setIsFrozen(JobService.IS_FROZEN_NO);
        job.setIsNotify(JobService.IS_NOTIFY_NO);
        job.setIsPublish(JobService.IS_PUBLISH_NO);
        job.setCreateTime(now);
        job.setCreateUser(userId);
        job.setUpdateTime(now);
        job.setUpdateUser(userId);

        schedulerJobMapper.insertSelective(job);

        // 插入workflow
        Workflow workflow = new Workflow();

        workflow.setTenantId(tenantId);
        workflow.setProjectId(projectId);
        workflow.setSchedulerJobId(job.getId());
        workflow.setWorkflowName(name);
        workflow.setIsDependParent(WorkflowService.IS_DEPEND_PARENT_YES);
        workflow.setCreateTime(now);
        workflow.setCreateUser(userId);
        workflow.setUpdateTime(now);
        workflow.setUpdateUser(userId);
        // 验证工作流是否已存在
        WorkflowExample workflowExample = new WorkflowExample();
        workflowExample.createCriteria().andProjectIdEqualTo(projectId).andWorkflowNameEqualTo(name);
        List<Workflow> list = workflowMapper.selectByExample(workflowExample);
        if (!list.isEmpty()) {
            resultCode.setCode(Messages.WORKFLOW_NAME_ALREADY_EXIST_CODE);
            resultCode.setMsg(Messages.WORKFLOW_NAME_ALREADY_EXIST_MSG);
            return resultCode;
        }
        // 执行添加工作流
        workflowMapper.insertSelective(workflow);
        // 修改job bizId
        SchedulerJob updateJob = new SchedulerJob();
        updateJob.setId(job.getId());
        updateJob.setJobBizId(workflow.getId());
        schedulerJobMapper.updateByPrimaryKeySelective(updateJob);

        logger.info("create workflow success!");

        // 添加操作日志
        projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.CREATED,
                OperateObjectTypeConstants.WORKFLOW, null, workflow.getId(), workflow.getWorkflowName(), userId,
                userName);

        return new ResultCode<>();
    }

    @Override
    public ResultCode<Long> addDatasetNode(Map<String, Object> createDatasetParam, Long createUser) {

        logger.info("start create workflow dataset node,params --> " + "map:{},createUser:{}", createDatasetParam,
                createUser);

        Object workflowIdParam = createDatasetParam.get("workflowId");
        Long workflowId = null;
        if (workflowIdParam instanceof Integer) {
            Integer workflowIdInt = (Integer) workflowIdParam;
            workflowId = workflowIdInt.longValue();
        } else {
            workflowId = (Long) workflowIdParam;
        }

        // 创建dataset
        ResultCode addDataSetRes = dataSetService.addDataSet(createDatasetParam, createUser);
        Long dataSetId = (Long) addDataSetRes.getData();

        addDatasetNodeByDatasetId(workflowId, dataSetId);

        logger.info("create workflow dataset node success!");

        ResultCode<Long> resultCode = new ResultCode<Long>();

        resultCode.setData(dataSetId);

        return resultCode;
    }

    @Override
    public ResultCode<Long> addDatasetNodeByProcessDialog(Long projectId, Long workFlowId, Long inputDataSetId,
                                                          Long dataSourceId, String dataSetName, Long createUser) {

        logger.info(
                "start addDatasetNodeByProcessDialog,params --> " + "projectId:{},workFlowId:{},"
                        + "inputDataSetId:{},dataSourceId:{}," + "dataSetName:{},createUser:{}",
                projectId, workFlowId, inputDataSetId, dataSourceId, dataSetName, createUser);

        ResultCode resultCode = dataSetService.addDataSetInWorkFlow(projectId, dataSourceId, workFlowId, inputDataSetId,
                dataSetName, createUser);
        Long dataSetId = (Long) resultCode.getData();

        addDatasetNodeByDatasetId(workFlowId, dataSetId);

        logger.info("addDatasetNodeByProcessDialog success!");

        ResultCode<Long> rc = new ResultCode<Long>();
        rc.setData(dataSetId);
        return rc;
    }

    private void addDatasetNodeByDatasetId(Long workflowId, Long datasetId) {
        Dataset dataset = datasetMapper.selectByPrimaryKey(datasetId);

        logger.info("dataset is : {}", JSON.toJSONString(dataset));

        Date now = new Date();
        WorkflowNode record = new WorkflowNode();

        record.setWorkflowId(workflowId);
        record.setNodeName(dataset.getDatasetName());
        record.setNodeType(WorkflowService.NODE_TYPE_DATASET);
        record.setDatasetId(datasetId);
        // 查询mysql/cassandra
        Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
        record.setDatasetType(datasource.getDataType());
        record.setCreateTime(now);
        record.setUpdateTime(now);

        workflowNodeMapper.insertSelective(record);
    }

    @Override
    public ResultCode<String> delWorkflow(String workflowIds, Long tenantId, Long userId, String userName,
                                          Long projectId) {
        logger.info("start delWorkflow,params --> " + "workflowIds:{},tenantId:{}", workflowIds, tenantId);

        String[] ids = StringUtils.split(workflowIds, ',');
        for (String workflowId : ids) {
            delOneWorkflow(Long.valueOf(workflowId), tenantId, userId, userName, projectId);
        }
        // 删除该工作流之下的任务


        logger.info("delWorkflow success!");
        return new ResultCode<>();
    }

    private void delOneWorkflow(Long workflowId, Long tenantId, Long userId, String userName, Long projectId) {
        WorkflowExample example = new WorkflowExample();

        example.createCriteria().andIdEqualTo(workflowId).andTenantIdEqualTo(tenantId)
                .andIsDelEqualTo(Constants.IS_DEL_NO);

        List<Workflow> workflows = workflowMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(workflows)) {
            throw new IllegalArgumentException("can not find by workflowId:" + workflowId + ",tenantId:" + tenantId);
        }
        Workflow workflow = workflows.get(0);
        // del workflow
        Workflow updateWorkflow = new Workflow();

        updateWorkflow.setId(workflowId);
        String deleteUUID = UUID.randomUUID().toString().replaceAll("-", "");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
        String deleteDate = dateFormat.format(new Date());
        String workflowDeletedName = workflow.getWorkflowName() + "_" + deleteDate + "_" + deleteUUID + ".del";
        updateWorkflow.setWorkflowName(workflowDeletedName);
        updateWorkflow.setIsDel(Constants.IS_DEL_YES);

        workflowMapper.updateByPrimaryKeySelective(updateWorkflow);
        // del workflow node

        WorkflowNode updateNode = new WorkflowNode();

        updateNode.setIsDel(Constants.IS_DEL_YES);

        WorkflowNodeExample nodeExample = new WorkflowNodeExample();
        nodeExample.createCriteria().andWorkflowIdEqualTo(workflowId);

        workflowNodeMapper.updateByExampleSelective(updateNode, nodeExample);

        WorkflowNodeRel updateNodeRel = new WorkflowNodeRel();

        updateNodeRel.setIsDel(Constants.IS_DEL_YES);

        WorkflowNodeRelExample refExample = new WorkflowNodeRelExample();
        refExample.createCriteria().andWorkflowIdEqualTo(workflowId);

        workflowNodeRelMapper.updateByExampleSelective(updateNodeRel, refExample);
        // del workflow inst

        WorkflowInst updateInst = new WorkflowInst();

        updateInst.setIsDel(Constants.IS_DEL_YES);

        WorkflowInstExample instExample = new WorkflowInstExample();
        instExample.createCriteria().andWorkflowIdEqualTo(workflowId);

        workflowInstMapper.updateByExampleSelective(updateInst, instExample);

        WorkflowNodeInst updateNodeInst = new WorkflowNodeInst();

        updateNodeInst.setIsDel(Constants.IS_DEL_YES);

        WorkflowNodeInstExample nodeInstexample = new WorkflowNodeInstExample();
        nodeInstexample.createCriteria().andWorkflowIdEqualTo(workflowId);

        workflowNodeInstMapper.updateByExampleSelective(updateNodeInst, nodeInstexample);
        // del job

        SchedulerJob updateJob = new SchedulerJob();

        updateJob.setId(workflow.getSchedulerJobId());
        updateJob.setIsDel(Constants.IS_DEL_YES);

        schedulerJobMapper.updateByPrimaryKeySelective(updateJob);

        // 添加操作日志
        projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.DELETED,
                OperateObjectTypeConstants.WORKFLOW, null, workflowId, workflow.getWorkflowName(), userId, userName);

        // del dataset

        /*
         * WorkflowNodeExample selNodeExample = new WorkflowNodeExample();
         *
         * selNodeExample.createCriteria().andWorkflowIdEqualTo(workflowId)
         * .andNodeTypeEqualTo(WorkflowService.NODE_TYPE_DATASET);
         *
         * List<WorkflowNode> nodes = workflowNodeMapper
         * .selectByExample(selNodeExample);
         *
         * List<Long> datasetIds = getDatasetIds(nodes);
         *
         * if (CollectionUtils.isNotEmpty(datasetIds)) { DatasetExample
         * datasetExample = new DatasetExample();
         * datasetExample.createCriteria().andIdIn(datasetIds);
         * datasetMapper.deleteByExample(datasetExample); }
         */
    }

    private List<Long> getDatasetIds(List<WorkflowNode> nodes) {
        List<Long> result = new ArrayList<Long>();
        if (CollectionUtils.isNotEmpty(nodes)) {
            for (WorkflowNode n : nodes) {
                result.add(n.getDatasetId());
            }
        }
        return result;
    }

    @Override
    public ResultCode<ListResult<Workflow>> getList(Long tenantId, String name, int page, int pageSize) {
        WorkflowExample example = new WorkflowExample();

        Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(tenantId);

        if (StringUtils.isNotBlank(name)) {
            criteria.andWorkflowNameLike("%" + name + "%");
        }

        List<Workflow> list = workflowMapper.selectByExample(example);

        long count = workflowMapper.countByExample(example);

        ListResult<Workflow> listResult = new ListResult<Workflow>();
        listResult.setPageNum(page);
        listResult.setPageSize(pageSize);
        listResult.setTotalNum((int) count);
        listResult.setListData(list);

        ResultCode<ListResult<Workflow>> resultCode = new ResultCode<ListResult<Workflow>>();
        resultCode.setData(listResult);
        return resultCode;
    }

    @Override
    public ResultCode<WorkflowDetail> detail(Long workflowId, Long tenantId) {
        WorkflowExample example = new WorkflowExample();

        example.createCriteria().andIdEqualTo(workflowId).andTenantIdEqualTo(tenantId);

        List<Workflow> workflows = workflowMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(workflows)) {
            throw new IllegalArgumentException("can not find by workflowId:" + workflowId + ",tenantId:" + tenantId);
        }

        Workflow workflow = workflows.get(0);

        // get job
        SchedulerJob job = schedulerJobMapper.selectByPrimaryKey(workflow.getSchedulerJobId());
        // get datasetnode count
        WorkflowNodeExample nodeExample = new WorkflowNodeExample();
        nodeExample.createCriteria().andWorkflowIdEqualTo(workflowId)
                .andNodeTypeEqualTo(WorkflowService.NODE_TYPE_DATASET);

        long datasetnodeCount = workflowNodeMapper.countByExample(nodeExample);
        nodeExample.clear();
        // get processnode count
        nodeExample.createCriteria().andWorkflowIdEqualTo(workflowId)
                .andNodeTypeEqualTo(WorkflowService.NODE_TYPE_PROCESS);

        long processnodeCount = workflowNodeMapper.countByExample(nodeExample);

        WorkflowDetail detail = new WorkflowDetail();

        detail.setId(workflow.getId());
        detail.setName(workflow.getWorkflowName());
        detail.setDesc(workflow.getWorkflowDesc());
        detail.setCreateTime(workflow.getCreateTime());
        detail.setUpdateTime(workflow.getUpdateTime());
        detail.setSchedulerType(job.getJobType());
        detail.setDatasetNodeCount((int) datasetnodeCount);
        detail.setProcessNodeCount((int) processnodeCount);
        detail.setIsDependParent(workflow.getIsDependParent());
        detail.setSchedulerJobId(workflow.getSchedulerJobId());

        ResultCode<WorkflowDetail> resultCode = new ResultCode<WorkflowDetail>();
        resultCode.setData(detail);
        return resultCode;
    }

    @Override
    public ResultCode<List<WorkflowNodeRelVO>> getNodeRelList(Long workflowId) {
        List<WorkflowNodeRelVO> nodeRelList = workflowNodeRelMapper2.getNodeRelList(workflowId);
        // get 游离状态下的数据集节点
        List<Long> nodeIds = getNodeIds(nodeRelList);
        WorkflowNodeExample example = new WorkflowNodeExample();
        com.quarkdata.data.model.dataobj.WorkflowNodeExample.Criteria criteria = example.createCriteria();
        criteria.andWorkflowIdEqualTo(workflowId);
        if (CollectionUtils.isNotEmpty(nodeIds)) {
            criteria.andIdNotIn(nodeIds);
        }
        List<WorkflowNode> nodes = workflowNodeMapper.selectByExample(example);
        for (WorkflowNode n : nodes) {
            WorkflowNodeRelVO vo = new WorkflowNodeRelVO();
            vo.setCurrentNodeId(n.getId());
            vo.setNodeName(n.getNodeName());
            vo.setNodeType(n.getNodeType());
            vo.setNodeProcessType(n.getNodeProcessType());
            vo.setNodeProcessSubType(n.getNodeProcessSubType());
            vo.setDatasetId(n.getDatasetId());
            vo.setDatasetType(n.getDatasetType());
            nodeRelList.add(vo);
        }
        ResultCode<List<WorkflowNodeRelVO>> resultCode = new ResultCode<List<WorkflowNodeRelVO>>();

        resultCode.setData(nodeRelList);
        return resultCode;
    }

    private List<Long> getNodeIds(List<WorkflowNodeRelVO> nodeRelList) {
        List<Long> nodeIds = new ArrayList<>();
        for (WorkflowNodeRelVO n : nodeRelList) {
            nodeIds.add(n.getCurrentNodeId());
        }
        return nodeIds;
    }

    @Override
    public ResultCode<ListResult<WorkflowExtend>> getWorkflowJobList(Long tenantId, Long projectId, String name,
                                                                     int page, int pageSize) {
        int limitStart = (page - 1) * pageSize;
        int limitEnd = pageSize;
        List<WorkflowExtend> workflowList = workflowMapper2.getWorkflowList(tenantId, projectId, name, limitStart,
                limitEnd);
        Long count = workflowMapper2.getWorkflowListCount(tenantId, projectId, name);

        ListResult<WorkflowExtend> listResult = new ListResult<WorkflowExtend>();
        listResult.setPageNum(page);
        listResult.setPageSize(pageSize);
        listResult.setTotalNum(count.intValue());
        listResult.setListData(workflowList);

        ResultCode<ListResult<WorkflowExtend>> resultCode = new ResultCode<ListResult<WorkflowExtend>>();

        resultCode.setData(listResult);
        return resultCode;
    }

    @Override
    public ResultCode<Long> addNodeRel(AddNodeRelVO addNodeRelVO, Long tenantId, Long userId, String userName) {
        Date now = new Date();
        logger.info("start addNodeRel,params --> " + "addNodeRelVO:{},date:{}", JSON.toJSONString(addNodeRelVO), now);

        // 查询输入节点和输出节点 在workflow中是否存在，不存在就插入
        Long workflowId = addNodeRelVO.getWorkflowId();

        DatasetNode inputNode = addNodeRelVO.getInputNode();
        Long inputDatasetId = inputNode.getDatasetId();

        DatasetNode outputNode = addNodeRelVO.getOutputNode();
        Long outputDatasetId = outputNode.getDatasetId();

        WorkflowNodeExample example = new WorkflowNodeExample();

        example.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(inputDatasetId);

        List<WorkflowNode> nodes = workflowNodeMapper.selectByExample(example);

        WorkflowNode inputNodeAfter = null;

        if (CollectionUtils.isEmpty(nodes)) {
            // insert into workflow_node
            WorkflowNode node = new WorkflowNode();

            node.setWorkflowId(workflowId);
            node.setNodeName(inputNode.getNodeName());
            node.setNodeType(WorkflowService.NODE_TYPE_DATASET);
            node.setDatasetId(inputDatasetId);
            // dataset_type
            Dataset dataset = datasetMapper.selectByPrimaryKey(inputDatasetId);
            Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
            node.setDatasetType(datasource.getDataType());
            node.setCreateTime(now);
            node.setUpdateTime(now);

            logger.info("insert workflow node,node is : {}", JSON.toJSONString(node));

            workflowNodeMapper.insertSelective(node);

            inputNodeAfter = node;
        } else {
            inputNodeAfter = nodes.get(0);
        }

        example.clear();
        example.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(outputDatasetId);

        nodes = workflowNodeMapper.selectByExample(example);

        WorkflowNode outputNodeAfter = null;

        if (CollectionUtils.isEmpty(nodes)) {
            // insert into workflow_node
            WorkflowNode node = new WorkflowNode();

            node.setWorkflowId(workflowId);
            node.setNodeName(outputNode.getNodeName());
            node.setNodeType(WorkflowService.NODE_TYPE_DATASET);
            node.setDatasetId(outputDatasetId);
            // dataset_type
            Dataset dataset = datasetMapper.selectByPrimaryKey(outputDatasetId);
            Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
            node.setDatasetType(datasource.getDataType());
            node.setCreateTime(now);
            node.setUpdateTime(now);

            workflowNodeMapper.insertSelective(node);

            logger.info("insert workflow node,node is : {}", JSON.toJSONString(node));

            outputNodeAfter = node;
        } else {
            outputNodeAfter = nodes.get(0);
        }

        // insert process node

        ProcessNode processNode = addNodeRelVO.getProcessNode();

        WorkflowNode processNodeRecord = new WorkflowNode();

        processNodeRecord.setWorkflowId(workflowId);
        // 拼接处理节点名称 'compute-'+输出的节点名称
        String nodeName = outputNodeAfter.getNodeName();

        processNodeRecord.setIsAppend(processNode.getIsAppend());
        processNodeRecord.setNodeName(Constants.PROCESSNODE_NAME_PREFIX + nodeName);
        processNodeRecord.setNodeType(WorkflowService.NODE_TYPE_PROCESS);
        processNodeRecord.setNodeProcessType(processNode.getNodeProcessType());
        processNodeRecord.setPreprocessJson(processNode.getNodePreprocessJson());
        processNodeRecord.setNodeProcessSubType(processNode.getNodeProcessSubType());
        processNodeRecord.setCreateTime(now);
        processNodeRecord.setUpdateTime(now);

        logger.info("insert process node,node is : {}", JSON.toJSONString(processNodeRecord));

        workflowNodeMapper.insertSelective(processNodeRecord);

        // 建立节点间关系
        insertNodeRel(workflowId, inputNodeAfter, outputNodeAfter, processNodeRecord);

        logger.info("addNodeRel success!");

        // 添加操作日志
        Long projectId = addNodeRelVO.getProjectId();
        projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.CREATED,
                OperateObjectTypeConstants.WORKFLOWNODE, OperateObjectTypeConstants.WORKFLOWNODE_SYNC,
                processNodeRecord.getId(), processNodeRecord.getNodeName(), userId, userName);

        ResultCode<Long> rc = new ResultCode<Long>();
        rc.setData(processNodeRecord.getId());
        return rc;
    }

    @Override
    public ResultCode<Long> updNodeRel(AddNodeRelVO addNodeRelVO, Long tenantId, Long userId, String userName) {
        Date now = new Date();
        logger.info("start updNodeRel,params --> " + "updNodeRelVO:{},date:{}", JSON.toJSONString(addNodeRelVO), now);
        // 查询输入节点和输出节点 在workflow中是否存在，不存在就插入
        Long workflowId = addNodeRelVO.getWorkflowId();
        DatasetNode inputNode = addNodeRelVO.getInputNode();
        Long inputDatasetId = inputNode.getDatasetId();
        DatasetNode outputNode = addNodeRelVO.getOutputNode();
        Long outputDatasetId = outputNode.getDatasetId();
        WorkflowNodeExample example = new WorkflowNodeExample();
        example.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(inputDatasetId);
        List<WorkflowNode> nodes = workflowNodeMapper.selectByExample(example);
        WorkflowNode inputNodeAfter = null;
        if (CollectionUtils.isEmpty(nodes)) {
            WorkflowNode node = new WorkflowNode();
            node.setWorkflowId(workflowId);
            node.setNodeName(inputNode.getNodeName());
            node.setNodeType(WorkflowService.NODE_TYPE_DATASET);
            node.setDatasetId(inputDatasetId);
            Dataset dataset = datasetMapper.selectByPrimaryKey(inputDatasetId);
            Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
            node.setDatasetType(datasource.getDataType());
            node.setCreateTime(now);
            node.setUpdateTime(now);
            logger.info("insert workflow node,node is : {}", JSON.toJSONString(node));
            workflowNodeMapper.insertSelective(node);
            inputNodeAfter = node;
        } else {
            inputNodeAfter = nodes.get(0);
        }
        example.clear();
        example.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(outputDatasetId);
        nodes = workflowNodeMapper.selectByExample(example);
        WorkflowNode outputNodeAfter = null;
        if (CollectionUtils.isEmpty(nodes)) {
            // insert into workflow_node
            WorkflowNode node = new WorkflowNode();
            node.setWorkflowId(workflowId);
            node.setNodeName(outputNode.getNodeName());
            node.setNodeType(WorkflowService.NODE_TYPE_DATASET);
            node.setDatasetId(outputDatasetId);
            // dataset_type
            Dataset dataset = datasetMapper.selectByPrimaryKey(outputDatasetId);
            Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
            node.setDatasetType(datasource.getDataType());
            node.setCreateTime(now);
            node.setUpdateTime(now);
            workflowNodeMapper.insertSelective(node);
            logger.info("insert workflow node,node is : {}", JSON.toJSONString(node));
            outputNodeAfter = node;
        } else {
            outputNodeAfter = nodes.get(0);
        }

        // 输入输出节点创建完成 修改当前预处理节点

        ProcessNode processNode = addNodeRelVO.getProcessNode();
        WorkflowNode processNodeRecord = new WorkflowNode();
        processNodeRecord.setId(processNode.getNodeId());
        processNodeRecord.setWorkflowId(workflowId);
        // 拼接处理节点名称 输入节点名称+'-'+处理节点类型（0：pre 预处理、1：sync 数据同步、2：SQL）
//        String nodeName = inputNodeAfter.getNodeName();
//        String displayProcessType = Constants.processTypeDict.get(processNode.getNodeProcessType())
//                .get(processNode.getNodeProcessSubType());
        //processNodeRecord.setNodeName(nodeName + "-" + displayProcessType);
        processNodeRecord.setNodeType(WorkflowService.NODE_TYPE_PROCESS);
        processNodeRecord.setNodeProcessType(processNode.getNodeProcessType());
        processNodeRecord.setNodeProcessSubType(processNode.getNodeProcessSubType());
        processNodeRecord.setPreprocessJson(processNode.getNodePreprocessJson());
        processNodeRecord.setIsAppend(processNode.getIsAppend());
        processNodeRecord.setCreateTime(now);
        processNodeRecord.setUpdateTime(now);
        logger.info("update process node,node is : {}", JSON.toJSONString(processNodeRecord));
        example.clear();
        example.createCriteria().andIdEqualTo(processNode.getNodeId());
        workflowNodeMapper.updateByExampleSelective(processNodeRecord, example);
        // 修改节点间关系
        updateNodeRel(workflowId, inputNodeAfter, outputNodeAfter, processNodeRecord);

        logger.info("addNodeRel success!");

        // 添加操作日志
        Long projectId = addNodeRelVO.getProjectId();
        projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.CREATED,
                OperateObjectTypeConstants.WORKFLOWNODE, OperateObjectTypeConstants.WORKFLOWNODE_SYNC,
                processNodeRecord.getId(), processNodeRecord.getNodeName(), userId, userName);

        ResultCode<Long> rc = new ResultCode<Long>();
        rc.setData(processNodeRecord.getId());
        return rc;
    }

    private void insertNodeRel(Long workflowId, WorkflowNode inputNodeAfter, WorkflowNode outputNodeAfter,
                               WorkflowNode processNodeRecord) {
        logger.info(
                "insertNodeRel,workflowId : {},inputNodeAfter : {}," + "outputNodeAfter : {},processNodeRecord : {}",
                workflowId, JSON.toJSONString(inputNodeAfter), JSON.toJSONString(outputNodeAfter),
                JSON.toJSONString(processNodeRecord));

        // insert rel
        WorkflowNodeRelExample relExample = new WorkflowNodeRelExample();

        relExample.createCriteria().andWorkflowIdEqualTo(workflowId).andCurrentNodeIdEqualTo(inputNodeAfter.getId());

        List<WorkflowNodeRel> rels = workflowNodeRelMapper.selectByExample(relExample);
        if (CollectionUtils.isEmpty(rels)) {
            // insert input rel
            WorkflowNodeRel relRecord = new WorkflowNodeRel();

            relRecord.setWorkflowId(workflowId);
            relRecord.setCurrentNodeId(inputNodeAfter.getId());

            workflowNodeRelMapper.insertSelective(relRecord);
        }

        // insert process node rel
        WorkflowNodeRel relRecord = new WorkflowNodeRel();

        relRecord.setWorkflowId(workflowId);
        relRecord.setCurrentNodeId(processNodeRecord.getId());
        relRecord.setParentNodeId(inputNodeAfter.getId());

        workflowNodeRelMapper.insertSelective(relRecord);

        relExample.clear();
        relExample.createCriteria().andWorkflowIdEqualTo(workflowId).andCurrentNodeIdEqualTo(outputNodeAfter.getId());

        rels = workflowNodeRelMapper.selectByExample(relExample);

        if (CollectionUtils.isEmpty(rels)) {
            WorkflowNodeRel outputRelRecord = new WorkflowNodeRel();

            outputRelRecord.setWorkflowId(workflowId);
            outputRelRecord.setCurrentNodeId(outputNodeAfter.getId());
            outputRelRecord.setParentNodeId(processNodeRecord.getId());

            workflowNodeRelMapper.insertSelective(outputRelRecord);

        } else {
            WorkflowNodeRel workflowNodeRel = rels.get(0);
            workflowNodeRel.setParentNodeId(processNodeRecord.getId());

            workflowNodeRelMapper.updateByPrimaryKeySelective(workflowNodeRel);
        }
    }

    private void updateNodeRel(Long workflowId, WorkflowNode inputNodeAfter, WorkflowNode outputNodeAfter,
                               WorkflowNode processNodeRecord) {
        logger.info("updateNodeRel,workflowId : {},processNodeRecord : {}", workflowId,
                JSON.toJSONString(processNodeRecord));

        // insert rel
        WorkflowNodeRelExample relExample = new WorkflowNodeRelExample();

        relExample.createCriteria().andWorkflowIdEqualTo(workflowId).andCurrentNodeIdEqualTo(inputNodeAfter.getId());

        List<WorkflowNodeRel> rels = workflowNodeRelMapper.selectByExample(relExample);
        if (CollectionUtils.isEmpty(rels)) {
            WorkflowNodeRel relRecord = new WorkflowNodeRel();

            relRecord.setWorkflowId(workflowId);
            relRecord.setCurrentNodeId(inputNodeAfter.getId());

            workflowNodeRelMapper.insertSelective(relRecord);
        }

        relExample.clear();
        relExample.createCriteria().andWorkflowIdEqualTo(workflowId).andCurrentNodeIdEqualTo(processNodeRecord.getId());
        WorkflowNodeRel relRecord = new WorkflowNodeRel();
        relRecord.setWorkflowId(workflowId);
        relRecord.setCurrentNodeId(processNodeRecord.getId());
        relRecord.setParentNodeId(inputNodeAfter.getId());
        workflowNodeRelMapper.updateByExampleSelective(relRecord, relExample);

        relExample.clear();
        relExample.createCriteria().andWorkflowIdEqualTo(workflowId).andCurrentNodeIdEqualTo(outputNodeAfter.getId());
        rels = workflowNodeRelMapper.selectByExample(relExample);

        if (CollectionUtils.isEmpty(rels)) {
            WorkflowNodeRel outputRelRecord = new WorkflowNodeRel();
            outputRelRecord.setWorkflowId(workflowId);
            outputRelRecord.setCurrentNodeId(outputNodeAfter.getId());
            outputRelRecord.setParentNodeId(processNodeRecord.getId());
            workflowNodeRelMapper.insertSelective(outputRelRecord);

        } else {
            WorkflowNodeRel workflowNodeRel = rels.get(0);
            workflowNodeRel.setParentNodeId(processNodeRecord.getId());
            workflowNodeRelMapper.updateByPrimaryKeySelective(workflowNodeRel);
        }
    }

    @Override
    public ResultCode<String> delNode(Long nodeId, Long tenantId, Long userId, String userName, Long projectId) {

        logger.info("start delNode by nodeId,nodeId:{}", nodeId);

        WorkflowNode node = workflowNodeMapper.selectByPrimaryKey(nodeId);
        if (node == null) {
            throw new IllegalArgumentException("can not found node by nodeId :" + nodeId);
        }
        workflowNodeMapper.deleteByPrimaryKey(nodeId);
        if (node.getNodeType().equals(WorkflowService.NODE_TYPE_PROCESS)) {
            // del process node
            WorkflowNodeRelExample example = new WorkflowNodeRelExample();

            example.createCriteria().andCurrentNodeIdEqualTo(nodeId);

            workflowNodeRelMapper.deleteByExample(example);

            // 将依赖这个处理节点的节点parent_node_id设为null

            example.clear();
            example.createCriteria().andParentNodeIdEqualTo(nodeId);

            List<WorkflowNodeRel> nodes = workflowNodeRelMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(nodes)) {
                WorkflowNodeRel workflowNodeRel = nodes.get(0);
                workflowNodeRel.setParentNodeId(null);
                workflowNodeRelMapper.updateByPrimaryKey(workflowNodeRel);
            }

            // 添加操作日志
            projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.DELETED,
                    OperateObjectTypeConstants.WORKFLOWNODE, null, nodeId, node.getNodeName(), userId, userName);

        } else if (node.getNodeType().equals(WorkflowService.NODE_TYPE_DATASET)) {
            // del dataset node
            // 需要将前后的处理节点同时删除

            // 删除之前的节点
            WorkflowNodeRelExample example = new WorkflowNodeRelExample();

            example.createCriteria().andCurrentNodeIdEqualTo(nodeId);

            List<WorkflowNodeRel> nodes = workflowNodeRelMapper.selectByExample(example);

            if (CollectionUtils.isNotEmpty(nodes)) {
                WorkflowNodeRel nodeRel = nodes.get(0);
                Long parentNodeId = nodeRel.getParentNodeId();

                if (parentNodeId != null) {
                    example.clear();
                    example.createCriteria().andCurrentNodeIdEqualTo(parentNodeId);
                    workflowNodeRelMapper.deleteByExample(example);
                    // del node
                    workflowNodeMapper.deleteByPrimaryKey(parentNodeId);
                }
            }

            // 删除之后的节点
            example.clear();
            example.createCriteria().andParentNodeIdEqualTo(nodeId);

            List<WorkflowNodeRel> nodeRels = workflowNodeRelMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(nodeRels)) {
                // 设置依赖关系为null
                for (WorkflowNodeRel rel : nodeRels) {

                    example.clear();
                    example.createCriteria().andParentNodeIdEqualTo(rel.getCurrentNodeId());

                    List<WorkflowNodeRel> nos = workflowNodeRelMapper.selectByExample(example);
                    WorkflowNodeRel workflowNodeRel = nos.get(0);
                    workflowNodeRel.setParentNodeId(null);
                    workflowNodeRelMapper.updateByPrimaryKey(workflowNodeRel);
                    // del after process node
                    workflowNodeMapper.deleteByPrimaryKey(rel.getCurrentNodeId());
                }
            }
            example.clear();
            example.createCriteria().andParentNodeIdEqualTo(nodeId);
            workflowNodeRelMapper.deleteByExample(example);

            // 删除当前节点
            example.clear();
            example.createCriteria().andCurrentNodeIdEqualTo(nodeId);
            workflowNodeRelMapper.deleteByExample(example);

            Long datasetId = node.getDatasetId();

            // 数据源有写权限 则删除数据源的表 && 判断数据源的表是业务平台生成的 （带project.key）
            /*
             * Dataset dataset = datasetMapper.selectByPrimaryKey(datasetId);
             * Datasource datasource =
             * datasourceMapper.selectByPrimaryKey(dataset .getDatasourceId());
             * String isWrite = datasource.getIsWrite(); if
             * (isWrite.equals(Constants.DATASOURCE_IS_WRITE_YES)) { //
             * 判断是否是从本系统创建的数据集（有projectkey前缀） Long projectId =
             * getProjectId(node.getWorkflowId()); ProjectWithBLOBs project =
             * projectMapper.selectByPrimaryKey(projectId); String projectKey =
             * project.getProjectKey(); String tableName =
             * dataset.getTableName(); if (tableName.startsWith(projectKey +
             * Constants.TABLE_NAME_DELIMITER)) { delDatasourceTable(datasource,
             * tableName); } }
             */

            // 删除数据集相关
            dataSetService.deleteDataSet(datasetId + "");
        }

        logger.info("delNode success!");
        return new ResultCode<String>();
    }

    @Override
    public ResultCode<String> removeNode(Long nodeId, Long tenantId, Long userId, String userName, Long projectId) {
        logger.info("start removeNode by nodeId,nodeId:{}", nodeId);
        WorkflowNode node = workflowNodeMapper.selectByPrimaryKey(nodeId);
        if (node == null) {
            throw new IllegalArgumentException("can not found node by nodeId :" + nodeId);
        }
        workflowNodeMapper.deleteByPrimaryKey(nodeId);
        // 需要将前后的处理节点同时删除
        // 删除之前的节点
        WorkflowNodeRelExample example = new WorkflowNodeRelExample();
        example.createCriteria().andCurrentNodeIdEqualTo(nodeId);
        List<WorkflowNodeRel> nodes = workflowNodeRelMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(nodes)) {
            WorkflowNodeRel nodeRel = nodes.get(0);
            Long parentNodeId = nodeRel.getParentNodeId();
            if (parentNodeId != null) {
                example.clear();
                example.createCriteria().andCurrentNodeIdEqualTo(parentNodeId);
                workflowNodeRelMapper.deleteByExample(example);
                workflowNodeMapper.deleteByPrimaryKey(parentNodeId);
            }
        }
        // 删除之后的节点
        example.clear();
        example.createCriteria().andParentNodeIdEqualTo(nodeId);
        List<WorkflowNodeRel> nodeRels = workflowNodeRelMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(nodeRels)) {
            for (WorkflowNodeRel rel : nodeRels) {
                example.clear();
                example.createCriteria().andParentNodeIdEqualTo(rel.getCurrentNodeId());
                List<WorkflowNodeRel> nos = workflowNodeRelMapper.selectByExample(example);
                WorkflowNodeRel workflowNodeRel = nos.get(0);
                workflowNodeRel.setParentNodeId(null);
                workflowNodeRelMapper.updateByPrimaryKey(workflowNodeRel);
                workflowNodeMapper.deleteByPrimaryKey(rel.getCurrentNodeId());
            }
        }
        example.clear();
        example.createCriteria().andParentNodeIdEqualTo(nodeId);
        workflowNodeRelMapper.deleteByExample(example);
        // 删除当前节点
        example.clear();
        example.createCriteria().andCurrentNodeIdEqualTo(nodeId);
        workflowNodeRelMapper.deleteByExample(example);
        logger.info("removeNode success!");
        return new ResultCode<String>();
    }

    private Long getProjectId(Long workflowId) {
        Workflow workflow = workflowMapper.selectByPrimaryKey(workflowId);
        return workflow.getProjectId();
    }

    private void delDatasourceTable(Datasource datasource, String tableName) {
        String dataType = datasource.getDataType();

        String host = datasource.getHost();
        Integer port = datasource.getPort();
        String db = datasource.getDb();

        String username = datasource.getUsername();
        String password = datasource.getPassword();

        String dropTableName = tableName;
        if (dataType.equals(Constants.DATASOURCE_DATA_TYPE_MYSQL)) {
            // mysql
            MySqlUtils2.dropTable(host, String.valueOf(port), db, username, password, dropTableName);
        } else if (dataType.equals(Constants.DATASOURCE_DATA_TYPE_CASSANDRA)) {
            // cassandra
            CassandraUtil2.dropTable(host, String.valueOf(port), db, username, password, dropTableName);
        }
    }

    @Override
    public ResultCode<Long> addDatasetNodeByExist(Long workflowId, Long datasetId) {
        logger.info("start addDatasetNodeByExist,params -->" + "workflowId:{},datasetId:{}", workflowId, datasetId);

        // 判断数据集是否已经是一个节点
        WorkflowNodeExample example = new WorkflowNodeExample();
        WorkflowNodeExample.Criteria criteria = example.createCriteria();
        criteria.andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(datasetId);
        List<WorkflowNode> nodes = workflowNodeMapper.selectByExample(example);
        if (null == nodes || nodes.isEmpty()) {
            // 如果不是，才添加这个节点
            addDatasetNodeByDatasetId(workflowId, datasetId);
        }

        logger.info("addDatasetNodeByExist success!");

        ResultCode<Long> resultCode = new ResultCode<Long>();
        resultCode.setData(datasetId);
        return resultCode;
    }

    @Override
    public ResultCode<String> setWorkflowDesc(Long workflowId, Long tenantId, String desc, Long userId, String userName,
                                              Long projectId) {
        WorkflowExample example = new WorkflowExample();
        example.createCriteria().andIdEqualTo(workflowId).andTenantIdEqualTo(tenantId);

        List<Workflow> list = workflowMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(
                    "can not find workflow by workflowId:" + workflowId + ",tenantId:" + tenantId);
        }

        Workflow workflow = list.get(0);
        workflow.setWorkflowDesc(desc);
        workflowMapper.updateByPrimaryKeyWithBLOBs(workflow);
        // 添加操作日志
        projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
                OperateObjectTypeConstants.WORKFLOW, null, workflow.getId(), workflow.getWorkflowName(), userId,
                userName);

        return new ResultCode<String>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultCode<String> renameWorkflow(Long workflowId, Long tenantId, String newName, Long userId,
                                             String userName, Long projectId) {

        WorkflowExample example = new WorkflowExample();
        example.createCriteria().andIdEqualTo(workflowId).andTenantIdEqualTo(tenantId);

        List<Workflow> list = workflowMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(
                    "can not find workflow by workflowId:" + workflowId + ",tenantId:" + tenantId);
        }
        Workflow workflow = list.get(0);
        // 验证新的名称是否已经存在
        example.clear();
        example.createCriteria().andProjectIdEqualTo(workflow.getProjectId()).andWorkflowNameEqualTo(newName);
        long count = workflowMapper.countByExample(example);
        if (count > 0) {
            // 已经存在error
            return ResultCode.buildErrorResult(Messages.WORKFLOW_NAME_ALREADY_EXIST_CODE,
                    Messages.WORKFLOW_NAME_ALREADY_EXIST_MSG);
        }

        Workflow newWorkflow = new Workflow();
        newWorkflow.setId(workflow.getId());
        newWorkflow.setWorkflowName(newName);
        workflowMapper.updateByPrimaryKeySelective(newWorkflow);
        // 修改workflow名称，同时修改job name
        SchedulerJob job = new SchedulerJob();
        job.setId(workflow.getSchedulerJobId());
        job.setJobName(newName);
        schedulerJobMapper.updateByPrimaryKeySelective(job);

        // 添加操作日志
        projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
                OperateObjectTypeConstants.WORKFLOW, null, workflow.getId(), workflow.getWorkflowName(), userId,
                userName);

        return new ResultCode<>();
    }

    @Override
    public ResultCode<NodeDetail> getNodeDetail(Long nodeId) {
        WorkflowNode node = workflowNodeMapper.selectByPrimaryKey(nodeId);

        NodeDetail nodeDetail = new NodeDetail();
        nodeDetail.setNodeProcessSubType(node.getNodeProcessSubType());
        nodeDetail.setIsAppend(node.getIsAppend());
        nodeDetail.setNodeName(node.getNodeName());
        nodeDetail.setCreateTime(node.getCreateTime());
        nodeDetail.setUpdateTime(node.getUpdateTime());

        if (node.getNodeType().equals(WorkflowService.NODE_TYPE_PROCESS)) {
            String displayType = Constants.processTypeDict.get(node.getNodeProcessType())
                    .get(node.getNodeProcessSubType());
            nodeDetail.setDisplayProcessType(displayType);

            // 查看输入节点

            WorkflowNodeRelExample example = new WorkflowNodeRelExample();

            example.createCriteria().andCurrentNodeIdEqualTo(nodeId);

            List<WorkflowNodeRel> nodeRels = workflowNodeRelMapper.selectByExample(example);
            List<DatasetNodeDTO> inputs = new ArrayList<>();
            for (WorkflowNodeRel nodeRel : nodeRels) {
                Long parentNodeId = nodeRel.getParentNodeId();
                WorkflowNode wn = workflowNodeMapper.selectByPrimaryKey(parentNodeId);
                DatasetNodeDTO input = new DatasetNodeDTO();
                input.setId(wn.getId());
                input.setName(wn.getNodeName());
                input.setType(wn.getDatasetType());
                input.setDatasetId(wn.getDatasetId());
                inputs.add(input);
            }
            nodeDetail.setInputDatasetNode(inputs);
            // 查看输出节点
            example.clear();
            example.createCriteria().andParentNodeIdEqualTo(nodeId);
            WorkflowNodeRel nodeRel2 = workflowNodeRelMapper.selectByExample(example).get(0);
            Long currentNodeId = nodeRel2.getCurrentNodeId();
            WorkflowNode wn = workflowNodeMapper.selectByPrimaryKey(currentNodeId);
            DatasetNodeDTO output = new DatasetNodeDTO();
            output.setId(wn.getId());
            output.setName(wn.getNodeName());
            output.setType(wn.getDatasetType());
            output.setDatasetId(wn.getDatasetId());

            List<DatasetNodeDTO> outputs = new ArrayList<DatasetNodeDTO>();
            outputs.add(output);
            nodeDetail.setOutputDatasetNode(outputs);
        }

        ResultCode<NodeDetail> resultCode = new ResultCode<NodeDetail>();
        resultCode.setData(nodeDetail);
        return resultCode;
    }

    @Override
    public ResultCode<String> clearDatasetnodeData(Long nodeId) {
        WorkflowNode node = workflowNodeMapper.selectByPrimaryKey(nodeId);
        // Long projectId = getProjectId(node.getWorkflowId());
        // ProjectWithBLOBs project =
        // projectMapper.selectByPrimaryKey(projectId);
        // String projectKey = project.getProjectKey();

        // 查询数据集
        // Dataset dataset =
        // datasetMapper.selectByPrimaryKey(node.getDatasetId());

        logger.info("clear dataset node,nodeId is : {},datasetId is : {}", nodeId, node.getDatasetId());

        ResultCode rc = dataSetService.clearData(node.getDatasetId());

        /*
         * Long datasourceId = dataset.getDatasourceId(); Datasource datasource
         * = datasourceMapper .selectByPrimaryKey(datasourceId);
         *
         * String host = datasource.getHost(); Integer port =
         * datasource.getPort(); String db = datasource.getDb(); String username
         * = datasource.getUsername(); String password =
         * datasource.getPassword();
         *
         * String dataType = datasource.getDataType();
         *
         * String tableName = dataset.getDatasetName(); switch (dataType) { case
         * Constants.DATASOURCE_DATA_TYPE_MYSQL: MySqlUtils2.truncateTable(host,
         * String.valueOf(port), db, username, password, tableName); break; case
         * Constants.DATASOURCE_DATA_TYPE_CASSANDRA:
         * CassandraUtil2.truncateTable(host, String.valueOf(port), db,
         * username, password, tableName); break; default: break; }
         */

        return rc;
    }

    @Override
    public ResultCode<ListResult<WorkflowNode>> getProcessNodeList(Long projectId, Long workflowId, String nodeName,
                                                                   int page, int pageSize) {
        int limitStart = (page - 1) * pageSize;
        int limitEnd = pageSize;
        List<WorkflowNode> processNodeList = workflowNodeMapper2.getProcessNodeList(projectId, workflowId, nodeName,
                limitStart, limitEnd);

        Long count = workflowNodeMapper2.getProcessNodeListCount(projectId, workflowId, nodeName);

        ListResult<WorkflowNode> listResult = new ListResult<WorkflowNode>();
        listResult.setPageNum(page);
        listResult.setPageSize(pageSize);
        listResult.setTotalNum(count.intValue());
        listResult.setListData(processNodeList);

        ResultCode<ListResult<WorkflowNode>> rc = new ResultCode<ListResult<WorkflowNode>>();
        rc.setData(listResult);

        return rc;
    }

    @Override
    public ResultCode<String> testNodeSyncProcess(Long processNodeId, Long tenantId, Long userId, String userName, String flag,
                                                  SingleNodeTestExecuteLog singexecLog) {
        // 开启运行日志的获取
        RootLoggerUtils.OpenLogRecord();
        SingleNodeTestExecuteLog execLog = null;
        try {
            WorkflowNode processNode = workflowNodeMapper.selectByPrimaryKey(processNodeId);
            // 执行测试,bizDateParam sysTimeParam一致   (调到前面防止出现空指针异常)
            SimpleDateFormat syssdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat bizdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date now = new Date();
            Date yesterday = new Date(now.getTime() - 86400000L);
            String sysTimeParam = syssdf.format(now);
            String bizDateParam = bizdf.format(yesterday);
            if ("1".equals(flag)) {
                execLog = singexecLog;
            } else {
                execLog = new SingleNodeTestExecuteLog();
                execLog.setTenantId(tenantId);
                execLog.setWorkflowNodeId(processNodeId);
                execLog.setName(processNode.getNodeName());
                execLog.setType(processNode.getNodeProcessSubType());    // 0-同步 1-预处理 2-分组
                execLog.setCreateUser(userId);
                execLog.setCreateUsername(userName);
            }
            execLog.setStartTime(new Date());
            WorkflowNodeRelExample relExample = new WorkflowNodeRelExample();

            relExample.createCriteria().andCurrentNodeIdEqualTo(processNodeId);

            WorkflowNodeRel workflowNodeRel = workflowNodeRelMapper.selectByExample(relExample).get(0);

            Long inputNodeId = workflowNodeRel.getParentNodeId();

            relExample.clear();
            relExample.createCriteria().andParentNodeIdEqualTo(processNodeId);
            WorkflowNodeRel outputWorkflowNodeRel = workflowNodeRelMapper.selectByExample(relExample).get(0);

            Long outputNodeId = outputWorkflowNodeRel.getCurrentNodeId();

            WorkflowNode inputNode = workflowNodeMapper.selectByPrimaryKey(inputNodeId);

            WorkflowNode outputNode = workflowNodeMapper.selectByPrimaryKey(outputNodeId);

            // input
            Dataset inputDataset = datasetMapper.selectByPrimaryKey(inputNode.getDatasetId());

            Datasource inputDatasource = datasourceMapper.selectByPrimaryKey(inputDataset.getDatasourceId());

            String inputTableName = inputDataset.getTableName();

            String inputHost = inputDatasource.getHost();

            Integer inputPort = inputDatasource.getPort();

            String inputDb = inputDatasource.getDb();

            String inputUsername = inputDatasource.getUsername();

            String inputPassword = inputDatasource.getPassword();

            // output
            Dataset outputDataset = datasetMapper.selectByPrimaryKey(outputNode.getDatasetId());
            Datasource outputDatasource = datasourceMapper.selectByPrimaryKey(outputDataset.getDatasourceId());

            String outputTableName = outputDataset.getTableName();

            String outputHost = outputDatasource.getHost();

            Integer outputPort = outputDatasource.getPort();

            String outputDb = outputDatasource.getDb();

            String outputUsername = outputDatasource.getUsername();

            String outputPassword = outputDatasource.getPassword();

			/*// 执行测试,bizDateParam sysTimeParam一致
			SimpleDateFormat syssdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat bizdf = new SimpleDateFormat("yyyy-MM-dd ");
			Date now = new Date();
			Date yesterday = new Date(now.getTime() - 86400000L);
			String sysTimeParam = syssdf.format(now);
			String bizDateParam = bizdf.format(yesterday);
			if ("1".equals(flag)) {
				execLog = singexecLog;
			} else {
				execLog = new SingleNodeTestExecuteLog();
				execLog.setTenantId(tenantId);
				execLog.setWorkflowNodeId(processNodeId);
				execLog.setName(processNode.getNodeName());
				execLog.setType(processNode.getNodeType());    // 0-同步 1-预处理 2-分组
				execLog.setCreateUser(userId);
				execLog.setCreateUsername(userName);
			}*/
            if (inputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_MYSQL)
                    && outputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_MYSQL)) {
                // mysql to mysql
                DataSyncUtils.syncDataFromMysqlToMysql(inputHost, inputPort, inputDb, inputUsername, inputPassword,
                        inputTableName, outputHost, outputPort, outputDb, outputUsername, outputPassword,
                        outputTableName, bizDateParam, sysTimeParam, processNode.getIsAppend());
            } else if (inputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_MYSQL)
                    && outputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_CASSANDRA)) {
                // mysql to cassandra
                DataSyncUtils.syncDataFromMysqlToCassandra(inputHost, inputPort, inputDb, inputUsername, inputPassword,
                        inputTableName, outputHost, outputPort, outputDb, outputUsername, outputPassword,
                        outputTableName, bizDateParam, sysTimeParam, processNode.getIsAppend());
            } else if (inputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_CASSANDRA)
                    && outputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_MYSQL)) {
                // cassandra to mysql
                DataSyncUtils.syncDataFromCassandraToMysql(inputHost, inputPort, inputDb, inputUsername, inputPassword,
                        inputTableName, outputHost, outputPort, outputDb, outputUsername, outputPassword,
                        outputTableName, bizDateParam, sysTimeParam, processNode.getIsAppend());
            } else if (inputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_CASSANDRA)
                    && outputDatasource.getDataType().equals(Constants.DATASOURCE_DATA_TYPE_CASSANDRA)) {
                // cassandra to cassandra
                DataSyncUtils.syncDataFromCassandraToCassandra(inputHost, inputPort, inputDb, inputUsername,
                        inputPassword, inputTableName, outputHost, outputPort, outputDb, outputUsername, outputPassword,
                        outputTableName, bizDateParam, sysTimeParam, processNode.getIsAppend());
            }
            // 执行完同步测试后 将输出数据集的is_sync字段改为1
            outputDataset.setIsSync("1");
            datasetMapper.updateByPrimaryKey(outputDataset);
            execLog.setStatus("4");// 0：待执行、1：已取消、2：执行中、3：已停止、4：执行成功、5：执行失败
            execLog.setEndTime(new Date());
            if ("1".equals(flag)) {
                singleNodeTestExecuteLogMapper.updateByPrimaryKey(execLog);  // 重跑更新数据
            } else {
                singleNodeTestExecuteLogMapper.insertSelective(execLog);    // 执行插入数据
            }
        } catch (Exception e) {
            logger.error("exec test task", e);
            execLog.setStatus("5");   // 更新状态后不更新数据库?????
            execLog.setEndTime(new Date());
            if ("1".equals(flag)) {
                singleNodeTestExecuteLogMapper.updateByPrimaryKey(execLog);  // 重跑更新数据
            } else {
                singleNodeTestExecuteLogMapper.insertSelective(execLog);    // 执行插入数据
            }
        } finally {
            // 关闭运行日志的获取，并获得测试数据
            StringBuffer sb = new StringBuffer();
            StringBuffer sb1 = new StringBuffer();
            String temporary = "";      // 获取临时的变量
            logger.info("|----------------");
            List<String> assembleLog = AppThread.getAssembleLog();
            int temporaryFlag = 1;
            for (String log : assembleLog) {
                if (log.contains("[" + userName + "]")) {  //得到所有的带当前name的数据
                    sb.append(log);
                }
                if (log.contains("[" + userName + "]") && log.contains("[ERROR]") && temporaryFlag == 1) {
                    temporary = log;               // 获得第一次错误到最后的错误，理论上来说是可以的
                    temporaryFlag = 0;
                }
                sb1.append(log);  // 得到一个完整的日志字符串，然后进行截取
            }
            // 存在有错误信息
            //System.err.println("ningning"+sb1 + "ningning");
            if (temporary != null && temporary != "") {
                String more = sb1.substring(sb1.indexOf(temporary), sb1.lastIndexOf("at"));
                System.out.println("moremore" + more + "moremore");
                System.err.println(sb1.indexOf(temporary) + ":" + sb1.indexOf(temporary) + temporary.length());
                sb.replace(sb.indexOf(temporary), sb.indexOf(temporary) + temporary.length(), more);
            }
            System.err.println("guoning" + sb + "guoning");
            // 根据flag判断是执行还是重跑，使用不同地方传递过来的typeId，可以避免首次执行的一些逻辑上的错误
            long typeId;
            if ("1".equals(flag)) {
                typeId = singexecLog.getId();
            } else {
                typeId = execLog.getId();
            }
            // 获得运行日志后选择插入到数据库(如果存在直接更新，不存直接创建更新)
            RunningLogExample example = new RunningLogExample();
            example.createCriteria().andTypeIdEqualTo(typeId);
            List<RunningLog> runningLogs = runningLogMapper.selectByExample(example);
            if (runningLogs.size() > 0) {
                runningLogMapper2.updateLogContent(typeId, sb.toString());
            } else {
                RunningLog runningLog = new RunningLog();
                runningLog.setType("2"); //0:工作流的运行日志 1:工作流中的单个节点的运行日志  2：单节点的运行日志
                runningLog.setTypeId(typeId);
                runningLog.setLogContent(sb.toString());
                runningLogMapper.insert(runningLog);
            }
        }
        return new ResultCode<String>();
    }

    @Override
    public ResultCode<String> singleNodeTestRuns(String singNodeId, Long userId, String name) {   // 此处的name是为添加运行日志来使用的
        String[] ids = singNodeId.split(",");
        for (String id : ids) {
            SingleNodeTestExecuteLog singleNodeTest = singleNodeTestExecuteLogMapper.selectByPrimaryKey(Long.parseLong(id));
            Long workflowNodeId = singleNodeTest.getWorkflowNodeId();
            testNodeSyncProcess(workflowNodeId, null, null, name, "1", singleNodeTest);

            // 添加重跑的操作日志
            operationLogService.InsertOPerationLog(Constants.SINGLE_TEST_OPERATION, userId, Constants.OPERATION_BY_RERUN, Long.parseLong(id));
        }
        return new ResultCode<String>();
    }

    @Override
    public ResultCode<String> execWorkflow(Long jobId, Long projectId, Long tenantId, Long userId, String userName) {
        SchedulerJob job = schedulerJobMapper.selectByPrimaryKey(jobId);

        if (job == null) {
            throw new RuntimeException("can not found job by jobId : " + jobId);
        }

        String jobBizType = job.getJobBizType();
        if (jobBizType.equals("0")) {
            // 工作流
            runWorkflowTask(job);
        }

        // 添加时间轴的操作
        if (projectId != null && tenantId != null && userId != null && userName != null) {
            projectTimelineService.insertOptLog(projectId, tenantId,
                    OperateTypeConstants.EDITED, OperateObjectTypeConstants.JOB,
                    null, job.getId(), job.getJobName(), userId, userName);
        }
        return new ResultCode<String>();
    }

    // 执行单个重跑的操作
    public void reRunWorkflow(Long taskId) {
        // 得到task更新UpdateTime
        SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(taskId);
        Date date = new Date();
        task.setUpdateTime(date);
        long jobId = task.getSchedulerJobId();
        String bizDateParam = task.getBizDateParam();
        String sysTimeParam = task.getSysTimeParam();
        schedulerTaskMapper.updateByPrimaryKey(task);

        //获得workflow
        WorkflowExample flowExample = new WorkflowExample();
        flowExample.createCriteria().andSchedulerJobIdEqualTo(jobId);
        List<Workflow> workflows = workflowMapper.selectByExample(flowExample);

        //获得workflowInst
        WorkflowInstExample instExample = new WorkflowInstExample();
        instExample.createCriteria().andSchedulerTaskIdEqualTo(taskId);
        List<WorkflowInst> workflowInst = workflowInstMapper.selectByExample(instExample);

        //生成workflownoderel实例
        WorkflowNodeRelExample relExample = new WorkflowNodeRelExample();
        relExample.createCriteria().andWorkflowIdEqualTo(workflows.get(0).getId()).andParentNodeIdIsNull();
        List<WorkflowNodeRel> topNodes = workflowNodeRelMapper.selectByExample(relExample);
        for (WorkflowNodeRel topNode : topNodes) {
            if (workflowExecutorService.checkNextProcess(topNode)) {
                // 提交执行任务
                workflowExecutorService.reRun4manual(topNode, workflows.get(0), workflowInst.get(0).getId(), jobId, task.getId(),
                        bizDateParam, sysTimeParam);
            }
        }
    }

    @Override
    public ResultCode<String> reRunWorkflows(String taskIds, Long userId) {
        String[] ids = taskIds.split(",");
        for (String id : ids) {
            reRunWorkflow(Long.valueOf(id));
            // 添加重跑的操作日志  TODO
            operationLogService.InsertOPerationLog(Constants.WORKFLOW_OPERATION, userId, Constants.OPERATION_BY_RERUN, Long.parseLong(id));
        }

        return new ResultCode<String>();
    }

    private void runWorkflowTask(SchedulerJob job) {
        // 查询workflow
        Workflow workflow = workflowMapper.selectByPrimaryKey(job.getJobBizId());

        // 根据job生成任务实例
        SchedulerTask task = new SchedulerTask();

        task.setSchedulerJobId(job.getId());
        task.setProjectId(workflow.getProjectId());
        task.setTaskStatus(Constants.TASK_STATUS_WAIT);
        task.setIsManual(Constants.TASK_IS_MANUAL_YES);
        // 立即执行 biz_date_param和sys_time_param保存一致
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String bizDateParam = sdf.format(now);
        String sysTimeParam = sdf2.format(now);

        task.setBizDateParam(bizDateParam);
        task.setSysTimeParam(sysTimeParam);
        task.setCreateTime(now);
        task.setUpdateTime(now);

        schedulerTaskMapper.insertSelective(task);

        // 生成workflow实例
        WorkflowInst workflowInst = new WorkflowInst();

        workflowInst.setWorkflowId(workflow.getId());
        workflowInst.setSchedulerTaskId(task.getId());

        workflowInstMapper.insertSelective(workflowInst);
        // 生成workflow node实例
        WorkflowNodeRelExample relExample = new WorkflowNodeRelExample();
        relExample.createCriteria().andWorkflowIdEqualTo(workflow.getId()).andParentNodeIdIsNull();
        List<WorkflowNodeRel> topNodes = workflowNodeRelMapper.selectByExample(relExample);

        for (WorkflowNodeRel topNode : topNodes) {
            if (workflowExecutorService.checkNextProcess(topNode)) {
                // 提交执行任务
                workflowExecutorService.run4manual(topNode, workflow, workflowInst.getId(), job.getId(), task.getId(),
                        bizDateParam, sysTimeParam);
            }
        }
    }

    @Override
    public ResultCode<String> shotdownWorkflows(String taskId, Long userId) {
        // 修改状态status改为终止中的状态，然后去run方法中寻找status的状态，用来停止线程的执行
        SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(Long.parseLong(taskId));
        task.setTaskStatus(Constants.TASK_STATUS_TERMINATING);
        schedulerTaskMapper.updateByPrimaryKey(task);
        // 添加终止的操作日志
        operationLogService.InsertOPerationLog(Constants.WORKFLOW_OPERATION, userId, Constants.OPERATION_BY_SHOTDOEN, Long.parseLong(taskId));
        return new ResultCode<String>();
    }

    @Override
    public ResultCode<String> cancleworkflows(String taskId, Long userId) {
        // 将工作流的状态修改为停止状态
        SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(Long.parseLong(taskId));
        task.setTaskStatus(Constants.TASK_STATUS_CANCEL);
        schedulerTaskMapper.updateByPrimaryKey(task);
        // 添加取消的操作日志
        operationLogService.InsertOPerationLog(Constants.WORKFLOW_OPERATION, userId, Constants.OPERATION_BY_CANCLE, Long.parseLong(taskId));
        return new ResultCode<String>();
    }


}
