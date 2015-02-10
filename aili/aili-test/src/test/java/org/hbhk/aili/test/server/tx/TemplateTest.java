package org.hbhk.aili.test.server.tx;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TemplateTest {

	public static void main(String[] args) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.execute(new ConnectionCallback<String>() {
			@Override
			public String doInConnection(Connection con) throws SQLException,
					DataAccessException {
				return null;
			}
		});
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new HibernateTransactionManager();
		// new 一个事务
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		// 初始化事务,参数定义事务的传播类型;
		TransactionStatus status = transactionManager.getTransaction(def);
		// 获得事务状态
		try {
			transactionManager.commit(status);
			// 提交事务;
		} catch (Exception e) {
			transactionManager.rollback(status);
			// 回滚事务;
		}
	}

}
