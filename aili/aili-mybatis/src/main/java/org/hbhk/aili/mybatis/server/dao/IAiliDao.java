package org.hbhk.aili.mybatis.server.dao;

import java.util.List;

import org.hbhk.aili.mybatis.server.support.Pagination;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public interface IAiliDao<T, PK> {
	/**
	 * 按ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id);

	/**
	 * 按ID删除实体
	 * 
	 * @param id
	 */
	public void remove(PK id);

	/**
	 * 按ID创建实体
	 * 
	 * @param entity
	 */
	public void create(T entity);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 查询所有实体列表
	 * 
	 * @return
	 */
	public List<T> getAll();

	/**
	 * 按分页查询所有实体
	 * 
	 * @param page
	 * @return
	 */
	public Pagination<T> getAllByPage(Pagination<T> page);

}
