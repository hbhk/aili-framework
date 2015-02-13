package org.hbhk.aili.job.server;

import java.util.List;

import org.hbhk.aili.job.share.pojo.QuartzInfo;


public interface IQuartzDao {

	/**
	 * 查找所有的定时任务
	 * @return
	 */
	public List<QuartzInfo> selectAllQuartJob(String jobName) ;
}