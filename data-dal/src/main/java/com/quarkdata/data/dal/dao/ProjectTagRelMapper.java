package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.ProjectTagRelExample;
import com.quarkdata.data.model.dataobj.ProjectTagRelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectTagRelMapper {
    long countByExample(ProjectTagRelExample example);

    int deleteByExample(ProjectTagRelExample example);

    int deleteByPrimaryKey(ProjectTagRelKey key);

    int insert(ProjectTagRelKey record);

    int insertSelective(ProjectTagRelKey record);

    List<ProjectTagRelKey> selectByExample(ProjectTagRelExample example);

    int updateByExampleSelective(@Param("record") ProjectTagRelKey record, @Param("example") ProjectTagRelExample example);

    int updateByExample(@Param("record") ProjectTagRelKey record, @Param("example") ProjectTagRelExample example);
}