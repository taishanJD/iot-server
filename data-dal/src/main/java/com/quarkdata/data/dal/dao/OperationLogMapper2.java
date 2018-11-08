package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.vo.TaskLogDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OperationLogMapper2 {
    List<String> getLogCreatDay (@Param("type") Long Type,@Param("typeId") Long typeId);
    List<TaskLogDetail> getOneGruopOperatLog(@Param("type") Long type,@Param("typeId") Long typeId,@Param("createDay") String createDay);
    int deleteOperaLog(@Param("type")Long type,@Param("typeId")Long typeId);
}