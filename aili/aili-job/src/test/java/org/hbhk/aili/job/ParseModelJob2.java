package org.hbhk.aili.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ParseModelJob2 implements Job {

	private static final Logger logger = Logger.getLogger(ParseModelJob2.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("这里输进任务处理的内容3");
	}

}
