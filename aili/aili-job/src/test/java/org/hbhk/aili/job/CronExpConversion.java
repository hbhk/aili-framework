package org.hbhk.aili.job;

public class CronExpConversion {

	/**
	 * 页面设置转为UNIX cron expressions 转换算法
	 * 
	 * @param everyWhat
	 * @param commonNeeds
	 *            包括 second minute hour
	 * @param monthlyNeeds
	 *            包括 第几个星期 星期几
	 * @param weeklyNeeds
	 *            包括 星期几
	 * @param userDefinedNeeds
	 *            包括具体时间点
	 * @return cron expression
	 */
	public static String convertDateToCronExp(String everyWhat,
			String[] commonNeeds, String[] monthlyNeeds, String weeklyNeeds,
			String userDefinedNeeds) {
		String cronEx = "";
		String commons = commonNeeds[0] + "   " + commonNeeds[1] + "   "
				+ commonNeeds[2] + "   ";
		String dayOfWeek = "";
		if ("monthly".equals(everyWhat)) {
			// eg.: 6#3 (day 6 = Friday and "#3" = the 3rd one in the
			// month)
			dayOfWeek = monthlyNeeds[1]
					+ CronExRelated.specialCharacters
							.get(CronExRelated._THENTH) + monthlyNeeds[0];
			cronEx = (commons
					+ CronExRelated.specialCharacters.get(CronExRelated._ANY)
					+ "   "
					+ CronExRelated.specialCharacters.get(CronExRelated._EVERY)
					+ "   " + dayOfWeek + "   ").trim();
		} else if ("weekly".equals(everyWhat)) {
			dayOfWeek = weeklyNeeds; // 1
			cronEx = (commons
					+ CronExRelated.specialCharacters.get(CronExRelated._ANY)
					+ "   "
					+ CronExRelated.specialCharacters.get(CronExRelated._EVERY)
					+ "   " + dayOfWeek + "   ").trim();
		} else if ("userDefined".equals(everyWhat)) {
			String dayOfMonth = userDefinedNeeds.split(" - ")[2];
			if (dayOfMonth.startsWith(" 0 ")) {
				dayOfMonth = dayOfMonth.replaceFirst(" 0 ", "");
			}
			String month = userDefinedNeeds.split(" - ")[1];
			if (month.startsWith(" 0 ")) {
				month = month.replaceFirst(" 0 ", "");
			}
			String year = userDefinedNeeds.split(" - ")[0];
			// FIXME 暂时不加年份 Quartz报错
			/*
			 * cronEx = (commons + dayOfMonth + " " + month + " " +
			 * CronExRelated.specialCharacters.get(CronExRelated._ANY) + " " +
			 * year).trim();
			 */
			cronEx = (commons + dayOfMonth + "   " + month + "   "
					+ CronExRelated.specialCharacters.get(CronExRelated._ANY) + "   ")
					.trim();
		}
		return cronEx;
	}
	
	public static void main(String[] args) {
		String ss=convertDateToCronExp("weekly", new String[]{"11","22","22"}, new String[]{"1","2"}, "1", "");
	System.out.println(ss);
	
	}
	
}
