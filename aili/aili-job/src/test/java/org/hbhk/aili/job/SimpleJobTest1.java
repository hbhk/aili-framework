package org.hbhk.aili.job;

import org.hbhk.aili.job.server.JobService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleJobTest1 {

	
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:job/jobContext1.xml");

			JobService quartzService = (JobService) context
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
