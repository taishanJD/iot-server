package com.quarkdata.data.service;


import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.vo.TaskLogDetail;

/**
 * @program: data
 * @description: 任务和的单节点的操作日志
 * @author: ning
 * @create: 2018-07-16 16:58
 **/
public interface OperationLogService {
    /**
     * @Description: 添加新的操作日志
     * @Param:  type 0 task  1 单节点；user 操作人； content 0 重跑 1终止 2 取消； typeId task的id或者单节点日志的id
     * @return:  ResultCode
     * @Author: ning
     * @Date: 2018/7/16
     */
    void InsertOPerationLog (int type,Long user,int content,Long typeId);
    /**
    * @Description:  获得操作日志
    * @Param:      日志类型 单节点 task；  单节点的Id或者task的Id
    * @return:
    * @Author: ning
    * @Date: 2018/7/24
    */
    ResultCode getOperationLog(Long type,Long typeId);

    /**
    * @Description: 获得task中节点点击的详细信息
    * @Param:
    * @return:
    * @Author: ning
    * @Date: 2018/7/25
    */
    ResultCode<TaskLogDetail> getNodeDetail(Long taskId, Long nodeId);

    /**
    * @Description:  获得单节点日志的详情
    * @Param:
    * @return:
    * @Author: ning
    * @Date: 2018/7/25
    */
    ResultCode<TaskLogDetail> getSingNodeLogDetail(Long singNodeId);
}
