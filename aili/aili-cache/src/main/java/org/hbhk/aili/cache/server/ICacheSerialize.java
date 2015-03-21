package org.hbhk.aili.cache.server;

public interface ICacheSerialize<T> {

	T serialize(Object obj);

	Object deserialize(T obj);
}
