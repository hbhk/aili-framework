package org.hbhk.aili.lock.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.lock.server.dao.IBusinessLockDao;
import org.hbhk.aili.lock.share.pojo.MutexElement;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务锁数据访问类
 */
public class BusinessLockDao extends SqlSessionDaoSupport implements
		IBusinessLockDao {

	private final String NAMESPACE = "hbhk.aili.";

	@Override
	public Long setnx(String key, String value, int ttl) {
		String existValue = this.get(key);
		if (StringUtils.isNotBlank(existValue)) {
			return 0l;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", key);
			map.put("value", value);
			map.put("ttl", ttl);
			this.getSqlSession().insert(NAMESPACE + ".insert", map);
			return 1l;
		}
	}

	@Override
	public String get(String key) {
		String value = (String) this.getSqlSession().selectOne(
				NAMESPACE + ".selectByKey", key);
		return value;
	}

	@Override
	public void del(String key) {
		this.getSqlSession().delete(NAMESPACE + ".delete", key);
	}

	@Override
	public void delBatch(List<String> keys) {
		this.getSqlSession().delete(NAMESPACE + ".deleteBatchByKeys", keys);
	}

	@Override
	public List<Long> setnxBatch(Map<String, MutexElement> map) {
		List<Long> reslutList = new ArrayList<Long>();
		for (Entry<String, MutexElement> entry : map.entrySet()) {
			MutexElement m = entry.getValue();
			Long result = this.setnx(entry.getKey(), m.getBusinessDesc(),
					m.getTtl());
			reslutList.add(result);
		}
		return reslutList;
	}

	@Override
	@Transactional
	public void delTimeoutData() {
		this.getSqlSession().delete(NAMESPACE + ".deleteTimeoutData");
	}

}
