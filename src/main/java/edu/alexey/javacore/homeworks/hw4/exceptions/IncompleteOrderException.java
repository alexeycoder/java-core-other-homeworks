package edu.alexey.javacore.homeworks.hw4.exceptions;

public class IncompleteOrderException extends Exception {
	
	public IncompleteOrderException() {
		super("Нельзя завершить комплектование пустого заказа, необходима хотя бы одна позиция в заказе.");
	}
}
