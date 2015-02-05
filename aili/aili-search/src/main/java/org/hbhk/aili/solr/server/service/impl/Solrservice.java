package org.hbhk.aili.solr.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.hbhk.aili.solr.server.service.ISolrCallback;
import org.hbhk.aili.solr.server.service.ISolrservice;
import org.hbhk.aili.solr.share.model.SolrBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Solrservice implements ISolrservice<SolrBase> {

	@Autowired
	private SolrServer solrServer;

	public void rollback() {
		try {
			solrServer.rollback();
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateIndex(List<SolrBase> list) {
		try {
			List<String> ids = new ArrayList<String>();
			for (SolrBase solr : list) {
				ids.add(solr.getId());
			}
			solrServer.deleteById(ids);
			solrServer.addBeans(list);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	public void addIndexs(List<SolrBase> list) {
		try {
			solrServer.addBeans(list);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}

	}

	public void addIndex(SolrBase t) {
		try {
			solrServer.addBean(t);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}

	}

	public List<SolrBase> queryListWithPage(String query, String sort,
			int start, int size, Class<SolrBase> cls) {
		ModifiableSolrParams params = new ModifiableSolrParams();
		if (query.equals("")) {
			query = "*:*";
		}
		params.set("q", query);
		params.set("start", start);
		params.set("rows", size);
		params.set("sort", sort);
		List<SolrBase> list = new ArrayList<SolrBase>();
		try {
			QueryResponse response = solrServer.query(params);
			list = response.getBeans(cls);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public QueryResponse query(SolrParams params) {
		try {
			return  solrServer.query(params);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T execute(ISolrCallback<T> action) {
		try {
			T t = action.doExecute(solrServer);
			solrServer.commit();
			return t;
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}

	}

	public void delAllIndexs() {
		try {
			solrServer.deleteByQuery("*:*");
			solrServer.commit();
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	public void delIndexs(List<String> ids) {
		try {
			solrServer.deleteById(ids);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<SolrBase> queryListWithPage(String query,
			Map<String, String> filters, String sort, int start,
			int size, Class<SolrBase> cls) {
		
		
		return null;
	}

}
