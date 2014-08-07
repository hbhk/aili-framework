package org.hbhk.aili.lock.server.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.lock.server.dao.IBusinessLockDao;
import org.hbhk.aili.lock.server.service.IBusinessLockService;
import org.hbhk.aili.lock.share.pojo.MutexElement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 业务互斥锁服务 DB实现版本
 */
public class DbBusinessLockService implements IBusinessLockService {

	private static final Log LOGGER = LogFactory.getLog(DbBusinessLockService.class);

	/**
	 * 业务锁数据访问
	 */
	private IBusinessLockDao businessLockDao;

	/**
	 * 锁定某个业务对象 timeout = 0 时，非阻塞调用，如果对象已锁定立刻返回失败 timeout > 0
	 * 时，阻塞调用，如果对象已锁定，会等待直到1）获取锁并返回成功 2）超时并返回失败
	 * @param mutex
	 *            互斥对象
	 * @param timeout
	 *            超时时间，单位：秒
	 * @return true：锁定成功，false：锁定失败
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean lock(MutexElement mutex, int timeout) {

		// 输入参数校验
		if (mutex == null || mutex.getType() == null
				|| StringUtils.isEmpty(mutex.getBusinessNo())) {
			throw new RuntimeException("互斥参数为空");
		}
		String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
		String value = mutex.getBusinessDesc();

		try {
			long nano = System.nanoTime();
			do {
				LOGGER.debug("try lock key: " + key);
				// 设置业务锁记录，如果不存在记录，插入记录，并设置锁的过期时间，如果存在记录不做操作
				Long i = businessLockDao.setnx(key, value, mutex.getTtl());
				if (i == 1) {	// setnx成功，获得锁
					LOGGER.debug("get lock, key: " + key + " , expire in " + mutex.getTtl() + " seconds.");
					return true;
				} else {	// 存在锁
					if (LOGGER.isDebugEnabled()) {
						String desc = businessLockDao.get(key);
						LOGGER.debug("key: " + key + " locked by another business：" + desc);
					}
				}
				
				if (timeout == 0) {	// 非阻塞调用，则退出
					break;
				}
				
				Thread.sleep(1000);	// 每秒访问一次
				
			} while ((System.nanoTime() - nano) < timeout*1000l*1000l*1000l);

			// 得不到锁，返回失败
			return false;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		// 锁不再作为业务的的强制必要条件
		// 发生异常，则不再处理锁
		return true;
	}

	/**
	 * 解除某个业务对象锁定
	 * @param mutex
	 *            互斥对象
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void unlock(MutexElement mutex) {
		
		// 输入参数校验
		if (mutex == null || mutex.getType() == null
				|| StringUtils.isEmpty(mutex.getBusinessNo())) {
			throw new RuntimeException("互斥参数为空");
		}

		String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
		
		try {
			businessLockDao.del(key);
			LOGGER.debug("release lock, key :" + key);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	
	/** 
	 * 批量锁定多个业务对象
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean lock(List<MutexElement> mutexes, int timeout) {
		
		// 输入参数校验
		if (CollectionUtils.isEmpty(mutexes)) {
			throw new RuntimeException("互斥参数为空");
		}
		
		// 定义并构造所有锁MAP
		LinkedHashMap<String, MutexElement> map = new LinkedHashMap<String, MutexElement>();
		
		for (MutexElement mutex : mutexes) {

			if (mutex == null || mutex.getType() == null
					|| StringUtils.isEmpty(mutex.getBusinessNo())) {
				throw new RuntimeException("互斥参数为空");
			}

			String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
			map.put(key, mutex);
		}

		try {

			List<String> locking = new ArrayList<String>();	// 要锁定的KEY集合
			List<String> locked = new ArrayList<String>();	// 已锁定的KEY集合
			locking.addAll(map.keySet());
			long nano = System.nanoTime();

			do {
				LOGGER.debug("try lock keys: " + locking);
				
				// 批量提交
				List<Long> results = businessLockDao.setnxBatch(map);
				
				for (int i = 0; i < results.size(); ++i) {
					Long result = (Long) results.get(i);
					String key = locking.get(i);
					
					if (result == 1) {	// setnx成功，获得锁
						locked.add(key);
					} else {	// 存在锁
					}
				}
				
				locking.removeAll(locked);	// 已锁定资源去除
				
				if (CollectionUtils.isEmpty(locking)) {	// 得到所有锁资源
					return true;
				} else {	// 部分资源未能锁住
					LOGGER.debug("keys: " + locking + " locked by another business：");
				}
				
				if (timeout == 0) {	// 非阻塞调用，则退出
					break;
				}
				
				Thread.sleep(1000);	// 每秒访问一次
				
			} while ((System.nanoTime() - nano) < timeout*1000l*1000l*1000l);

			// 得不到锁，释放锁定的部分对象，并返回失败
			if (CollectionUtils.isNotEmpty(locked)) {
				businessLockDao.delBatch(locked);
			}
			
			return false;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		// 锁不再作为业务的的强制必要条件
		// 发生异常，则不再处理锁
		return true;
	}

	/** 
	 * 批量解除多个业务对象锁
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void unlock(List<MutexElement> mutexes) {
		
		// 输入参数校验
		if (CollectionUtils.isEmpty(mutexes)) {
			throw new RuntimeException("互斥参数为空");
		}
		
		List<String> keys = new ArrayList<String>();
		for (MutexElement mutex : mutexes) {

			if (mutex == null || mutex.getType() == null
					|| StringUtils.isEmpty(mutex.getBusinessNo())) {
				throw new RuntimeException("互斥参数为空");
			}

			String key = mutex.getType().getPrefix() + mutex.getBusinessNo();
			keys.add(key);
		}
		
		try {
			businessLockDao.delBatch(keys);
			LOGGER.debug("release lock, keys :" + keys);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	public void setBusinessLockDao(IBusinessLockDao businessLockDao) {
		this.businessLockDao = businessLockDao;
	}

}
