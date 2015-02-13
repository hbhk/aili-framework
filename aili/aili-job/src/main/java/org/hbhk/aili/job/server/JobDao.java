package org.hbhk.aili.job.server;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.hbhk.aili.job.share.pojo.JobInfo;
import org.hbhk.aili.job.share.pojo.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JobDao implements IJobDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * 查找所有的定时任务
	 * @return
	 */
	public List<JobInfo> selectAllQuartJob(String jobName) {
		String sql = "SELECT QRTZ_JOB_DETAILS.JOB_NAME JOB_NAME ,QRTZ_TRIGGERS.TRIGGER_NAME TRIGGER_NAME"
				+ ",NEXT_FIRE_TIME,PREV_FIRE_TIME,TRIGGER_STATE,TRIGGER_TYPE,START_TIME,END_TIME"
				+ ",QRTZ_JOB_DETAILS.DESCRIPTION  DESCRIPTION from QRTZ_TRIGGERS inner join QRTZ_JOB_DETAILS "
				+ " on QRTZ_TRIGGERS.JOB_NAME = QRTZ_JOB_DETAILS.JOB_NAME";
		if(StringUtils.isNotEmpty(jobName)){
			sql=sql+" WHERE QRTZ_JOB_DETAILS.JOB_NAME = ?"+" order by start_time";
		}else{
			sql =sql+" order by start_time";
		}
		return jdbcTemplate.query(sql,new Object[]{jobName}, new JobMapper());
	}
}