package org.hbhk.aili.job.share.pojo;


public class QuartzInfo {

	private String jobNname;
	private String trggerName;
	private long nextFireTime;
	private long prevFireTime;
	private String triggerState;
	private String triggerType;
	private long startTime;
	private long endTime;
	private String desc;
	public String getJobNname() {
		return jobNname;
	}
	public void setJobNname(String jobNname) {
		this.jobNname = jobNname;
	}
	public String getTrggerName() {
		return trggerName;
	}
	public void setTrggerName(String trggerName) {
		this.trggerName = trggerName;
	}
	public long getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public long getPrevFireTime() {
		return prevFireTime;
	}
	public void setPrevFireTime(long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}
	public String getTriggerState() {
		return triggerState;
	}
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	

}
