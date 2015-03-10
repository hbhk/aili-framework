package org.hbhk.aili.mybatis.server.model;

public class Order {
	private int id;
	private double price;
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

}
