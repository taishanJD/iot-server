package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.ProjectComment;
import com.quarkdata.data.model.dataobj.ProjectCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectCommentMapper {
    long countByExample(ProjectCommentExample example);

    int deleteByExample(ProjectCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProjectComment record);

    int insertSelective(ProjectComment record);

    List<ProjectComment> selectByExampleWithBLOBs(ProjectCommentExample example);

    List<ProjectComment> selectByExample(ProjectCommentExample example);

    ProjectComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProjectComment record, @Param("example") ProjectCommentExample example);

    int updateByExampleWithBLOBs(@Param("record") ProjectComment record, @Param("example") ProjectCommentExample example);

    int updateByExample(@Param("record") ProjectComment record, @Param("example") ProjectCommentExample example);

    int updateByPrimaryKeySelective(ProjectComment record);

    int updateByPrimaryKeyWithBLOBs(ProjectComment record);

    int updateByPrimaryKey(ProjectComment record);

    ProjectComment selectForUpdate(Long id);
}