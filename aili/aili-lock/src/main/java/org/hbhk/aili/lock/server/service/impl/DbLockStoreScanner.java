package org.hbhk.aili.lock.server.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.lock.server.dao.IBusinessLockDao;
import org.springframework.beans.factory.InitializingBean;


/**
 * 清除规则：每5分钟删除t_lock_record中create_time时间为当前时间5分钟以前，并且已经解锁的业务锁信息
 */
public final class DbLockStoreScanner extends Thread implements InitializingBean{
	
	private Log log = LogFactory.getLog(DbLockStoreScanner.class);
	
	/**
	 * 扫描间隔（毫秒）
	 */
	private int scannerInterval = 60*5;

	private IBusinessLockDao businessLockDao;

	public void setScannerInterval(int scannerInterval) {
		this.scannerInterval = 1000*scannerInterval;
	}
	
	public void setBusinessLockDao(IBusinessLockDao businessLockDao) {
		this.businessLockDao = businessLockDao;
	}


	/**
	 * 是否启用enable: true：启用;false：不启用
	 */
	private boolean enable = false;
	
	/**
	 * 线程停止标识:true,停止;false:不停止
	 */
	private boolean stopFlag = false;

	@Override
	public void run() {
		while(!stopFlag){
			try {
				businessLockDao.delTimeoutData();
				TimeUnit.SECONDS.sleep(scannerInterval);
			} catch (InterruptedException e) {
				log.error("",e);
			}
			
		}
	}
	public void setStopFlag(boolean flag){
		stopFlag=flag;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(enable){
			this.start();			
		}
	}
}
