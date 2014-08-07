package org.hbhk.aili.mybatis.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hbhk.aili.mybatis.server.dao.IAiliDao;
import org.hbhk.aili.mybatis.share.model.Pagination;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AiliDaoSupport<T, PK> extends SqlSessionDaoSupport
		implements IAiliDao<T, PK> {

	/**
	 * 按ID获取单一记录
	 */
	protected final String OP_GET = ".get";
	/**
	 * 按ID删除记录
	 */
	protected final String OP_DEL = ".remove";
	/**
	 * 按ID更新记录
	 */
	protected final String OP_UPD = ".update";
	/**
	 * 添加记录
	 */
	protected final String OP_CREATE = ".create";
	/**
	 * 查询记录列表
	 */
	protected final String OP_GETALL = ".getAll";

	/**
	 * 返回当前实体的命名空间字符串名称
	 */
	public abstract String getNamespace();

	@Override
	@Autowired(required = false)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public void create(T entity) {
		this.getSqlSession().insert(getNamespace() + OP_CREATE, entity);
	}

	@Override
	public void update(T entity) {
		this.getSqlSession().update(getNamespace() + OP_UPD, entity);
	}

	@Override
	public void remove(PK entityId) {
		this.getSqlSession().delete(getNamespace() + OP_DEL, entityId);
	}

	@Override
	public T get(PK entityId) {
		return this.getSqlSession()
				.selectOne(getNamespace() + OP_GET, entityId);
	}

	@Override
	public List<T> getAll() {
		return this.getSqlSession()
				.selectList(getNamespace() + OP_GETALL, null);
	}

	@Override
	public Pagination<T> getAllByPage(Pagination<T> page) {
		RowBounds rowBounds = new RowBounds(page.getStart(), page.getSize());
		List<T> datas = getSqlSession().selectList(getNamespace() + OP_GETALL,
				rowBounds);
		page.setItems(datas);
		return page;
	}

}
