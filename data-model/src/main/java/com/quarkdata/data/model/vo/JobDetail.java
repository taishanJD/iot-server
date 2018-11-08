package com.quarkdata.data.model.vo;

import java.util.List;

import com.quarkdata.data.model.dataobj.SchedulerJob;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;

/**
 * 
 * @author typ
 * 	2018年5月7日
 */
public class JobDetail extends SchedulerJob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<SchedulerJobResponsible> users;
	
	private String isDependParent;// 是否依赖父节点执行状态（0：否、1：是）默认1
	
	private String noticeType; // 通知类型（0：邮件、1：短信、2：邮件和短信）
	
	private String noticeReasonType; // 通知原因类型（0：出错、1：完成、2：出错和完成）


	public List<SchedulerJobResponsible> getUsers() {
		return users;
	}


	public void setUsers(List<SchedulerJobResponsible> users) {
		this.users = users;
	}


	public String getIsDependParent() {
		return isDependParent;
	}


	public void setIsDependParent(String isDependParent) {
		this.isDependParent = isDependParent;
	}


	public String getNoticeType() {
		return noticeType;
	}


	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}


	public String getNoticeReasonType() {
		return noticeReasonType;
	}


	public void setNoticeReasonType(String noticeReasonType) {
		this.noticeReasonType = noticeReasonType;
	}
}
