package org.hbhk.aili.orm.server.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ReadWriteDataSource extends AbstractRoutingDataSource {

	@Autowired(required = false)
	private DataSource readDataSource;

	@Autowired(required = false)
	private DataSource writeDataSource;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(readDataSource, "readDataSource can not be null");
		Assert.notNull(writeDataSource, "writeDataSource can not be null");
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put(ReadWriteSupport.READ, readDataSource);
		targetDataSources.put(ReadWriteSupport.WRITE, writeDataSource);
		this.setTargetDataSources(targetDataSources);
		this.setDefaultTargetDataSource(writeDataSource);
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		logger.debug("Current LookupKey:"
				+ ReadWriteStatusHolder.getReadWriteStatus());
		if (ReadWriteStatusHolder.getReadWriteStatus() == null) {
			return ReadWriteSupport.WRITE;
		}

		return ReadWriteStatusHolder.getReadWriteStatus();
	}

	public DataSource getReadDataSource() {
		return readDataSource;
	}

	public void setReadDataSource(DataSource readDataSource) {
		this.readDataSource = readDataSource;
	}

	public DataSource getWriteDataSource() {
		return writeDataSource;
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}
}
