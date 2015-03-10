package org.hbhk.aili.mybatis.server.model;

import java.util.List;

import org.hbhk.aili.mybatis.share.model.BizBaseModel;

public class Person extends BizBaseModel{

	private static final long serialVersionUID = 5710475763489918931L;
	private Long id;
	private String name;
	private List<Order> orderList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

}

