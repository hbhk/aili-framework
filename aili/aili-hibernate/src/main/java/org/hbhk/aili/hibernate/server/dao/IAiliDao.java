package org.hbhk.aili.hibernate.server.dao;

import java.util.List;

import org.hbhk.aili.hibernate.share.model.Page;
import org.hibernate.Criteria;
import org.hibernate.LockMode;

/**
 * 
 * @author lny
 */
public interface IAiliDao<T, PK> {
	// -------------------- 基本检索、增加、修改、删除操作 --------------------

	// 根据主键获取实体。如果没有相应的实体，返回 null。
	T get(PK id);

	// 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
	T getWithLock(PK id, LockMode lock);

	// 根据主键获取实体。如果没有相应的实体，抛出异常。
	T load(PK id);

	// 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
	T loadWithLock(PK id, LockMode lock);

	// 获取全部实体。
	List<T> loadAll();

	// loadAllWithLock() ?

	// 更新实体
	void update(T entity);

	// 更新实体并加锁
	// void updateWithLock(T entity, LockMode lock);

	// 存储实体到数据库
	void save(T entity);

	// saveWithLock()

	// 增加或更新实体
	void saveOrUpdate(T entity);

	// 增加或更新集合中的全部实体
	// void saveOrUpdateAll(Collection<T> entities);

	// 删除指定的实体
	void delete(T entity);

	// 加锁并删除指定的实体
	// void deleteWithLock(T entity, LockMode lock);

	// 根据主键删除指定实体
	void deleteByKey(PK id);

	Page<T> queryForList(String hql, Object[] params, Page<T> page);

	// 根据主键加锁并删除指定的实体
	// void deleteByKeyWithLock(PK id, LockMode lock);

	// 删除集合中的全部实体
	// void deleteAll(Collection<T> entities);

	// -------------------- HSQL ----------------------------------------------

	// 使用HSQL语句直接增加、更新、删除实体
	// int bulkUpdate(String queryString);
	//
	// // 使用带参数的HSQL语句增加、更新、删除实体
	// int bulkUpdate(String queryString, Object[] values);
	//
	// // 使用HSQL语句检索数据
	// List<T> find(String queryString);
	//
	// // 使用带参数的HSQL语句检索数据
	// List<T> find(String queryString, Object[] values);
	//
	// // 使用带命名的参数的HSQL语句检索数据
	// List<T> findByNamedParam(String queryString, String[] paramNames,
	// Object[] values);
	//
	// // 使用命名的HSQL语句检索数据
	// List<T> findByNamedQuery(String queryName);
	//
	// // 使用带参数的命名HSQL语句检索数据
	// List<T> findByNamedQuery(String queryName, Object[] values);
	//
	// // 使用带命名参数的命名HSQL语句检索数据
	// List<T> findByNamedQueryAndNamedParam(String queryName,
	// String[] paramNames, Object[] values);
	//
	// // 使用HSQL语句检索数据，返回 Iterator
	// Iterator iterate(String queryString);
	//
	// // 使用带参数HSQL语句检索数据，返回 Iterator
	// Iterator iterate(String queryString, Object[] values);
	//
	// // 关闭检索返回的 Iterator
	// void closeIterator(Iterator it);

	// -------------------------------- Criteria ------------------------------

	// 创建与会话无关的检索标准对象
	// DetachedCriteria createDetachedCriteria();

	// 创建与会话绑定的检索标准对象
	Criteria createCriteria();

	// 使用指定的检索标准检索数据
	// List<T> findByCriteria(DetachedCriteria criteria);

	// 使用指定的检索标准检索数据，返回部分记录
	// List<T> findByCriteria(DetachedCriteria criteria, int firstResult,
	// int maxResults);

	// 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
	List<T> findEqualByEntity(T entity, String[] propertyNames);

	// 使用指定的实体及属性(非主键)检索（满足属性 like 串实体值）数据
	List<T> findLikeByEntity(T entity, String[] propertyNames);

	// 使用指定的检索标准检索数据，返回指定范围的记录
	// Integer getRowCount(DetachedCriteria criteria);

	// 使用指定的检索标准检索数据，返回指定统计值
	// Object getStatValue(DetachedCriteria criteria, String
	// propertyName,
	// String StatName);

	// -------------------------------- Others --------------------------------

	// // 加锁指定的实体
	// void lock(T entity, LockMode lockMode);
	//
	// // 强制初始化指定的实体
	// void initialize(Object proxy);

	// 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	void flush();

}
