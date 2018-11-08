package com.quarkdata.data.dal.dao;

import org.apache.ibatis.annotations.Param;

public interface RunningLogMapper2 {
    int updateLogContent(@Param("typeId") Long dataSetId,@Param("logContent")String logContent);
    int deleteRunningLog(@Param("type") Long type,@Param("typeId") Long typeId);
}