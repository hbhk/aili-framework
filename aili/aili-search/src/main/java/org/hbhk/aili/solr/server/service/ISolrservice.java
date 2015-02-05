package org.hbhk.aili.solr.server.service;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.hbhk.aili.solr.share.model.SolrBase;

public interface ISolrservice<T extends SolrBase> {

	void updateIndex(List<T> list);

	void addIndex(T t);

	void addIndexs(List<T> list);

	void delAllIndexs();

	void delIndexs(List<String> ids);

	List<T> queryListWithPage(String query, String sort,
			int start, int size, Class<T> cls);

	List<T> queryListWithPage(String query,Map<String, String> filters, String sort,
			int start, int size, Class<T> cls);
	QueryResponse query(SolrParams params);
}
