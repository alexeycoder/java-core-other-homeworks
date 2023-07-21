package edu.alexey.javacore.homeworks.hw4.enums;

public enum Category {
	
	OFFICE("Офисная мебель"),
	SOFT("Мягкая мебель"),
	STORAGE("Мебель для хранения"),
	BEDROOM("Мебель для спальни"),
	KITCHEN("Мебель для кухни");

	private final String name;

	public String getName() {
		return name;
	}

	private Category(String name) {
		this.name = name;
	}
}
