package edu.alexey.javacore.homeworks.hw4.entities;

import edu.alexey.javacore.homeworks.hw4.enums.Category;

public class Product {

	private String name;
	private Category category;
	private Integer price;

	public Product(String name, Category category, Integer price) {
		this.name = name;
		this.category = category;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
