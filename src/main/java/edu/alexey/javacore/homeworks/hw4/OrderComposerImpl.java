package edu.alexey.javacore.homeworks.hw4;

import java.util.Arrays;
import java.util.Objects;

import edu.alexey.javacore.homeworks.hw4.entities.Order;
import edu.alexey.javacore.homeworks.hw4.exceptions.IllegalAmountException;
import edu.alexey.javacore.homeworks.hw4.exceptions.IncompleteOrderException;
import edu.alexey.javacore.homeworks.hw4.exceptions.NoSuchCustomerException;
import edu.alexey.javacore.homeworks.hw4.exceptions.NoSuchProductException;
import edu.alexey.javacore.homeworks.hw4.exceptions.TooMuchSaleException;

/**
 * Реализация фабрики заказов.
 * <hr>
 * Усиливаем инкапсуляцию магазина:
 * <br>
 * 1. Благодаря наследованию от внутреннего по отношению к классу магазина
 * абстрактного класса фабрики заказов (Store.OrderComposer), реализация фабрики
 * заказов может иметь доступ к состоянию магазина через защищённые поля/методы
 * данного внутреннего-класса-родителя, что позволяет магазину не раскрывать
 * наружу элементы своего состояния.
 * <br>
 * 2. Объявление конструктора фабрики заказов защищённым, обеспечивает
 * невозможность бесконтрольного создания её экземпляров снаружи, вне
 * специального фабричного метода (startOrder) у экземпляра конкретного
 * магазина, диктующего по-сути механизм/порядок комплектования заказа:
 * store.startOrder(…).putItem(…).putItem(…).complete() -> Order.
 */
public class OrderComposerImpl extends Store.OrderComposer {

	final static int MAX_DISCOUNT_VERBATIM = 50;

	private final Order order;

	protected OrderComposerImpl(Store store, String customerFirstName, String customerLastName)
			throws NoSuchCustomerException {

		// наследник внутреннего класса использует спец. конструктор,
		// в котором, используя переданную ссылку на экземпляра внешнего класса
		// (Store), вызывает конструктор родительского внутреннего класса:
		store.super();

		if (customerFirstName == null || customerFirstName.isBlank()) {
			throw new IllegalArgumentException("customerFirstName");
		}
		if (customerLastName == null || customerLastName.isBlank()) {
			throw new IllegalArgumentException("customerLastName");
		}

		var customerOpt = Arrays.stream(customers).filter(Objects::nonNull)
				.filter(c -> customerFirstName.equalsIgnoreCase(c.getFirstName()))
				.filter(c -> customerLastName.equalsIgnoreCase(c.getLastName()))
				.findAny();
		if (customerOpt.isEmpty()) {
			throw new NoSuchCustomerException(String.format("%s $s", customerFirstName, customerLastName));
		}

		this.order = new Order(customerOpt.get());
	}

	public OrderComposerImpl putItem(String productName, String productAmount)
			throws IllegalAmountException, NoSuchProductException {

		assert order != null : "the 'order' field is unexpectedly null";

		if (productName == null || productName.isBlank()) {
			throw new IllegalArgumentException("productName");
		}
		if (productAmount == null || productAmount.isBlank()) {
			throw new IllegalArgumentException("productAmount");
		}
		int amount = Integer.parseInt(productAmount.strip()); // any NumberFormatException propagates up -- OK!
		if (amount <= 0) {
			throw new IllegalAmountException(amount, 1, null);
		}
		var productOpt = Arrays.stream(products).filter(Objects::nonNull)
				.filter(p -> productName.strip().equalsIgnoreCase(p.getName()))
				.findAny();
		if (productOpt.isEmpty()) {
			throw new NoSuchProductException(productName);
		}

		order.put(productOpt.get(), amount);
		return this;
	}

	public Order complete() throws IncompleteOrderException, TooMuchSaleException {
		assert order != null : "the 'order' field is unexpectedly null";

		if (order.isEmpty()) {
			throw new IncompleteOrderException();
		}

		int totalDiscountPercent = discountHelper.totalDiscountVerbatim(order);
		if (totalDiscountPercent > MAX_DISCOUNT_VERBATIM) {
			throw new TooMuchSaleException(totalDiscountPercent, MAX_DISCOUNT_VERBATIM);
		}

		return order;
	}
}
