package com.quarkdata.data.service.impl;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.OperateObjectTypeConstants;
import com.quarkdata.data.model.common.OperateTypeConstants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.*;
import com.quarkdata.data.service.*;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.TypeUtil;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;
import com.quarkdata.data.util.db.MySqlUtils;
import com.quarkdata.tenant.model.dataobj.User;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessByCodeServiceImpl implements ProcessByCodeService {

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    DatasourceMapper datasourceMapper;

    @Autowired
    DatasetSchemaMapper datasetSchemaMapper;

    @Autowired
    WorkflowNodeMapper workflowNodeMapper;

    @Autowired
    WorkflowNodeRelMapper workflowNodeRelMapper;

    @Autowired
    DataSetSchemaService dataSetSchemaService;

    @Autowired
    ProjectTimelineService projectTimelineService;

    @Autowired
    SingleNodeTestExecuteLogMapper singleNodeTestExecuteLogMapper;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    DataSetServiceImpl dataSetService = new DataSetServiceImpl();

    /**
     * 校验sql,输入的表必须来自同一数据源
     *
     * @param sql
     * @param inputIds 输入数据集ids
     * @return
     */
    @Override
    public ResultCode validateSql(String sql, String inputIds) {

        ResultCode resultCode = null;
        String[] inputIdArray = inputIds.split(",");
        Set<String> connectNames = new HashSet<>();
        List<String> tableNames = new ArrayList<>();
        Datasource datasource = null;
        for (String inputId : inputIdArray) {
            Dataset dataset = datasetMapper.selectByPrimaryKey(Long.parseLong(inputId));
            datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
            if (null == datasource) {
                resultCode = ResultUtil.error(Messages.DATASOURCE_NOT_FOUND_CODE, Messages.DATASOURCE_NOT_FOUND_MSG);
                return resultCode;
            }
            connectNames.add(datasource.getConnName());
            tableNames.add(dataset.getTableName());
        }

        // 输入的表要是否来自同一数据源
        if (1 == connectNames.size()) {
            String deFormatSql = sql.replaceAll("\n", " ").replaceAll("2B%", "+").trim();
            // 以分号结尾
            if (!deFormatSql.endsWith(";")) {
                resultCode = ResultUtil.error(Messages.LACK_OF_SEMICOLONS_CODE, Messages.LACK_OF_SEMICOLONS_MSG);
                return resultCode;
            }
            // 不可进行增删改操作
            String[] sqlWords = deFormatSql.split(" ");
            for (String keyWord : sqlWords) {
                if ("INSERT".equalsIgnoreCase(keyWord) || "UPDATE".equalsIgnoreCase(keyWord) || "DELETE".equalsIgnoreCase(keyWord) || "ALTER".equalsIgnoreCase(keyWord)) {
                    resultCode = ResultUtil.error(Messages.CANNOT_EDIT_TABLE_CODE, Messages.CANNOT_EDIT_TABLE__MSG);
                    return resultCode;
                }
            }

            // 获取sql中使用的table
            List<String> table = getTableInSql(deFormatSql);
            // 校验sql中使用的table是否都在输入数据集中
            for (String tableName : table) {
                if (!tableNames.contains(tableName)) {
                    resultCode = ResultUtil.error(Messages.TABLE_NOT_IN_DATASET_LIST_CODE, "SQL中使用的表: "+ tableName+" 未添加到输入数据集列表");
                    return resultCode;
                }
            }
            List<Map<String, Object>> data;
            switch (datasource.getDataType()) {
                case "0": // mysql
                    MySqlUtils db = getDb(datasource);
                    try {
                        data = db.executeQuery(deFormatSql, null);
                        resultCode = ResultUtil.success(data);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        resultCode = ResultUtil.error(Messages.QUERY_FAILED_CODE, e.toString());
                    }
                    return resultCode;

                case "6": // cassandra
                    data = CassandraUtil2.executeQueryCql(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), deFormatSql);
                    resultCode = ResultUtil.success(data);
                    return resultCode;
            }
        } else {
            resultCode = ResultUtil.error(Messages.DIFF_DATASOURCE_CODE, Messages.DIFF_DATASOURCE_MSG);
        }
        return resultCode;
    }

    private  List<String> getTableInSql(String sql) {
        List<String> table = new ArrayList<>();
        String strForFrom = sql;
        while (true) {
            String from = "from\\s(.+)";
            Matcher matchFrom = Pattern.compile(from, Pattern.CASE_INSENSITIVE).matcher(strForFrom);
            if (matchFrom.find()) {
                strForFrom = matchFrom.group(1);
                String tableName = strForFrom.trim().split(" ")[0];
                while (true) {
                    // 排除from后不是表的情况
                    if (tableName.contains("(")) {
                        break;
                    }
                    if (!tableName.endsWith(";") && !tableName.endsWith(")")) {
                        table.add(tableName);
                        break;
                    } else {
                        tableName = tableName.substring(0, tableName.length() - 1);
                    }
                }
            } else break;
        }
        String strForJoin = sql;
        while (true) {
            String join = "join\\s(.+)";
            Matcher matchJoin = Pattern.compile(join, Pattern.CASE_INSENSITIVE).matcher(strForJoin);
            if (matchJoin.find()) {
                strForJoin = matchJoin.group(1);
                String tableName = strForJoin.trim().split(" ")[0];
                while (true) {
                    if (tableName.contains("(")) {
                        break;
                    }
                    if (!tableName.endsWith(";") && !tableName.endsWith(")")) {
                        table.add(tableName);
                        break;
                    } else {
                        tableName = tableName.substring(0, tableName.length() - 1);
                    }
                }
            } else break;
        }
        return table;
    }

    /**
     * 保存sql节点
     *
     * @param workflowId 工作流id
     * @param sql        sql语句
     * @param inputIds   输入数据集ids
     * @param outputId   输出数据集id
     * @param isAppend   是否覆盖 0-覆盖 1-追加
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultCode savaSql(Long workflowId, String sql, String inputIds, Long outputId, String isAppend, Long processNodeId, Long projectId) {

        //登录态获取租户和用户信息
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        User user = UserInfoUtil.getUserInfoVO().getUser();
        ResultCode resultCode;
        String deFormatSql = sql.replaceAll("2B%", "+");

        //校验sql,获取所有输入数据集表结构信息
        List<DatasetSchema> inputDatasetSchemas = inputDatasetSchema(inputIds);
        //校验sql执行后结构与输出数据集结构是否一致,不一致则删除重建
        resultCode = isOutputDatasetEqual(deFormatSql, inputIds, inputDatasetSchemas, outputId);

        if (0 != resultCode.getCode()) {
            return resultCode;
        }
        Map<String, Object> resultMap = (Map<String, Object>) resultCode.getData();
        boolean isEqual = (boolean) resultMap.get("isEqual");
        if (false == isEqual) {
            //重建表结构及数据集结构
            outputTableStructure(resultCode, outputId);
        }
        String[] inputIdArray = inputIds.split(",");
        // 判断是新建还是编辑
        if(0 == processNodeId) { // 新建
            // 添加处理节点
            Dataset dataset = datasetMapper.selectByPrimaryKey(outputId);
            logger.info("start create WorkflowNode, params--> workflowId: {}, nodeName: {}, nodeType: {}, nodeProcessType: {}, nodeProcessSubType: {}, sqlStatement: {}, isAppend: {}",
                    + workflowId, "compute_" + dataset.getDatasetName(), "1", "1", "2", deFormatSql, isAppend);
            WorkflowNode workflowNode = new WorkflowNode();
            workflowNode.setWorkflowId(workflowId);
            workflowNode.setNodeName("compute_" + dataset.getDatasetName());
            workflowNode.setNodeType("1");
            workflowNode.setNodeProcessType("1");
            workflowNode.setNodeProcessSubType("2");
            workflowNode.setSqlStatement(deFormatSql);
            workflowNode.setIsAppend(isAppend);
            workflowNode.setIsDel("0");
            workflowNode.setCreateTime(new Date());
            workflowNode.setUpdateTime(new Date());
            workflowNodeMapper.insertSelective(workflowNode);
            logger.info("create WorkflowNode: {} success!", workflowNode.getId());
            // 新建工作流处理节点时间轴
            projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.CREATED,
                    OperateObjectTypeConstants.WORKFLOWNODE, null, workflowNode.getId(), workflowNode.getNodeName(), user.getId(), user.getName());

            // 添加数据集节点及节点关系
            for (String inputId : inputIdArray) {
                addWorkFlowNode(workflowId, Long.parseLong(inputId), workflowNode.getId());
            }
            WorkflowNodeExample outputNodeExample = new WorkflowNodeExample();
            outputNodeExample.createCriteria().andDatasetIdEqualTo(outputId).andWorkflowIdEqualTo(workflowId);
            WorkflowNode outputNode = workflowNodeMapper.selectByExample(outputNodeExample).get(0);

            logger.info("start create WorkflowNodeRel, params--> workflowId: {}, parentNodeId: {}, currentNodeId: {}", + workflowId, workflowNode.getId(), outputNode.getId());
            addWorkFlowNode(workflowId, outputId, null);
            WorkflowNodeRel workflowNodeRel = new WorkflowNodeRel();
            workflowNodeRel.setWorkflowId(workflowId);
            workflowNodeRel.setParentNodeId(workflowNode.getId());
            workflowNodeRel.setCurrentNodeId(outputNode.getId());
            workflowNodeRel.setIsDel("0");
            workflowNodeRelMapper.insertSelective(workflowNodeRel);
            logger.info("create WorkflowNodeRel: {} success!", workflowNodeRel.getId());
            resultCode = ResultUtil.success(workflowNode.getId());
            return resultCode;

        } else { // 编辑
            // 判断是否需要更新处理节点(更改输出数据集或更改isAppend则需更新)
            WorkflowNodeRelExample workflowNodeRelExample1 = new WorkflowNodeRelExample();
            workflowNodeRelExample1.createCriteria().andWorkflowIdEqualTo(workflowId).andParentNodeIdEqualTo(processNodeId).andIsDelEqualTo("0");
            WorkflowNodeRel workflowNodeRel = workflowNodeRelMapper.selectByExample(workflowNodeRelExample1).get(0);
            WorkflowNode processNode = workflowNodeMapper.selectByPrimaryKey(processNodeId);
            WorkflowNodeExample wfExample = new WorkflowNodeExample();
            wfExample.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(outputId).andIsDelEqualTo("0");
            WorkflowNode outputNode = workflowNodeMapper.selectByExample(wfExample).get(0);

            if(!outputNode.getId().equals(workflowNodeRel.getCurrentNodeId())) { // 更改了输出数据集
                // 更新处理节点名称
                Dataset dataset = datasetMapper.selectByPrimaryKey(outputId);
                logger.info("start rename process node, new name is: {}", "compute_" + dataset.getDatasetName());
                WorkflowNode workflowNode = workflowNodeMapper.selectByPrimaryKey(processNodeId);
                workflowNode.setNodeName("compute_" + dataset.getDatasetName());
                workflowNodeMapper.updateByPrimaryKeySelective(workflowNode);
                logger.info("rename process node success!");
                // 编辑数据处理节点时间轴
                projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
                        OperateObjectTypeConstants.WORKFLOWNODE, null, workflowNode.getId(), workflowNode.getNodeName(), user.getId(), user.getName());

                // 解除原输出数据集与处理节点的关系
                WorkflowNodeRelExample workflowNodeRelExample = new WorkflowNodeRelExample();
                workflowNodeRelExample.createCriteria().andWorkflowIdEqualTo(workflowId).andParentNodeIdEqualTo(processNodeId).andIsDelEqualTo("0");
                logger.info("start delete WorkflowNodeRel by example:{}", workflowNodeRelExample);
                workflowNodeRelMapper.deleteByExample(workflowNodeRelExample);
                logger.info("delete success!");

                // 新建输出与处理节点关系
                WorkflowNodeExample outputNodeExample = new WorkflowNodeExample();
                outputNodeExample.createCriteria().andDatasetIdEqualTo(outputId).andWorkflowIdEqualTo(workflowId);
                WorkflowNode outNode = workflowNodeMapper.selectByExample(outputNodeExample).get(0);
                logger.info("start create WorkflowNodeRel, params--> workflowId: {}, parentNodeId: {}, currentNodeId: {}", + workflowId, processNodeId, outNode.getId());
                WorkflowNodeRel rel = new WorkflowNodeRel();
                rel.setWorkflowId(workflowId);
                rel.setParentNodeId(processNodeId);
                rel.setCurrentNodeId(outNode.getId());
                rel.setIsDel("0");
                workflowNodeRelMapper.insertSelective(rel);
                logger.info("create WorkflowNodeRel: {} success!", rel.getId());

            } else if (!isAppend.equals(processNode.getIsAppend())) {
                logger.info("start update WorkflowNode, set isAppend: {}", isAppend);
                WorkflowNode workflowNode = workflowNodeMapper.selectByPrimaryKey(processNodeId);
                workflowNode.setIsAppend(isAppend);
                workflowNodeMapper.updateByPrimaryKeySelective(workflowNode);
                logger.info("update WorkflowNode: {} success!", workflowNode.getId());
                // 编辑数据处理节点时间轴
                projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
                        OperateObjectTypeConstants.WORKFLOWNODE, null, workflowNode.getId(), workflowNode.getNodeName(), user.getId(), user.getName());
            }
            // 判断输入数据集是否变化
            WorkflowNodeRelExample workflowNodeRelExample2 = new WorkflowNodeRelExample();
            workflowNodeRelExample2.createCriteria().andWorkflowIdEqualTo(workflowId).andCurrentNodeIdEqualTo(processNodeId).andIsDelEqualTo("0");
            List<WorkflowNodeRel> workflowNodeRels = workflowNodeRelMapper.selectByExample(workflowNodeRelExample2);
            boolean inputIsChange = false;
            List<Long> inputNodeIdList = new ArrayList<>();
            for(String inputId : inputIdArray) {
                // 判断工作流和数据集节点是否连接
                WorkflowNodeExample example = new WorkflowNodeExample();
                example.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(Long.parseLong(inputId)).andIsDelEqualTo("0");
                List<WorkflowNode> opNode = workflowNodeMapper.selectByExample(example);
                if(!CollectionUtils.isEmpty(opNode)) {
                    inputNodeIdList.add(opNode.get(0).getId());
                } else {
                    // 无连接，则为新增的输入数据集
                    inputIsChange = true;
                    // 建连接
                    Dataset dataset = datasetMapper.selectByPrimaryKey(Long.parseLong(inputId));
                    logger.info("start create WorkflowNode, params--> workflowId: {}, nodeName: {}, nodeType: {}, datasetId: {}, datasetType: {}"
                            + workflowId, dataset.getDatasetName(), "0", Long.parseLong(inputId), "0");
                    WorkflowNode workflowNode = new WorkflowNode();
                    workflowNode.setWorkflowId(workflowId);
                    workflowNode.setNodeName(dataset.getDatasetName());
                    workflowNode.setNodeType("0");
                    workflowNode.setDatasetId(Long.parseLong(inputId));
                    workflowNode.setDatasetType("0");
                    workflowNode.setIsDel("0");
                    workflowNode.setCreateTime(new Date());
                    workflowNodeMapper.insertSelective(workflowNode);
                    inputNodeIdList.add(workflowNode.getId());
                    logger.info("create WorkflowNode: {} success!", workflowNode.getId());
                }
            }
            for(WorkflowNodeRel nodeRel : workflowNodeRels) {
                if (!inputNodeIdList.contains(nodeRel.getParentNodeId())) {
                    inputIsChange = true;
                }
            }
            // 若输入数据集变化，重建输入与处理节点关系
            if(inputIsChange) {
                workflowNodeRelMapper.deleteByExample(workflowNodeRelExample2);
                for(Long inputNodeId : inputNodeIdList) {
                    logger.info("start create WorkflowNodeRel, params--> workflowId: {}, parentNodeId: {}, currentNodeId: {}", + workflowId, inputNodeId, processNodeId);
                    WorkflowNodeRel rel = new WorkflowNodeRel();
                    rel.setWorkflowId(workflowId);
                    rel.setParentNodeId(inputNodeId);
                    rel.setCurrentNodeId(processNodeId);
                    rel.setIsDel("0");
                    workflowNodeRelMapper.insertSelective(rel);
                    logger.info("create WorkflowNodeRel: {} success!", rel.getId());
                }
            }
            //是否需要更新sql
            String oldSql = (String)getSql(processNodeId).getData();
            if(!oldSql.equals(deFormatSql)) {
                logger.info("start update WorkflowNode, set sqlStatement: {}", sql);
                WorkflowNode workflowNode = workflowNodeMapper.selectByPrimaryKey(processNodeId);
                workflowNode.setSqlStatement(deFormatSql);
                workflowNodeMapper.updateByPrimaryKeySelective(workflowNode);
                logger.info("update WorkflowNode: {} success!", workflowNode.getId());
                // 编辑数据处理节点时间轴
                projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
                        OperateObjectTypeConstants.WORKFLOWNODE, null, workflowNode.getId(), workflowNode.getNodeName(), user.getId(), user.getName());
            }
            resultCode = ResultUtil.success(processNodeId);
            return resultCode;
        }
    }

    private void addWorkFlowNode(Long workflowId, Long datasetId, Long processNodeId) {
        WorkflowNodeExample workflowNodeExample = new WorkflowNodeExample();
        workflowNodeExample.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(datasetId);
        List<WorkflowNode> workflowNodes = workflowNodeMapper.selectByExample(workflowNodeExample);
        Long workflowNodeId;
        if (0 == workflowNodes.size()) {
            Dataset dataset = datasetMapper.selectByPrimaryKey(datasetId);
            Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
            logger.info("start create WorkflowNode, params--> datasetId: {}, workflowId: {}, nodeName, nodeType, datasetType",
                    + datasetId, workflowId, dataset.getDatasetName(), "0", datasource.getDatasourceType());
            WorkflowNode workflowNode = new WorkflowNode();
            workflowNode.setWorkflowId(workflowId);
            workflowNode.setNodeName(dataset.getDatasetName());
            workflowNode.setNodeType("0");
            workflowNode.setDatasetId(datasetId);
            workflowNode.setDatasetType(datasource.getDatasourceType());
            workflowNode.setIsDel("0");
            workflowNode.setCreateTime(new Date());
            workflowNode.setUpdateTime(new Date());
            workflowNodeMapper.insertSelective(workflowNode);
            workflowNodeId = workflowNode.getId();
            logger.info("create WorkflowNode: {} success!", workflowNodeId);
        } else {
            workflowNodeId = workflowNodes.get(0).getId();
        }
        if (null != processNodeId) {
            logger.info("start create WorkflowNodeRel, params--> workflowId: {}, parentNodeId: {}, currentNodeId: {}", + workflowId, workflowNodeId, processNodeId);
            WorkflowNodeRel workflowNodeRel = new WorkflowNodeRel();
            workflowNodeRel.setWorkflowId(workflowId);
            workflowNodeRel.setParentNodeId(workflowNodeId);
            workflowNodeRel.setCurrentNodeId(processNodeId);
            workflowNodeRel.setIsDel("0");
            workflowNodeRelMapper.insertSelective(workflowNodeRel);
            logger.info("create WorkflowNodeRel: {} success!", workflowNodeRel.getId());
        }
    }

    /**
     * 执行sql单节点测试（保存完才可执行测试）
     *
     * @param sql
     * @param inputIds 输入数据集ids
     * @param outputId 输出数据集id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultCode executeSql(String sql, String inputIds, Long outputId, Long processNodeId, Long projectId) {

        //登录态获取租户和用户信息
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        User user = UserInfoUtil.getUserInfoVO().getUser();
        ResultCode resultCode;
        String deFormatSql = sql.replaceAll("2B%", "+");

        WorkflowNode workflowNode = workflowNodeMapper.selectByPrimaryKey(processNodeId);
        // 添加单节点测试记录
        SingleNodeTestExecuteLog execLog = new SingleNodeTestExecuteLog();
        execLog.setTenantId(tenantId);
        execLog.setWorkflowNodeId(processNodeId);
        execLog.setName(workflowNode.getNodeName());
        execLog.setType(workflowNode.getNodeProcessSubType());
        execLog.setCreateUser(user.getId());
        execLog.setCreateUsername(user.getName());
        execLog.setStartTime(new Date());
        execLog.setStatus("2");
        singleNodeTestExecuteLogMapper.insertSelective(execLog);

        Long workflowId = workflowNode.getWorkflowId();
        resultCode = handleReloadSchema(inputIds, workflowId);
        if (0 != resultCode.getCode()) {
            return resultCode;
        }
        String isAppend = workflowNode.getIsAppend();//0-覆盖 1-追加
        //校验sql
        resultCode = validateSql(sql, inputIds);
        if (0 == resultCode.getCode()) {
            int i = 0;
            // 重试次数
            while (i < 3) {
                List<Map<String, Object>> dataMap = (List<Map<String, Object>>) resultCode.getData();
                Dataset dataset = datasetMapper.selectByPrimaryKey(outputId);
                Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
                if (null == datasource) {
                    resultCode = ResultUtil.error(Messages.DATASOURCE_NOT_FOUND_CODE, Messages.DATASOURCE_NOT_FOUND_MSG);
                    i ++;
                    if (i == 2) {
                        execLog.setStatus("5");
                        execLog.setEndTime(new Date());
                        execLog.setRetryTimes(3);
                        singleNodeTestExecuteLogMapper.updateByPrimaryKey(execLog);
                        return resultCode;
                    }
                }
                //获取所有输入数据集表结构信息
                List<DatasetSchema> inputDatasetSchemas = inputDatasetSchema(inputIds);
                //校验sql执行后结构与输出数据集结构是否一致,不一致则删除重建
                resultCode = isOutputDatasetEqual(deFormatSql, inputIds, inputDatasetSchemas, outputId);
                if (0 != resultCode.getCode()) {
                    i ++;
                    if (i == 2) {
                        execLog.setStatus("5");
                        execLog.setEndTime(new Date());
                        execLog.setRetryTimes(3);
                        singleNodeTestExecuteLogMapper.updateByPrimaryKey(execLog);
                        return resultCode;
                    }
                }
                Map<String, Object> resultMap = (Map<String, Object>) resultCode.getData();
                boolean isEqual = (boolean) resultMap.get("isEqual");
                if (!isEqual) { //重建数据集结构
                    outputTableStructure(resultCode, outputId);
                }
                if(!CollectionUtils.isEmpty(dataMap)) { // 插入数据
                    resultCode = insertData(datasource, dataMap, dataset.getTableName(), isAppend, outputId);
                }
                if (0 != resultCode.getCode()) {
                    i ++;
                    if (i == 2) {
                        execLog.setStatus("5");
                        execLog.setEndTime(new Date());
                        execLog.setRetryTimes(3);
                        singleNodeTestExecuteLogMapper.updateByPrimaryKey(execLog);
                        return resultCode;
                    }
                } else {
                    break;
                }
            }
            // 执行处理节点时间轴
            projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EXECUTE,
                    OperateObjectTypeConstants.WORKFLOWNODE, null, workflowNode.getId(), workflowNode.getNodeName(), user.getId(), user.getName());
            execLog.setStatus("4");
            execLog.setEndTime(new Date());
            if (0 == i) {
                execLog.setRetryTimes(i);
            } else {
                execLog.setRetryTimes(i + 1);
            }
            singleNodeTestExecuteLogMapper.updateByPrimaryKey(execLog);
            return resultCode;
        } else {
            return resultCode;
        }
    }

    // 获取数据库连接
    private MySqlUtils getDb(Datasource datasource) {
        String host = datasource.getHost();
        Integer port = datasource.getPort();
        String dbName = datasource.getDb();
        String userName = datasource.getUsername();
        String password = datasource.getPassword();
        MySqlUtils db = new MySqlUtils(host, port.toString(), dbName, userName, password);
        return db;
    }

    // 插入数据
    private ResultCode insertData(Datasource datasource, List<Map<String, Object>> resultData, String tableName, String isAppend, Long datasetId) {
        ResultCode resultCode;
        Date now = new Date();
        SimpleDateFormat sysTimeSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat bizTimeSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dayBefore = calendar.getTime();
        String sysTime = sysTimeSdf.format(now);
        String bizTime = bizTimeSdf.format(dayBefore);
        try {
            switch (datasource.getDataType()) {
                case "0":
                    MySqlUtils db = getDb(datasource);
                    dataSetService.addBizAndSysColumnInMysql(db, datasource.getDb(), tableName);
                    if ("0".equals(isAppend)) {
                        //覆盖，删除历史数据
                        db.executeUpdate("TRUNCATE TABLE " + tableName + ";", null);
                    }
                    for (Map<String, Object> oneRecord : resultData) {
                        StringBuffer keys = new StringBuffer();
                        StringBuffer values = new StringBuffer();
                        for (String key : oneRecord.keySet()) {
                            String value = oneRecord.get(key).toString();
                            if (null != value && !"".equals(value.trim())) {
                                // 插入数据时，tinyint（1）类型的值需将true转为1，false转为0
                                DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
                                datasetSchemaExample.createCriteria().andDatasetIdEqualTo(datasetId).andColumnNameEqualTo(key);
                                DatasetSchema datasetSchema = datasetSchemaMapper.selectByExample(datasetSchemaExample).get(0);
                                if("TINYINT".equals(TypeUtil.dataSet2Mysql(datasetSchema.getColumnType()))) {
                                    if("true".equalsIgnoreCase(value)) {
                                        values.append("'1',");
                                    } else if ("false".equalsIgnoreCase(value)) {
                                        values.append("'0',");
                                    }
                                } else {
                                    values.append("'" + value + "',");
                                }
                                keys.append("`" + key + "`,");
                            }
                        }
                        keys.append("`biz_date_param`,`sys_time_param`)");
                        values.append("'" + bizTime + "', '" + sysTime + "')");
                        String sql = "INSERT INTO " + tableName + " (" + keys.toString() + " VALUES (" + values.toString();
                        //追加数据
                        db.executeUpdate(sql, null);
                    }
                    break;

                case "6":
                    dataSetService.addBizAndSysColumnInCassandra(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), tableName);
                    if ("0".equals(isAppend)) {
                        //覆盖，删除历史数据
                        String clearDataCql = "TRUNCATE TABLE " + tableName + ";";
                        CassandraUtil2.executeCql(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), clearDataCql);
                    }
                    Cluster cluster = CassandraUtil2.getCluster(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword());
                    Session session = cluster.connect(datasource.getDb());
                    for (Map<String, Object> oneRecord : resultData) {
                        StringBuffer keys = new StringBuffer();
                        StringBuffer values = new StringBuffer();
                        for (String key : oneRecord.keySet()) {
                            String value = oneRecord.get(key).toString();
                            if (null != value && !"".equals(value.trim())) {
                                // 插入数据时，text和timestamp类型的值需要加单引号
                                DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
                                datasetSchemaExample.createCriteria().andDatasetIdEqualTo(datasetId).andColumnNameEqualTo(key);
                                DatasetSchema datasetSchema = datasetSchemaMapper.selectByExample(datasetSchemaExample).get(0);
                                if("text".equals(TypeUtil.dataSet2Cassandra(datasetSchema.getColumnType())) || "timestamp".equals(TypeUtil.dataSet2Cassandra(datasetSchema.getColumnType()))) {
                                    values.append("'" + value + "',");
                                } else {
                                    values.append(value + ",");
                                }
                                keys.append(key + ",");
                            }
                        }
                        keys.append("biz_date_param,sys_time_param)");
                        values.append("'" + bizTime + "', '" + sysTime + "')");
                        String insertDataCql = "INSERT INTO " + tableName + " (" + keys.toString() + " VALUES (" + values.toString();
                        //追加数据
                        logger.info("cql is : " + insertDataCql);
                        session.execute(insertDataCql);
                    }
                    if (null != session){
                        session.close();
                        logger.info("cassandra session closed");
                    }
                    break;
            }

            resultCode = ResultUtil.success();
        } catch (SQLException e) {
            e.printStackTrace();
            resultCode = ResultUtil.error(Messages.INSERT_DATA_FAILED_CODE, Messages.INSERT_DATA_FAILED_MSG);
        }
        return resultCode;
    }

    // 获取所有输入数据集表结构信息
    private List<DatasetSchema> inputDatasetSchema(String inputIds) {
        String[] inputIdArray = inputIds.split(",");
        List<DatasetSchema> inputDatasetSchemas = new ArrayList<>();
        for (String inputId : inputIdArray) {
            Dataset dataset = datasetMapper.selectByPrimaryKey(Long.parseLong(inputId));
            DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
            datasetSchemaExample.createCriteria().andDatasetIdEqualTo(dataset.getId());
            List<DatasetSchema> datasetSchemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);
            if (0 != datasetSchemas.size()) {
                for (DatasetSchema datasetSchema : datasetSchemas) {
                    inputDatasetSchemas.add(datasetSchema);
                }
            }
        }
        return inputDatasetSchemas;
    }

    // 校验sql执行后结构与输出数据集结构是否一致
    private ResultCode isOutputDatasetEqual(String sql, String inputIds, List<DatasetSchema> inputDatasetSchemas, Long outputId){
        ResultCode resultCode;
        Set<String> sqlColumns = null;
        String[] inputIdArray = inputIds.split(",");
        Dataset dataset = datasetMapper.selectByPrimaryKey(Long.parseLong(inputIdArray[0]));
        Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
        switch (datasource.getDataType()) {
            case "0":
                try {
                    sqlColumns = getDb(datasource).getSqlColumns(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                    resultCode = ResultUtil.error(Messages.GET_SQL_COLUMNS_FAILED_CODE, Messages.GET_SQL_COLUMNS_FAILED_MSG);
                    return resultCode;
                }
                break;
            case "6":
                sqlColumns = CassandraUtil2.getSqlColumns(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), sql);
                break;
        }

        // 校验sql执行后结构与输出数据集结构是否一致
        DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
        datasetSchemaExample.createCriteria().andDatasetIdEqualTo(outputId);
        boolean isEqual = true;
        List<DatasetSchema> outputSchemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);
        if (sqlColumns.size() == outputSchemas.size()) {
            for (int i = 0; i < outputSchemas.size(); i++) {
                while (!sqlColumns.contains(outputSchemas.get(i).getColumnName().toLowerCase())) {
                    isEqual = false;
                    break;
                }
            }
        } else {
            isEqual = false;
        }

        List<DatasetSchema> sqlSchemas = new ArrayList<>();
        Set<String> outputColumnName = new HashSet<>();
        if (null != inputDatasetSchemas) {
            for (DatasetSchema datasetSchema : inputDatasetSchemas) { //获取outputColumnName: 除别名外所有字段， sqlSchemas：除别名外所有字段结构集合
                if (sqlColumns.contains(datasetSchema.getColumnName().toLowerCase())) {
                    if (0 == sqlSchemas.size()) {
                        sqlSchemas.add(datasetSchema);
                        for (DatasetSchema outputSchema : sqlSchemas) {
                            outputColumnName.add(outputSchema.getColumnName().toLowerCase());
                        }
                    } else {
                        if (!outputColumnName.contains(datasetSchema.getColumnName())) {
                            sqlSchemas.add(datasetSchema);
                            outputColumnName.add(datasetSchema.getColumnName().toLowerCase());
                        }
                    }
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isEqual", isEqual);
        resultMap.put("sqlSchemas", sqlSchemas);//sql执行后的字段结构集合
        resultMap.put("sqlColumns", sqlColumns);//sql执行后的字段名集合
        resultMap.put("outputColumnName", outputColumnName);//除别名外所有字段
        resultCode = ResultUtil.success(resultMap);
        return resultCode;
    }

    //获取输出表结构
    private ResultCode outputTableStructure(ResultCode resultCode, Long outputId) {
        Dataset dataset = datasetMapper.selectByPrimaryKey(outputId);
        Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
        //校验sql执行后结构与输出数据集结构是否一致,不一致则删除重建
        List<Map<String, Object>> tableStructures = null;
        DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
        datasetSchemaExample.createCriteria().andDatasetIdEqualTo(outputId);
        Map<String, Object> resultMap = (Map<String, Object>) resultCode.getData();
        boolean isEqual = (boolean) resultMap.get("isEqual");
        List<DatasetSchema> sqlSchemas = (List<DatasetSchema>) resultMap.get("sqlSchemas");
        Set<String> sqlColumns = (Set<String>) resultMap.get("sqlColumns");
        Set<String> outputColumnName = (Set<String>) resultMap.get("outputColumnName");
        switch (datasource.getDataType()) {
            case "0":
                if (!isEqual) {
                    //删除输出数据集结构
                    logger.info("开始删除datasetSchema");
                    datasetSchemaMapper.deleteByExample(datasetSchemaExample);
                    logger.info("删除datasetSchema结束");
                    //创建输出数据集结构
                    tableStructures = new ArrayList<>();
                    for (DatasetSchema datasetSchema : sqlSchemas) {
                        Map<String, Object> tableStructure = new HashMap<>();
                        DatasetSchema ds = new DatasetSchema();
                        ds.setDatasetId(outputId);
                        ds.setColumnName(datasetSchema.getColumnName());
                        ds.setColumnType(datasetSchema.getColumnType());
                        if("VARCHAR".equals(TypeUtil.dataSet2Mysql(datasetSchema.getColumnType()))) {
                            ds.setColumnLength(255);
                        } else {
                            ds.setColumnLength(datasetSchema.getColumnLength());
                        }
                        logger.info("开始创建datasetSchema");
                        datasetSchemaMapper.insertSelective(ds);
                        logger.info("创建datasetSchema结束");
                        tableStructure.put("columnName", ds.getColumnName());
                        tableStructure.put("columnType", TypeUtil.dataSet2Mysql(ds.getColumnType()));
                        tableStructure.put("precision", ds.getColumnLength());
                        tableStructures.add(tableStructure);
                    }
                    // 添加sql产生的别名字段
                    sqlColumns.removeAll(outputColumnName);
                    if (0 != sqlColumns.size()) {

                        for (String columnName : sqlColumns) {

                            for (DatasetSchema datasetSchema : sqlSchemas) {

                                if(columnName.equalsIgnoreCase(datasetSchema.getColumnName())) {
                                    Map<String, Object> tableStructure = new HashMap<>();
                                    DatasetSchema ds = new DatasetSchema();
                                    ds.setDatasetId(outputId);
                                    ds.setColumnName(datasetSchema.getColumnName());
                                    ds.setColumnType("7");
                                    ds.setColumnLength(255);
                                    logger.info("开始创建datasetSchema");
                                    datasetSchemaMapper.insertSelective(ds);
                                    logger.info("创建datasetSchema结束");
                                    tableStructure.put("columnName", ds.getColumnName());
                                    tableStructure.put("columnType", TypeUtil.dataSet2Mysql(ds.getColumnType()));
                                    tableStructure.put("precision", ds.getColumnLength());
                                    tableStructures.add(tableStructure);
                                }
                            }
                        }
                    }
                    MySqlUtils db = getDb(datasource);
                    //删除数据表并根据数据集结构新建
                    DataSetServiceImpl dataSetService = new DataSetServiceImpl();
                    try {
                        db.executeCreateTableSQL("DROP TABLE IF EXISTS " + dataset.getTableName() + ";");
                        db.createTableFromParamList(dataset.getTableName(), tableStructures);
                        // 追加biz_date_param和sys_time_param两个字段
                        dataSetService.addBizAndSysColumnInMysql(db, datasource.getDb(), dataset.getTableName());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        resultCode = ResultUtil.error(Messages.CREATE_TABLE_FAILED_CODE, Messages.CREATE_TABLE_FAILED_MSG);
                        return resultCode;
                    } catch (Exception e) {
                        e.printStackTrace();
                        resultCode = ResultUtil.error(Messages.DROP_TABLE_FAILED_CODE, Messages.DROP_TABLE_FAILED_MSG);
                        return resultCode;
                    }
                } else {
                    List<DatasetSchema> datasetSchemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);
                    for (DatasetSchema datasetSchema : datasetSchemas) {
                        Map<String, Object> tableStructure = new HashMap<>();
                        tableStructure.put("columnName", datasetSchema.getColumnName());
                        tableStructure.put("columnType", TypeUtil.dataSet2Mysql(datasetSchema.getColumnType()));
                        if("VARCHAR".equals(TypeUtil.dataSet2Mysql(datasetSchema.getColumnType()))) {
                            tableStructure.put("precision", 255);
                        } else {
                            tableStructure.put("precision", datasetSchema.getColumnLength());
                        }
                        tableStructures.add(tableStructure);
                    }
                }
                break;

            case "6":
                if (!isEqual) {
                    //删除输出数据集结构
                    logger.info("开始删除datasetSchema");
                    datasetSchemaMapper.deleteByExample(datasetSchemaExample);
                    logger.info("删除datasetSchema结束");
                    //创建输出数据集结构
                    tableStructures = new ArrayList<>();
                    for (DatasetSchema datasetSchema : sqlSchemas) {
                        Map<String, Object> tableStructure = new HashMap<>();
                        DatasetSchema ds = new DatasetSchema();
                        ds.setDatasetId(outputId);
                        ds.setColumnName(datasetSchema.getColumnName());
                        ds.setColumnType(datasetSchema.getColumnType());
                        logger.info("开始创建datasetSchema");
                        datasetSchemaMapper.insertSelective(ds);
                        logger.info("创建datasetSchema结束");
                        tableStructure.put("columnName", ds.getColumnName());
                        tableStructure.put("columnType", TypeUtil.dataSet2Cassandra(ds.getColumnType()));
                        tableStructures.add(tableStructure);
                    }
                    sqlColumns.removeAll(outputColumnName);
                    if (0 != sqlColumns.size()) {
                        Map<String, Object> tableStructure = new HashMap<>();
                        for (String columnName : sqlColumns) {
                            DatasetSchema ds = new DatasetSchema();
                            ds.setDatasetId(outputId);
                            ds.setColumnName(columnName);
                            ds.setColumnType("7");
                            logger.info("开始创建datasetSchema");
                            datasetSchemaMapper.insertSelective(ds);
                            logger.info("创建datasetSchema结束");
                            tableStructure.put("columnName", ds.getColumnName());
                            tableStructure.put("columnType", TypeUtil.dataSet2Cassandra(ds.getColumnType()));
                            tableStructures.add(tableStructure);
                        }
                    }
                    //删除数据表并根据数据集结构新建
                    DataSetServiceImpl dataSetService = new DataSetServiceImpl();
                    String dropTableCql = "DROP TABLE " + dataset.getTableName() + ";";
                    CassandraUtil2.executeCql(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), dropTableCql);
                    CassandraUtil2.createTableFromParamList(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), dataset.getTableName(), tableStructures.get(0).get("columnName").toString(), tableStructures);
                    // 追加biz_date_param和sys_time_param两个字段
                    dataSetService.addBizAndSysColumnInCassandra(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), dataset.getTableName());
                } else {
                    List<DatasetSchema> datasetSchemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);
                    for (DatasetSchema datasetSchema : datasetSchemas) {
                        Map<String, Object> tableStructure = new HashMap<>();
                        tableStructure.put("columnName", datasetSchema.getColumnName());
                        tableStructure.put("columnType", TypeUtil.dataSet2Cassandra(datasetSchema.getColumnType()));
                        tableStructures.add(tableStructure);
                    }
                }
                break;
        }
        resultCode = ResultUtil.success(tableStructures);
        return resultCode;
    }

    //判断输入数据集是否有父节点，无父节点则判断输入数据集与原表结构是否相等，不相等则重新拉取表结构
    private ResultCode handleReloadSchema(String inputIds, Long workflowId) {
        ResultCode resultCode;
        Boolean createTable = false;//是否需要重新建输出表，true:需要
        String[] inputIdArray = inputIds.split(",");
        for (String inputId : inputIdArray) {

            WorkflowNodeExample workflowNodeExample = new WorkflowNodeExample();
            workflowNodeExample.createCriteria().andWorkflowIdEqualTo(workflowId).andDatasetIdEqualTo(Long.parseLong(inputId));
            WorkflowNode inputWoflNode = workflowNodeMapper.selectByExample(workflowNodeExample).get(0);

            WorkflowNodeRelExample workflowNodeRelExample = new WorkflowNodeRelExample();
            workflowNodeRelExample.createCriteria().andWorkflowIdEqualTo(workflowId).andCurrentNodeIdEqualTo(inputWoflNode.getId());
            List<WorkflowNodeRel> workflowNodeRel = workflowNodeRelMapper.selectByExample(workflowNodeRelExample);

            if (!CollectionUtils.isEmpty(workflowNodeRel)) {
                Dataset dataset = datasetMapper.selectByPrimaryKey(Long.parseLong(inputId));
                Datasource datasource = datasourceMapper.selectByPrimaryKey(dataset.getDatasourceId());
                String tableName = dataset.getTableName();
                if (null == datasource) {
                    resultCode = ResultUtil.error(Messages.DATASOURCE_NOT_FOUND_CODE, Messages.DATASOURCE_NOT_FOUND_MSG);
                    return resultCode;
                }
                try {
                    List<Map<String, Object>> tableStructure = null;
                    switch (datasource.getDataType()) {

                        case "0":
                            MySqlUtils db = getDb(datasource);
                            tableStructure = db.getTableStructure(tableName);
                            break;
                        case "6":
                            tableStructure = CassandraUtil2.getTableStructure(datasource.getHost(), datasource.getPort().toString(), datasource.getUsername(), datasource.getPassword(), datasource.getDb(), dataset.getTableName());
                            break;
                    }

                    resultCode = dataSetSchemaService.getStructure(Long.parseLong(inputId));
                    Map<String, Object> resultMap = (Map<String, Object>) resultCode.getData();
                    List<Map<String, String>> datasetStructure = (List<Map<String, String>>) resultMap.get("structure");
                    //不相等重新拉取表结构
                    List<String> tableColumn = new ArrayList<>();
                    for(Map<String, Object> structure: tableStructure) {
                        tableColumn.add(structure.get("columnName").toString());
                    }
                    for(Map<String, String> structure: datasetStructure) {
                        while (!tableColumn.contains(structure.get("columnName"))) {
                            createTable = true;
                            break;
                        }
                    }
                    while (createTable) {
                        dataSetSchemaService.reloadSchema(Long.parseLong(inputId));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    resultCode = ResultUtil.error(Messages.GET_SOURCE_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_SOURCE_TABLE_STRUCTURE_FAILED_MSG);
                    return resultCode;
                }
            }
        }
        resultCode = ResultUtil.success();
        return resultCode;
    }

    public ResultCode getSql(Long nodeId) {
        ResultCode resultCode;
        WorkflowNode workflowNode = workflowNodeMapper.selectByPrimaryKey(nodeId);
        resultCode = ResultUtil.success(workflowNode.getSqlStatement());
        return  resultCode;
    }
}
