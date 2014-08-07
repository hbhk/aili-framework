package org.hbhk.aili.hibernate.server;

import java.util.Set;

import javax.annotation.Resource;

import org.hbhk.aili.hibernate.share.model.Category;
import org.hbhk.aili.hibernate.share.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml" })
public class HTest {

	@Resource
	private SessionFactory sessionFactory;

	
	@Test
	public void addc() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Category c = new Category();
		c.setId(2);
		c.setName("c1");
		c.setDescription("desc");
		session.save(c);
		session.getTransaction().commit();
	}

	@Test
	public void add() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Category c = new Category();
		c.setId(1);
		c.setName("c1");
		c.setDescription("desc");
		Product p = new Product();
		p.setName("计算机科学与技术");
		p.setPrice("123");
		p.setDescripton("计算机科学与技术,好啊，真是红啊");
		p.setCategory(c);
		c.getProducts().add(p);

		session.save(p);
		session.getTransaction().commit();
	}

	@Test
	public void find() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Category c = (Category) session.get(Category.class, 1);
		System.out.println("id: " + c.getId() + "  name:" + c.getName());
		Set<Product> p = c.getProducts();
		for (Product product : p) {
			System.out.println("id:" + product.getId() + "  name:"
					+ product.getName() + "  description:"
					+ product.getDescripton());
		}
		session.getTransaction().commit();
	}
}
