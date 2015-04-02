package org.hbhk.aili.job.server;

import java.util.List;

import org.quartz.Job;
import org.quartz.SchedulerException;

public interface IJobService  {

	/**
	 * 增加定时任务
	 */
	public void addJob(String jobName,List<String> topicIds,
			String description, String cronPattern, Class<? extends Job> jobCls)
			throws Exception;
	
	/**
	 * 增加定时任务
	 */
	public void addJob(String jobName, String group,List<String> topicIds,
			String description, String cronPattern, Class<? extends Job> jobCls)
			throws Exception ;
	/**
	 * 删除定时任务
	 */
	public void deleteJob(String jobName) throws SchedulerException ;
	
	/**
	 * 删除定时任务
	 */
	public void deleteJob(String jobName,String group) throws SchedulerException ;

}
