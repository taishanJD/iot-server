package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.SingleNodeTestExecuteLog;
import com.quarkdata.data.model.dataobj.SingleNodeTestExecuteLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SingleNodeTestExecuteLogMapper {
    long countByExample(SingleNodeTestExecuteLogExample example);

    int deleteByExample(SingleNodeTestExecuteLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SingleNodeTestExecuteLog record);

    int insertSelective(SingleNodeTestExecuteLog record);

    List<SingleNodeTestExecuteLog> selectByExample(SingleNodeTestExecuteLogExample example);

    SingleNodeTestExecuteLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SingleNodeTestExecuteLog record, @Param("example") SingleNodeTestExecuteLogExample example);

    int updateByExample(@Param("record") SingleNodeTestExecuteLog record, @Param("example") SingleNodeTestExecuteLogExample example);

    int updateByPrimaryKeySelective(SingleNodeTestExecuteLog record);

    int updateByPrimaryKey(SingleNodeTestExecuteLog record);

    SingleNodeTestExecuteLog selectForUpdate(Long id);
}