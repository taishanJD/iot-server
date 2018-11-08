package com.quarkdata.data.model.vo;

import java.util.List;

import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;
import com.quarkdata.data.model.dataobj.SingleNodeTestExecuteLog;

public class SingleNodeTestExecuteLogVO extends SingleNodeTestExecuteLog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private  List<SchedulerJobResponsible> users;    // private List<SchedulerJobResponsible> users;

	public  List<SchedulerJobResponsible> getUsers() {
		return users;
	}

	public void setUsers( List<SchedulerJobResponsible> users) {
		this.users = users;
	}
}
