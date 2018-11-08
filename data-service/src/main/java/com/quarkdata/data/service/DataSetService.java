package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;

import java.util.Map;

public interface DataSetService {


    /**
     * 请求参数用json写在requestbody中，添加dataset，包含表结构数据
     * @param paramMap
     * @return
     */
    ResultCode addDataSet(Map<String, Object> paramMap,Long createUser);

//    /**
//     * 新建数据集，直接对应源数据库中的源表，不新建表
//     * @param projectId 项目id
//     * @param dataSourceId 数据源id
//     * @param dataSetName  数据集名称
//     * @param tableName  表名
//     * @param isFloatToInt  是否浮点型转整型：0否1是
//     * @param createUser  创建人id
//     * @return
//     */
//    ResultCode addDataSet(Long projectId, Long dataSourceId, String dataSetName, String tableName, String isFloatToInt ,Long createUser);

    /**
     * 在工作流中创建某种数据处理时，新建数据集作为输出，不指定已有的table名，而是新建一个空table，表结构复制输入数据集的表结构，表名为project key加data_set名
     * @param projectId 项目id
     * @param dataSourceId 数据源id
     * @param workFlowId 工作流id
     * @param dataSetName  数据集名称
     * @param createUser  创建人id
     * @return
     */
    ResultCode addDataSetInWorkFlow(Long projectId, Long dataSourceId, Long workFlowId, Long inputDataSetId, String dataSetName ,Long createUser);


    /**
     * 获取dataset列表，可用dataset name过滤，或用workFlowId过滤
     * @param projectId 项目id
     * @param filter 根据数据集名称过滤
     * @param workFlowId 根据工作流过滤
     * @return
     */
    ResultCode getDataSetList(Long projectId, String filter, Long workFlowId);

    /**
     * 添加数据集前，需要拉出数据源下的所有的表名
     * @param dataSourceId
     * @return
     */
    ResultCode getTableNamesFromDataSource(Long dataSourceId);

    /**
     * 获取数据集对应的源表的数据数量，和同步时间
     * @param dataSetId
     * @return
     */
    ResultCode getDataSetDataCount(Long dataSetId);

    /**
     * 获取数据集详情
     * @param dataSetId
     * @return
     */
    ResultCode getDataSetDetail(Long dataSetId);

    /**
     * 获取单个数据集详情
     * @param dataSetId
     * @return
     */
    ResultCode getSingleDetailById(Long dataSetId);

    /**
     * 删除数据集,批量,逻辑删除
     * @param dataSetIds 数据集id，多个以逗号隔开
     * @return
     */
    ResultCode deleteDataSet(String dataSetIds);

    /**
     * 测试数据集连接,从源数据表中拉取100条数据做预览
     * @param dataSourceId
     * @param tableName
     * @return
     */
    ResultCode testConnect(Long dataSourceId, String tableName, Long dataSetId);

    /**
     * 获取表结构信息,包括字段名,字段类型,类型长度等
     * @param dataSourceId
     * @param tableName
     * @return
     */
    ResultCode getSourceTableStructure(Long dataSourceId, String tableName);

    /**
     * 清除数据集数据,删除数据,保留表结构
     *
     * @param dataSetId
     * @return
     */
    ResultCode clearData(Long dataSetId);

    /**
     * 查询输入数据集列表
     *
     * @param projectId
     * @param workflowId
     * @param page
     * @param pageSize
     * @param filter
     * @return
     */
    ResultCode inputList(Long projectId, Long workflowId, int page, int pageSize, String filter);

    /**
     *查询输出数据集列表
     *
     * @param projectId
     * @param workflowId
     * @param page
     * @param pageSize
     * @param filter
     * @return
     */
    ResultCode outputList(Long projectId, Long workflowId, int page, int pageSize, String filter);

    /**
     * 查询数据集对应的数据源及数据表信息
     * 
     * @param dataSetId
     * @return
     *
     * {
     *     "datasourceId" : 123,
     *     "datasourceName": "connection1",
     *     "tableName": "admin"
     * }
     */
    ResultCode getDatasourceInfo(Long dataSetId);

    /**
     * 检查数据集名称是否重复，同一project下或同一datasource下的数据集名称都不能重复
     *
     * @param datasetName
     * @param datasourceId
     * @param projectId
     * @return
     */
    ResultCode isDatasetNameExist(String datasetName, Long datasourceId, Long projectId);
}
