package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.ProjectTimeline;
import com.quarkdata.data.model.dataobj.ProjectTimelineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectTimelineMapper {
    long countByExample(ProjectTimelineExample example);

    int deleteByExample(ProjectTimelineExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProjectTimeline record);

    int insertSelective(ProjectTimeline record);

    List<ProjectTimeline> selectByExample(ProjectTimelineExample example);

    ProjectTimeline selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProjectTimeline record, @Param("example") ProjectTimelineExample example);

    int updateByExample(@Param("record") ProjectTimeline record, @Param("example") ProjectTimelineExample example);

    int updateByPrimaryKeySelective(ProjectTimeline record);

    int updateByPrimaryKey(ProjectTimeline record);

    ProjectTimeline selectForUpdate(Long id);
}