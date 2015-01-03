package org.hbhk.aili.nosql.server.mongodb;

import org.springframework.transaction.support.ResourceHolder;
import org.springframework.transaction.support.ResourceHolderSynchronization;
/**
 * mongo同步锁
 */
public class MongoSynchronization extends ResourceHolderSynchronization<ResourceHolder, Object> {

    public MongoSynchronization(ResourceHolder resourceHolder, Object resourceKey) {
        super(resourceHolder, resourceKey);
    }
}
