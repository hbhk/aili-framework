package org.hbhk.aili.cache.server.serialize;

public interface ICacheSerialize<T> {

	T serialize(Object obj);

	Object deserialize(T obj);
}
