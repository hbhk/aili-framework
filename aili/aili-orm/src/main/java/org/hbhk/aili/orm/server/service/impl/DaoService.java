package org.hbhk.aili.orm.server.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.hbhk.aili.orm.server.annotation.SimpleQuery;
import org.hbhk.aili.orm.server.annotation.SimpleUpdate;
import org.hbhk.aili.orm.server.handler.DefaultNameHandler;
import org.hbhk.aili.orm.server.handler.INameHandler;
import org.hbhk.aili.orm.server.page.MySqlPageQueryProvider;
import org.hbhk.aili.orm.server.page.PageQueryProvider;
import org.hbhk.aili.orm.server.service.IDaoService;
import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.hbhk.aili.orm.share.model.SqlContext;
import org.hbhk.aili.orm.share.util.SqlUtil;
import org.hbhk.aili.orm.share.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

@Service
public class DaoService implements IDaoService {

	protected final Logger logger = LoggerFactory.getLogger(DaoService.class);

	protected JdbcTemplate jdbcTemplate;

	private PageQueryProvider pageQueryProvider;
	private INameHandler nameHandler;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		pageQueryProvider = new MySqlPageQueryProvider();
		nameHandler = new DefaultNameHandler();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	protected String getCountQueryStringForSql(String sql, boolean withGroupby) {
		if (sql == null)
			return null;
		sql = sql.trim();
		String lowercaseSQL = sql.toLowerCase();
		int delim1 = lowercaseSQL.indexOf("from");
		int delim2 = lowercaseSQL.indexOf("order by");
		if (delim1 < 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("It seemed that current sql is not one valid one.");
				logger.debug("SQL:{}", sql);
			}
			return null;
		}
		if (delim2 == -1)
			delim2 = sql.length();
		String countSQL = "";
		if (withGroupby) {
			countSQL = "select count(1) as num from ("
					+ sql.substring(0, delim2) + ") tmp_tbl";
		} else {
			countSQL = "select count(1) as num "
					+ sql.substring(delim1, delim2);
		}
		logger.debug("Count SQL:{}", countSQL);
		return countSQL;
	}

	// sql part
	private <T> List<T> findByNativeQueryNative(String queryString,
			Object[] params, Sort[] sorts, int start, int pageSize,
			RowMapper<T> rowMapper) {
		if (sorts != null && sorts.length > 0) {
			queryString += " order by " + StringUtil.join(sorts);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("NativeFind[{}]", queryString);
			logger.debug("parameters: {}", params == null ? "null" : "["
					+ params.length + "]");
			if (params != null) {
				int index = 1;
				for (Object p : params) {
					logger.debug("{}): {}", index++, p);
				}
			}
		}
		if (start < 0 || pageSize < 0)
			return jdbcTemplate.query(queryString, params, rowMapper);
		else
			return jdbcTemplate.query(
					pageQueryProvider == null ? queryString : pageQueryProvider
							.getPagableQuery(queryString, start, pageSize),
					params, rowMapper);
	}

	protected <T> Pagination<T> findByNativeQueryNative(String queryString,
			Object[] params, Sort[] sorts, int start, int pageSize,
			boolean withGroupby, RowMapper<T> rowMapper) {
		Pagination<T> p = new Pagination<T>();
		List<T> list = findByNativeQueryNative(queryString, params, sorts,
				start, pageSize, rowMapper);
		p.setItems(list);
		String countQueryString = getCountQueryStringForSql(queryString,
				withGroupby);
		p.setCount((Integer) findByNativeQueryNative(countQueryString, params,
				null, -1, -1, new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt(1);
					}
				}).iterator().next());
		return setPagination(p, start, pageSize, sorts);
	}

	protected <T> Pagination<T> setPagination(Pagination<T> p, int start,
			int pageSize, Sort[] sorts) {
		if (pageSize == 0)
			throw new IllegalArgumentException();
		p.setCurrentPage((start / pageSize) + 1);
		p.setTotalPages((int) p.getCount() / pageSize
				+ (p.getCount() % pageSize == 0 ? 0 : 1));
		p.setStart(start);
		p.setSize(pageSize);
		StringBuilder sortStr = new StringBuilder();
		if (sorts != null) {
			for (Sort sort : sorts) {
				sortStr.append("," + sort.getField() + " " + sort.getType());
			}
		}
		String s = sortStr.toString();
		p.setSortStr(s.length() == 0 ? null : s.substring(1));
		return p;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public <T> T getByPrimaryKey(Class<T> clazz, Serializable pk) {
		return null;
	}

	@Override
	public <T> T save(T model) {
		SqlContext context = SqlUtil.buildInsertSql(model, nameHandler);
		jdbcTemplate.update(context.getSql().toString(), context.getParams()
				.toArray());
		return model;
	}

	@Override
	public <T> void delete(T model) {
		SqlContext context = SqlUtil.buildUpdateSql(model, nameHandler);
		jdbcTemplate.update(context.getSql().toString(), context.getParams()
				.toArray());
	}

	@Override
	public <T> void deleteByPrimaryKey(Class<T> clazz, Serializable pk) {

	}

	@Override
	public <T> List<T> findByNativeQuery(String queryString, Object[] params,
			RowMapper<T> rowMapper) {
		return (List<T>) jdbcTemplate.query(queryString, rowMapper);
	}

	@Override
	public <T> List<T> findByNativeQuery(String queryString, Object[] params,
			Sort[] sorts, RowMapper<T> rowMapper) {
		return (List<T>) jdbcTemplate.query(queryString, rowMapper);
	}

	@Override
	public <T> List<T> findByNativeQuery(String queryString, Object[] params,
			int start, int pageSize, RowMapper<T> rowMapper) {
		queryString = pageQueryProvider.getPagableQuery(queryString, start,
				pageSize);
		return (List<T>) jdbcTemplate.query(queryString, rowMapper);
	}

	@Override
	public <T> List<T> findByNativeQuery(String queryString, Object[] params,
			Sort[] sorts, int start, final int pageSize,
			final RowMapper<T> rowMapper) {
		if (start == -1 && pageSize == -1) {
			return (List<T>) jdbcTemplate.query(queryString, rowMapper);
		}
		queryString = pageQueryProvider.getPagableQuery(queryString, start,
				pageSize);
		return (List<T>) jdbcTemplate.query(queryString, rowMapper);
	}

	@Override
	public <T> Pagination<T> findByNativeQuery(String queryString,
			Object[] params, int start, int pageSize, boolean withGroupby,
			RowMapper<T> rowMapper) {
		return findByNativeQueryNative(queryString, params, null, start,
				pageSize, withGroupby, rowMapper);
	}

	@Override
	public <T> Pagination<T> findByNativeQuery(String queryString,
			Object[] params, Sort[] sorts, int start, int pageSize,
			boolean withGroupby, RowMapper<T> rowMapper) {
		return findByNativeQueryNative(queryString, params, sorts, start,
				pageSize, withGroupby, rowMapper);
	}

	@Override
	public <T> T findOneByNativeQuery(String queryString, Object[] params,
			RowMapper<T> rowMapper, Sort[] sorts) {
		return (T) jdbcTemplate.query(queryString, rowMapper).get(0);
	}

	@Override
	public int batchUpdateByNativeQuery(String queryString, Object[] params,
			Class<?>[] types) {
		return jdbcTemplate.update(queryString);
	}

	@Override
	public void executeDDL(String queryString) {
		jdbcTemplate.update(queryString);
	}

	@Override
	public Map<String, Object> executeSP(String spName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> executeSp(String spName,
			SqlParameter[] sqlParameters, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void evict(T model) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T findOneByQueryEx(String queryString,
			Map<String, Object> params, Sort[] sorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByQueryEx(String queryString,
			Map<String, Object> params, Sort[] sorts, int start, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Pagination<T> findByQueryEx(String queryString,
			Map<String, Object> params, Sort[] sorts, int start, int pageSize,
			boolean withGroupby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SimpleQuery
	public <T> List<T> get(T model, RowMapper<T> rowMapper) {
		SqlContext context = SqlUtil.buildQuerySql(model, nameHandler);
		String queryString = context.getSql().toString();
		List<Object> args = context.getParams();
		return jdbcTemplate.query(queryString, rowMapper, args.toArray());
	}

	@Override
	@SimpleUpdate
	public <T> T update(T model) {
		SqlContext context = SqlUtil.buildUpdateSql(model, nameHandler);
		jdbcTemplate.update(context.getSql().toString(), context.getParams()
				.toArray());
		return model;
	}

	@Override
	public <T> T getOne(T model, RowMapper<T> rowMapper) {
		List<T> ts = get(model, rowMapper);
		if (CollectionUtils.isEmpty(ts)) {
			return null;
		}
		return ts.get(0);
	}

	@Override
	public <T> List<T> get(T model, Page page, RowMapper<T> rowMapper) {
		SqlContext context = SqlUtil.buildQuerySql(model, nameHandler);
		String queryString = context.getSql().toString();
		// (pageNow-1)*pageSize,pageSize;
		queryString = queryString + " limit " + page.getStart() + ","
				+ page.getSize();
		List<Object> args = context.getParams();
		return jdbcTemplate.query(queryString, rowMapper, args.toArray());
	}

}
