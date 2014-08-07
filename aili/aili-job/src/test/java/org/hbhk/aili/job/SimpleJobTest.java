package org.hbhk.aili.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.job.server.QuartzService;
import org.junit.Test;
import org.quartz.Job;
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

			QuartzService quartzService = (QuartzService) context
					.getBean("quartzService");
			String jobName = "jobName";
			List<String> topicIds = new ArrayList<String>();
			topicIds.add("topicId1");
			topicIds.add("topicId2");
			String description = "description";
			String cronPattern = "0/10 * * * * ?";
			try {
				Class<?> jobclass = Class
						.forName("org.hbhk.aili.job.ParseModelJob");
				Job jobInstance = (Job) jobclass.newInstance();
//				quartzService.addParseModelJob(jobName, topicIds, description,
//						cronPattern, jobInstance);

			} catch (Exception e) {
				e.printStackTrace();
			}

			quartzService.getQuartzJobList(jobName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Test
	public void deleteJob() throws SchedulerException{
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:job/jobContext.xml");

		QuartzService quartzService = (QuartzService) context
				.getBean("quartzService");
		quartzService.deleteJob("jobName");
	}
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:job/jobContext.xml");

			QuartzService quartzService = (QuartzService) context
					.getBean("quartzService");
			System.out.println(quartzService);
			//Thread.sleep(20000);
			//System.out.println("delete");
			//quartzService.deleteJob("jobName");

//			Class<?> jobclass = Class
//					.forName("org.hbhk.aili.job.server.ParseModelJob");
//			System.out.println(jobclass);
//			Class<?>[] superclass = jobclass.getInterfaces();
//			for (int i = 0; i < superclass.length; i++) {
//				Class<?> class1 = superclass[i];
//				if (class1.equals(Job.class) && class1 == Job.class) {
//					System.out.println(class1);
//					break;
//				}
//
//			}
//			System.out.println("sssssssssss");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
