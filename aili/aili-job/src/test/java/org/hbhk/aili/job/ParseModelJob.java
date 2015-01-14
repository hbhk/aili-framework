package org.hbhk.aili.job;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ParseModelJob implements Job {

	private static final Logger logger = Logger.getLogger(ParseModelJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("这里输进任务处理的内容");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("aaaaaaaaaaaaaa");
	}

}
