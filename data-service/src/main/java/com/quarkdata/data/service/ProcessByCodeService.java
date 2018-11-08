package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.ResultUtil;

/**
 * 数据处理之代码处理
 * @author luohl 2018-6-21
 *
 */
public interface ProcessByCodeService {

    /**
     * 校验sql
     * @param sql
     * @param inputIds 输入数据集ids
     * @return
     */
    ResultCode validateSql(String sql, String inputIds);

    /**
     * 执行sql单节点测试
     * @param sql
     * @param inputIds 输入数据集ids
     * @param outputId 输出数据集id
     * @param processNodeId 处理节点id,用来获取追加或覆盖
     * @return
     */
    ResultCode executeSql(String sql, String inputIds, Long outputId, Long processNodeId, Long projectId);

    /**
     * 保存sql节点
     * @param workflowId 工作流id
     * @param sql sql语句
     * @param inputIds 输入数据集ids
     * @param outputId 输出数据集id
     * @param isAppend 是否覆盖 0-覆盖 1-追加
     * @param processNodeId 处理节点id
     * @return
     */
    ResultCode savaSql(Long workflowId, String sql, String inputIds, Long outputId, String isAppend, Long processNodeId, Long projectId);

    ResultCode getSql(Long nodeId);
}
