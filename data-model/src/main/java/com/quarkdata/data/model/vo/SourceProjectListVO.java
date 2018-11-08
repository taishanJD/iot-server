package com.quarkdata.data.model.vo;

import java.io.Serializable;

/**
 * 资源管理 项目列表
 * @author WangHao
 * 2018年5月17日
 */
public class SourceProjectListVO implements Serializable{
	
	private static final long serialVersionUID = -2870390634698246377L;
	
	// 项目ID
	private Long projectId;
	// 项目名称
	private String projectName;
	// 项目图片
	private byte[] projectPicture;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public byte[] getProjectPicture() {
		return projectPicture;
	}
	public void setProjectPicture(byte[] projectPicture) {
		this.projectPicture = projectPicture;
	}
	
}
