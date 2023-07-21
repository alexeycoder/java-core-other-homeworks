package edu.alexey.javacore.homeworks.hw4.exceptions;

public class NoSuchCustomerException extends Exception {

	public NoSuchCustomerException(String customerName) {
		super(String.format("Указан незарегистрированный покупатель \"%s\"", customerName));
	}

	public NoSuchCustomerException() {
		super("Указан незарегистрированный покупатель.");
	}
}
