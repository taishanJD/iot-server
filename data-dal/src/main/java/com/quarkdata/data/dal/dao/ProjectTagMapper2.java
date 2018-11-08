package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.ProjectTag;
import com.quarkdata.data.model.dataobj.ProjectTagExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectTagMapper2 {

    List<ProjectTag> selectTagByProjectId(@Param("projectId") Long projectId, @Param("tenantId") Long tenantId);

    List<Long> getTagIdByProjectId(@Param("projectId") Long projectId);

    List<Long> getTagIdByTenantId(@Param("tenantId") Long tenantId);

    List<Long> getTagIdByTagName(@Param("tenantId") Long tenantId, @Param("tagName") String tagName);

    List<ProjectTag> getUsedTag(@Param("tenantId") Long tenantId);

    List<ProjectTag> getAllTag(@Param("tenantId") Long tenantId, @Param("filter") String filter);
}