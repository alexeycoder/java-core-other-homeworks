package edu.alexey.javacore.homeworks.hw4.exceptions;

public class TooMuchSaleException extends Exception {

	private int badValue;

	public int getBadValue() {
		return badValue;
	}

	public TooMuchSaleException(int requested, int maxAllowed) {
		super(String.format("Суммарный процент скидки в заказе %d%% превышает максимально допустимый %d%%.",
				requested, maxAllowed));
		badValue = requested;
	}
}
