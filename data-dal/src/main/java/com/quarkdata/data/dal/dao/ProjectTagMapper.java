package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.ProjectTag;
import com.quarkdata.data.model.dataobj.ProjectTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectTagMapper {
    long countByExample(ProjectTagExample example);

    int deleteByExample(ProjectTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProjectTag record);

    int insertSelective(ProjectTag record);

    List<ProjectTag> selectByExample(ProjectTagExample example);

    ProjectTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProjectTag record, @Param("example") ProjectTagExample example);

    int updateByExample(@Param("record") ProjectTag record, @Param("example") ProjectTagExample example);

    int updateByPrimaryKeySelective(ProjectTag record);

    int updateByPrimaryKey(ProjectTag record);

    ProjectTag selectForUpdate(Long id);
}