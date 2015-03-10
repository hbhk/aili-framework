package org.hbhk.aili.mybatis.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class OneToManyRowMapperResultSetExtractor<T> implements
		ResultSetExtractor<List<T>> {
	public static final Logger log = LoggerFactory
			.getLogger(OneToManyRowMapperResultSetExtractor.class);
	private final RowMapper<T> rowMapper;

	private final int rowsExpected;

	/**
	 * Create a new RowMapperResultSetExtractor.
	 * 
	 * @param rowMapper
	 *            the RowMapper which creates an object for each row
	 */
	public OneToManyRowMapperResultSetExtractor(RowMapper<T> rowMapper) {
		this(rowMapper, 0);
	}

	/**
	 * Create a new RowMapperResultSetExtractor.
	 * 
	 * @param rowMapper
	 *            the RowMapper which creates an object for each row
	 * @param rowsExpected
	 *            the number of expected rows (just used for optimized
	 *            collection handling)
	 */
	public OneToManyRowMapperResultSetExtractor(RowMapper<T> rowMapper,
			int rowsExpected) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
		this.rowsExpected = rowsExpected;
	}

	@Override
	public List<T> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Set<T> results = (this.rowsExpected > 0 ? new HashSet<T>(this.rowsExpected) : new HashSet<T>());
		int rowNum = 0;
		while (rs.next()) {
			results.add(this.rowMapper.mapRow(rs, rowNum++));
		}
		List<T> list = (this.rowsExpected > 0 ? new ArrayList<T>(this.rowsExpected) : new ArrayList<T>());
		list.addAll(results);
		return list;
	}
}
