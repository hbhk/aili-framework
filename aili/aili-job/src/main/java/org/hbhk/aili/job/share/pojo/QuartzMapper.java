package org.hbhk.aili.job.share.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class QuartzMapper implements ResultSetExtractor<List<QuartzInfo>> {

	@Override
	public List<QuartzInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {

		QuartzInfo q = null;
		List<QuartzInfo> qs = new ArrayList<QuartzInfo>();
		while (rs.next()) {
			q = new QuartzInfo();
			String jobName = rs.getString("JOB_NAME");
			q.setJobNname(jobName);
			String trggerName = rs.getString("TRIGGER_NAME");
			q.setTrggerName(trggerName);
			q.setDesc(rs.getString("DESCRIPTION"));
			q.setEndTime(rs.getLong("END_TIME"));
			q.setNextFireTime(rs.getLong("NEXT_FIRE_TIME"));
			q.setPrevFireTime(rs.getLong("PREV_FIRE_TIME"));
			q.setStartTime(rs.getLong("START_TIME"));
			q.setTriggerState(rs.getString("TRIGGER_STATE"));
			q.setTriggerType(rs.getString("TRIGGER_TYPE"));
			qs.add(q);

		}
		return qs;
	}

}
