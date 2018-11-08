package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.vo.SchedulerTaskVO;
import com.quarkdata.data.model.vo.TaskLogDetail;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;

import java.util.List;

public interface SchedulerTaskService {

    ResultCode<ListResult<SchedulerTaskVO>> getTaskList(Long jobId,
                Long tenantId, Long projectId, Long userId, String name, String jobType, String taskStatus,
                String responsibleType, String bizDate, int page, int pageSize);
    /**
    * @Description:  获得节点间的关系
    * @Param:   任务id
    * @return:
    * @Author: ning
    * @Date: 2018/7/25
    */
    ResultCode<List<WorkflowNodeRelVO>> getNodeRelList(Long taskId);
    /**
    * @Description:  获得日志中task的详情
    * @Param:
    * @return:
    * @Author: ning
    * @Date: 2018/7/25
    */
    ResultCode<TaskLogDetail> getTaskDetail(Long taskId, Long jobId, Long tenantId);
    /**
    * @Description:  根据taskid删除task
    * @Param:  taskids
    * @return:
    * @Author: ning
    * @Date: 2018/8/13
    */
    ResultCode<String> deleteTask (String taskIds);
    /** 
    * @Description: 判断job下的task是否是可以删除的状态下的
    * @Param:  
    * @return:  
    * @Author: ning
    * @Date: 2018/8/14 
    */ 
    ResultCode<Long> getcantDelTasknumber (Long jobId,String statusOne,String statusTwo);
}
