package org.hbhk.aili.mybatis.server.model;

import java.util.List;

public class Person {

	private int id;
	private String name;
	private List<Order> orderList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(int id, String name, List<Order> orderList) {
		super();
		this.id = id;
		this.name = name;
		this.orderList = orderList;
	}

}

