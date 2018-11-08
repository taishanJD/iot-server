package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.Project;
import com.quarkdata.data.model.dataobj.ProjectExample;
import com.quarkdata.data.model.dataobj.ProjectWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectMapper {
    long countByExample(ProjectExample example);

    int deleteByExample(ProjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProjectWithBLOBs record);

    int insertSelective(ProjectWithBLOBs record);

    List<ProjectWithBLOBs> selectByExampleWithBLOBs(ProjectExample example);

    List<Project> selectByExample(ProjectExample example);

    ProjectWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProjectWithBLOBs record, @Param("example") ProjectExample example);

    int updateByExampleWithBLOBs(@Param("record") ProjectWithBLOBs record, @Param("example") ProjectExample example);

    int updateByExample(@Param("record") Project record, @Param("example") ProjectExample example);

    int updateByPrimaryKeySelective(ProjectWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProjectWithBLOBs record);

    int updateByPrimaryKey(Project record);

    ProjectWithBLOBs selectForUpdate(Long id);
}