package com.quarkdata.data.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: data
 * @description: 获得日志页面的task的详细信息，有job的部分信息
 * @author: ning
 * @create: 2018-07-24 10:57
 **/

public class TaskLogDetail implements Serializable {
    private String CreateUser;   // 创建者
    private String TaskType;    // 任务类型  周期 手动   (预处理,数据同步，SQL)
    private String interval;    // 调度周期
    private String taskStatus;  // 任务状态
    private Date scheduleDatetime;//定时时间
    private Date startTime;//开始执行时间
    private Date endTime;//结束执行时间

    public String getCreateUser() {
        return CreateUser;
    }

    public String getTaskType() {
        return TaskType;
    }

    public String getInterval() {
        return interval;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public Date getScheduleDatetime() {
        return scheduleDatetime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public void setTaskType(String taskType) {
        TaskType = taskType;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setScheduleDatetime(Date scheduleDatetime) {
        this.scheduleDatetime = scheduleDatetime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
