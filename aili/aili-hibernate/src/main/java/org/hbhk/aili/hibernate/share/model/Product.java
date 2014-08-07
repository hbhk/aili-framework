package org.hbhk.aili.hibernate.share.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "t_test_product")
public class Product implements java.io.Serializable {

	private static final long serialVersionUID = -1546206493725028472L;
	private Integer id;
	private Category category;
	private String name;
	private String price;
	private String descripton;

	public Product() {
	}

	public Product(Category category, String name, String price,
			String descripton) {
		this.category = category;
		this.name = name;
		this.price = price;
		this.descripton = descripton;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// 延迟加载：多对一方式
	// 关联信息：外键name = "category_id"
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "name", length = 500)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "price", length = 10)
	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "descripton", length = 500)
	public String getDescripton() {
		return this.descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

}
