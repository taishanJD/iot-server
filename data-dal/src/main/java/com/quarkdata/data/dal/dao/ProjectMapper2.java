package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProjectMapper2 {

    int updateByParameter(@Param("projectId") Long projectId,
                          @Param("projectName") String projectName,
                          @Param("projectInfo") String projectInfo,
                          @Param("projectDesc") String projectDesc,
                          @Param("projectStatus") String projectStatus,
                          @Param("tenantId") Long tenantId,
                          @Param("updateUser") Long updateUser,
                          @Param("updateTime") Date updateTime);

    ProjectWithBLOBs selectProject(@Param("projectId") Long projectId,
                                   @Param("tenantId") Long tenantId);

    long countTask(@Param("projectId") Long projectId,
                   @Param("tenantId") Long tenantId);

    List<ProjectWithBLOBs> getList(@Param("tenantId") Long tenantId,
                                   @Param("filter") String filter,
                                   @Param("status") String[] status,
                                   @Param("tag") Long[] tag,
                                   @Param("user") Long[] user,
                                   @Param("sortType") String sortType,
                                   @Param("sortMethod") String sortMethod);

    List<ProjectTimeline> getTimelineList(@Param("projectId") Long projectId,
                                          @Param("tenantId") Long tenantId);

    String getProjectName(@Param("projectId") Long projectId,
                          @Param("tenantId") Long tenantId);

    List<String> getCommentCreateDays(@Param("projectId") Long projectId,
                               @Param("tenantId") Long tenantId);

    List<ProjectComment> getOneGroupComment(@Param("projectId") Long projectId,
                                  @Param("tenantId") Long tenantId,
                                  @Param("createDay") String createDay);

    List<String> getTimelineCreateDays(@Param("projectId") Long projectId,
                                       @Param("tenantId") Long tenantId,
                                       @Param("objectParentType") String objectParentType,
                                       @Param("objectId") Long objectId);

    List<ProjectTimeline> getOneGroupTimeline(@Param("projectId") Long projectId,
                                            @Param("tenantId") Long tenantId,
                                            @Param("createDay") String createDay,
                                            @Param("objectParentType") String objectParentType,
                                            @Param("objectId") Long objectId);

    List<Long> getCreateuserId(@Param("tenantId") Long tenantId);

	List<Project> getAllProjectNameList();

}