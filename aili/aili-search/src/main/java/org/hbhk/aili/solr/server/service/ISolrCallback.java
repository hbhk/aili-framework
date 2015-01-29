package org.hbhk.aili.solr.server.service;

import org.apache.solr.client.solrj.SolrServer;

public interface ISolrCallback<T> {

	T doExecute(SolrServer solrServer);

}
