package org.hbhk.aili.hibernate.server.service.impl;

import org.hbhk.aili.hibernate.server.dao.impl.AiliDaoSurpport;
import org.hbhk.aili.hibernate.server.service.ICateService;
import org.hbhk.aili.hibernate.share.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CateService extends AiliDaoSurpport<Category, Integer> implements
		ICateService {

}
