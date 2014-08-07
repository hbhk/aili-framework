package org.hbhk.aili.hibernate.server.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hbhk.aili.hibernate.server.dao.IAiliDaoCallback;
import org.hbhk.aili.hibernate.server.dao.IAiliDao;
import org.hbhk.aili.hibernate.share.model.Page;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.atomikos.beans.PropertyUtils;

/**
 * Hibernate的范型基类.
 * <p>
 * 可以在service类中直接创建使用.也可以继承出DAO子类
 * </p>
 * 修改自Springside SimpleHibernateTemplate
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 * 
 */
public class AiliDaoSurpport<T, PK extends Serializable> implements
		IAiliDao<T, PK> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SessionFactory sessionFactory;
	
	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	@SuppressWarnings("unchecked")
	public AiliDaoSurpport() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	public T execute(IAiliDaoCallback<T> action) {
		Assert.notNull(action, "Callback object 对象不能为 Null ");
		Session session = getHibernateTemplate();
		Transaction tr = session.beginTransaction();
		T result = action.doInHibernate(session);
		tr.commit();
		session.close();
		return result;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getHibernateTemplate() {
		return getSessionFactory().openSession();
	}

	// -------------------- 基本检索、增加、修改、删除操作 --------------------

	// 根据主键获取实体。如果没有相应的实体，返回 null。
	public T get(final PK id) {
		return execute(new IAiliDaoCallback<T>() {
			@Override
			public T doInHibernate(Session session) {
				return (T) session.get(entityClass, id);
			}
		});
	}

	// 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
	public T getWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().get(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	// 根据主键获取实体。如果没有相应的实体，抛出异常。
	public T load(PK id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	// 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
	public T loadWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().load(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	// 获取全部实体。
	public List<T> loadAll() {
		return getHibernateTemplate().createCriteria(entityClass).list();
	}
	// loadAllWithLock() ?

	// 更新实体
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	// 更新实体并加锁
	public void updateWithLock(T entity, LockMode lock) {
		// getHibernateTemplate().update(entity, lock);
		// this.flush(); // 立即刷新，否则锁不会生效。
	}

	// 存储实体到数据库
	public void save(final T entity) {
		execute(new IAiliDaoCallback<T>() {
			@Override
			public T doInHibernate(Session session) {
				session.save(entity);
				return null;
			}
		});
	}

	// saveWithLock()？

	// 增加或更新实体
	public void saveOrUpdate(final T entity) {
		execute(new IAiliDaoCallback<T>() {
			@Override
			public T doInHibernate(Session session) {
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}

	// 增加或更新集合中的全部实体
	// public void saveOrUpdateAll(Collection<T> entities) {
	// getHibernateTemplate().saveOrUpdateAll(entities);
	// }

	// 删除指定的实体
	public void delete(final T entity) {
		execute(new IAiliDaoCallback<T>() {
			@Override
			public T doInHibernate(Session session) {
				session.delete(entity);
				return null;
			}
		});
	}

	// 加锁并删除指定的实体
	// public void deleteWithLock(T entity, LockMode lock) {
	// getHibernateTemplate().delete(entity, lock);
	// this.flush(); // 立即刷新，否则锁不会生效。
	// }

	// 根据主键删除指定实体
	public void deleteByKey(PK id) {
		this.delete(this.load(id));
	}

	@SuppressWarnings("unchecked")
	public Page<T> queryForList(final String hql, final Object[] params,
			final Page<T> page) {
		generatePageTotalCount(hql, params, page);
		List<T> lists = (List<T>) execute(new IAiliDaoCallback<T>() {
			@Override
			public T doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				setQueryParams(query, params);
				query.setFirstResult(page.getFirstIndex());
				query.setMaxResults(page.getPageSize());
				return (T) query.list();
			}
		});
		page.setData(lists);
		return page;
	}

	private void setQueryParams(Query query, Object[] params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}

	/**
	 * 该方法会改变参数page的totalCount字段
	 * 
	 * @param originHql
	 *            原始hql语句
	 * @param params
	 *            原始参数
	 * @param page
	 *            页面对象
	 */
	private void generatePageTotalCount(String originHql,
			final Object[] params, Page<T> page) {
		final String generatedCountHql = "select count(*) " + originHql;
		Integer totalCount = (Integer) execute(new IAiliDaoCallback<T>() {
			@Override
			public T doInHibernate(Session session) {
				Query countQuery = session.createQuery(generatedCountHql);
				setQueryParams(countQuery, params);
				Integer totalCount = ((Long) countQuery.uniqueResult())
						.intValue();
				return (T) totalCount;
			}
		});
		page.setTotalCount(totalCount);
	}

	// 根据主键加锁并删除指定的实体
	// public void deleteByKeyWithLock(PK id, LockMode lock) {
	// this.deleteWithLock(this.load(id), lock);
	// }

	// 删除集合中的全部实体
	// public void deleteAll(Collection<T> entities) {
	// getHibernateTemplate().deleteAll(entities);
	// }

	// -------------------- HSQL ----------------------------------------------

	// 使用HSQL语句直接增加、更新、删除实体
	// public int bulkUpdate(String queryString) {
	// return getHibernateTemplate().bulkUpdate(queryString);
	// }
	//
	// // 使用带参数的HSQL语句增加、更新、删除实体
	// public int bulkUpdate(String queryString, Object[] values) {
	// return getHibernateTemplate().bulkUpdate(queryString, values);
	// }

	// 使用HSQL语句检索数据
	// public List find(String queryString) {
	// return getHibernateTemplate().find(queryString);
	// }
	//
	// // 使用带参数的HSQL语句检索数据
	// public List find(String queryString, Object[] values) {
	// return getHibernateTemplate().find(queryString, values);
	// }
	//
	// // 使用带命名的参数的HSQL语句检索数据
	// public List findByNamedParam(String queryString, String[] paramNames,
	// Object[] values) {
	// return getHibernateTemplate().findByNamedParam(queryString, paramNames,
	// values);
	// }
	//
	// // 使用命名的HSQL语句检索数据
	// public List findByNamedQuery(String queryName) {
	// return getHibernateTemplate().findByNamedQuery(queryName);
	// }
	//
	// // 使用带参数的命名HSQL语句检索数据
	// public List findByNamedQuery(String queryName, Object[] values) {
	// return getHibernateTemplate().findByNamedQuery(queryName, values);
	// }
	//
	// // 使用带命名参数的命名HSQL语句检索数据
	// public List findByNamedQueryAndNamedParam(String queryName,
	// String[] paramNames, Object[] values) {
	// return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
	// paramNames, values);
	// }
	//
	// // 使用HSQL语句检索数据，返回 Iterator
	// public Iterator iterate(String queryString) {
	// return getHibernateTemplate().iterate(queryString);
	// }
	//
	// // 使用带参数HSQL语句检索数据，返回 Iterator
	// public Iterator iterate(String queryString, Object[] values) {
	// return getHibernateTemplate().iterate(queryString, values);
	// }
	//
	// // 关闭检索返回的 Iterator
	// public void closeIterator(Iterator it) {
	// getHibernateTemplate().closeIterator(it);
	// }
	//
	// // -------------------------------- Criteria
	// ------------------------------
	//
	// // 创建与会话无关的检索标准
	// public DetachedCriteria createDetachedCriteria() {
	// return DetachedCriteria.forClass(this.entityClass);
	// }
	//
	// // 创建与会话绑定的检索标准
	// public Criteria createCriteria() {
	// return this.createDetachedCriteria().getExecutableCriteria(
	// this.getSession());
	// }
	//
	// // 检索满足标准的数据
	// public List findByCriteria(DetachedCriteria criteria) {
	// return getHibernateTemplate().findByCriteria(criteria);
	// }
	//
	// // 检索满足标准的数据，返回指定范围的记录
	// public List findByCriteria(DetachedCriteria criteria, int firstResult,
	// int maxResults) {
	// return getHibernateTemplate().findByCriteria(criteria, firstResult,
	// maxResults);
	// }

	// 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
	public List<T> findEqualByEntity(T entity, String[] propertyNames) {
		Criteria criteria = this.createCriteria();
		Example exam = Example.create(entity);
		exam.excludeZeroes();
		String[] defPropertys = getSessionFactory().getClassMetadata(
				entityClass).getPropertyNames();
		for (String defProperty : defPropertys) {
			int ii = 0;
			for (ii = 0; ii < propertyNames.length; ++ii) {
				if (defProperty.equals(propertyNames[ii])) {
					criteria.addOrder(Order.asc(defProperty));
					break;
				}
			}
			if (ii == propertyNames.length) {
				exam.excludeProperty(defProperty);
			}
		}
		criteria.add(exam);
		return (List<T>) criteria.list();
	}

	// 使用指定的实体及属性检索（满足属性 like 串实体值）数据
	public List<T> findLikeByEntity(T entity, String[] propertyNames) {
		Criteria criteria = this.createCriteria();
		for (String property : propertyNames) {
			try {
				Object value = PropertyUtils.getProperty(entity, property);
				if (value instanceof String) {
					criteria.add(Restrictions.like(property, (String) value,
							MatchMode.ANYWHERE));
					criteria.addOrder(Order.asc(property));
				} else {
					criteria.add(Restrictions.eq(property, value));
					criteria.addOrder(Order.asc(property));
				}
			} catch (Exception ex) {
				// 忽略无效的检索参考数据。
			}
		}
		return (List<T>) criteria.list();
	}

	// 使用指定的检索标准获取满足标准的记录数
	// public Integer getRowCount(DetachedCriteria criteria) {
	// criteria.setProjection(Projections.rowCount());
	// List list = this.findByCriteria(criteria, 0, 1);
	// return (Integer) list.get(0);
	// }

	// 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
	// public Object getStatValue(DetachedCriteria criteria, String
	// propertyName,
	// String StatName) {
	// if (StatName.toLowerCase().equals("max"))
	// criteria.setProjection(Projections.max(propertyName));
	// else if (StatName.toLowerCase().equals("min"))
	// criteria.setProjection(Projections.min(propertyName));
	// else if (StatName.toLowerCase().equals("avg"))
	// criteria.setProjection(Projections.avg(propertyName));
	// else if (StatName.toLowerCase().equals("sum"))
	// criteria.setProjection(Projections.sum(propertyName));
	// else
	// return null;
	// List list = this.findByCriteria(criteria, 0, 1);
	// return list.get(0);
	// }

	// -------------------------------- Others --------------------------------

	// // 加锁指定的实体
	// public void lock(T entity, LockMode lock) {
	// getHibernateTemplate().lock(entity, lock);
	// }

	// 强制初始化指定的实体
	// public void initialize(Object proxy) {
	// getHibernateTemplate().
	// }

	// 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	public void flush() {
		getHibernateTemplate().flush();
	}

	@Override
	public Criteria createCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

}