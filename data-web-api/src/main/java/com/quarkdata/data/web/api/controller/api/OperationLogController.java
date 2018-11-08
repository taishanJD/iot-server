package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.vo.TaskLogDetail;
import com.quarkdata.data.service.OperationLogService;
import com.quarkdata.data.service.impl.OperationLogImpl;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: data
 * @description: 运行日志
 * @author: ning
 * @create: 2018-07-24 15:23
 **/
@RequestMapping(RouteKey.PREFIX_API + "/operation_log")
@RestController
public class OperationLogController {
    @Autowired
    OperationLogService operationLogService;

    static Logger logger = Logger.getLogger(OperationLogImpl.class);
    /**
    * @Description:  获得操作的日志，返回的形式类似于时间轴
    * @Param:
    * @return:
    * @Author: ning
    * @Date: 2018/7/24
    */
    @RequestMapping(value = "/get_operation_log", method = RequestMethod.GET)
    public ResultCode getOperationLog(Long type,Long typeId){
        ResultCode resultCode;
        try{
            return operationLogService.getOperationLog(type,typeId);
        }catch (Exception e){
            logger.error("获取日志失败", e);
            e.printStackTrace();
            resultCode = ResultUtil.error(Messages.GET_TIMELINE_LIST_CODE, Messages.GET_TIMELINE_LIST_MSG);
        }
        return resultCode;
    }

    /** 
    * @Description: 日志所需要task的节点详细信息
    * @Param:    任务id  当前点击节点id
    * @return:
    * @Author: ning
    * @Date: 2018/7/25 
    */
    @RequestMapping(value = "/get_node_detail", method = RequestMethod.GET)
    public ResultCode<TaskLogDetail> getNodeDetail(Long taskId, Long nodeId){
        ResultCode<TaskLogDetail> nodeDetail = operationLogService.getNodeDetail(taskId, nodeId);
        return nodeDetail;
    }

    /**
    * @Description: 获得当前的单节点的详细信息
    * @Param:
    * @return:
    * @Author: ning
    * @Date: 2018/7/25
    */
    @RequestMapping(value = "/get_singnode_detail", method = RequestMethod.GET)
    public ResultCode<TaskLogDetail> getSingNodeLogDetail(Long singNodeId){
        ResultCode<TaskLogDetail> logDetail = operationLogService.getSingNodeLogDetail(singNodeId);
        return logDetail;
    }
}
