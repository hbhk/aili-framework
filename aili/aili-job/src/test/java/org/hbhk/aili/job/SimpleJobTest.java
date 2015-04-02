package org.hbhk.aili.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.job.server.JobService;
import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleJobTest {

	private Log log = LogFactory.getLog(getClass());

	@SuppressWarnings("resource")
	@Test
	public void simpleJobTest() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:job/jobContext.xml");

			JobService quartzService = (JobService) context
					.getBean("jobService");
			for (int i = 0; i < 10; i++) {
				String jobName = "jobName"+i;
				List<String> topicIds = new ArrayList<String>();
				topicIds.add("topicId1");
				topicIds.add("topicId2");
				String description = "description";
				String cronPattern = "0/10 * * * * ?";
				try {
					quartzService.addJob(jobName, topicIds, description,
							cronPattern, ParseModelJob.class);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Test
	public void deleteJob() throws SchedulerException{
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:job/jobContext.xml");

		JobService quartzService = (JobService) context
				.getBean("jobService");
		for (int i = 3; i < 10; i++) {
			quartzService.deleteJob("jobName"+i);
		}
		
	}
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:job/jobContext.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
