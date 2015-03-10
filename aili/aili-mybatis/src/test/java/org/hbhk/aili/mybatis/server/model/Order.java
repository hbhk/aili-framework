package org.hbhk.aili.mybatis.server.model;

import org.hbhk.aili.mybatis.share.model.BizBaseModel;

public class Order extends BizBaseModel{
	private static final long serialVersionUID = -8042656832011605832L;
	private Long id;
	private double price;
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", price=" + price + "]";
	}

}
