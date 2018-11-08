package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.common.ScheduleJob;

public interface ScheduleJobMapper {

	void saveJob(ScheduleJob scheduleJob);

	void deleteScheduler(ScheduleJob scheduleJob);
}
