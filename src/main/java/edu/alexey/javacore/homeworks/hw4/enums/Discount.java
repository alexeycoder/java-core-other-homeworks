package edu.alexey.javacore.homeworks.hw4.enums;

public enum Discount {
	
	ZERO(0),
	FIVE(5),
	TEN(10),
	FIFTEEN(15),
	TWENTY(20);

	private final int percentValue;

	public int getPercentValue() {
		return percentValue;
	}

	private Discount(int percentValue) {
		this.percentValue = percentValue;
	}
}
