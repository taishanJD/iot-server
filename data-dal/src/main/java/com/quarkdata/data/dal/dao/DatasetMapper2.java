package com.quarkdata.data.dal.dao;

import java.util.HashMap;
import java.util.List;

import com.quarkdata.data.model.vo.DataSetDetailVO;
import com.quarkdata.data.model.vo.DataSetListVO;
import org.apache.ibatis.annotations.Param;

import com.quarkdata.data.model.vo.DatasetVO;

public interface DatasetMapper2 {
    /**
     * @param search 查询数据源表名称
     * @return
     */
    List<DatasetVO> datasetList(@Param("search") String search);


    /**
     * 查询数据集列表
     *
     * @param projectId
     * @param filter     数据集名称过滤
     * @param workFlowId 工作流id过滤
     * @return 数据集的id, name, update_time, data_type
     */
    List<DataSetListVO> getDataSetList(@Param("projectId") Long projectId, @Param("filter") String filter, @Param("workFlowId") Long workFlowId);


    /**
     * 查询数据集详情
     *
     * @param dataSetId
     * @return 数据集的id, name, update_time, create_name, data_type, description, 以及所在的workflow的名称
     */
    DataSetDetailVO getDataSetDetail(@Param("dataSetId") Long dataSetId);

    List<DataSetListVO> getDatasetInfo(@Param("datasetIds") List<Long> datasetIds,
                                           @Param("filter") String filter,
                                           @Param("start") int start,
                                           @Param("size") int size);

    List<DataSetListVO> getDatasetInfo1(@Param("datasetIds") List<Long> datasetIds,
                                       @Param("filter") String filter);

    /**
     * 工作流画布中添加处理节点时，输出数据集使用已有的数据集，本方法获取
     *
     * @param projectId
     * @param projectKey
     * @param workFlowId
     * @param filter
     * @param start
     * @param size
     * @return
     */
    List<DataSetListVO> getOutputDatasetList(@Param("projectId")Long projectId,@Param("projectKey")String projectKey,@Param("workFlowId") Long workFlowId,@Param("filter") String filter,@Param("start") int start,
                                             @Param("size") int size);

    /**
     * 工作流画布中添加处理节点时，输出数据集使用已有的数据集，本方法获取
     *
     * @param projectId
     * @param projectKey
     * @param workFlowId
     * @param filter
     * @return
     */
    List<DataSetListVO> getOutputDatasetList1(@Param("projectId")Long projectId,@Param("projectKey")String projectKey,@Param("workFlowId") Long workFlowId,@Param("filter") String filter);
}