package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.model.vo.TaskLogDetail;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;
import com.quarkdata.data.service.ProjectService;
import com.quarkdata.data.util.common.Logget.RootLoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.SchedulerTaskService;
import com.quarkdata.data.web.api.controller.RouteKey;
import com.quarkdata.data.model.vo.SchedulerTaskVO;

import java.util.List;

@RequestMapping(RouteKey.PREFIX_API + "/scheduler_task")
@RestController
public class SchedulerTaskController {
    static public Logger logger = Logger.getLogger(SchedulerTaskController.class);
    @Autowired
    SchedulerTaskService SchedulerTaskService;
    @Autowired
    UserController userController;
    @Autowired
    ProjectService projectService;

    /**
     * @param jobType         作业类型（0：周期性、1：手动）
     * @param taskStatus      调度任务状态（0：待执行、1：已取消、2：执行中、3：已停止、4：执行成功、5：执行失败）
     * @param responsibleType 0-全部责任人 1-我的
     * @param bizDate         yyyy-MM-dd
     */
    @RequestMapping("/list")
    public ResultCode<ListResult<SchedulerTaskVO>> list(
            Long jobId,
            Long projectId,
            String name,
            String jobType,
            String taskStatus,
            String responsibleType,
            String bizDate,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode<ListResult<SchedulerTaskVO>> resultCode = SchedulerTaskService
                .getTaskList(jobId, tenantId, projectId, userId, name, jobType, taskStatus,
                        responsibleType, bizDate, page, pageSize);
        return resultCode;
    }

    /**
     * @Description:
     * @Param: 任务ID
     * @return: 节点间关系
     * @Author: ning
     * @Date: 2018/7/17
     */
    @RequestMapping(value = "/get_node_rel_list/{taskId}", method = RequestMethod.GET)
    public ResultCode<List<WorkflowNodeRelVO>> getNodeRelList(@PathVariable("taskId") Long taskId) {
        ResultCode<List<WorkflowNodeRelVO>> nodeRelList = SchedulerTaskService.getNodeRelList(taskId);
        return nodeRelList;
    }

    /**
     * @Description:
     * @Param: 任务id，工作id
     * @return: 日志所需要的task的详情(部分需要使用到 ， 当前的任务对应的job的属性)
     * @Author: ning
     * @Date: 2018/7/24
     */
    @RequestMapping(value = "get_task_detail", method = RequestMethod.GET)
    public ResultCode<TaskLogDetail> getTaskDetail(Long taskId, Long jobId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        ResultCode<TaskLogDetail> taskDetail = SchedulerTaskService.getTaskDetail(taskId, jobId, tenantId);
        return taskDetail;
    }
    /**
    * @Description:  task的删除
    * @Param:  taskId
    * @return:
    * @Author: ning
    * @Date: 2018/8/13
    */
    @RequestMapping(value ="delete_task", method = RequestMethod.GET)
    public ResultCode<String> deleteTask(String taskIds) {
        ResultCode<String> resultCode = SchedulerTaskService.deleteTask(taskIds);
        return resultCode;
    }

}
