package org.hbhk.aili.test.server.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ParseModelJob implements Job {

	private static final Log logger = LogFactory.getLog(ParseModelJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("这里输进任务处理的内容");
	}

}
