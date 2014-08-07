package org.hbhk.aili.hibernate.server.dao;

import org.hibernate.Session;

public interface IAiliDaoCallback<T> {
	T doInHibernate(Session session);
}
