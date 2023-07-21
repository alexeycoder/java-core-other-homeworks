package edu.alexey.javacore.homeworks.hw4.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Order {

	private static int nextPseudoId = 1;

	private static int acquirePseudoId() {
		return nextPseudoId++;
	}

	private final int pseudoId;
	private final Customer customer;
	private final ArrayList<OrderItem> items;

	public Order(Customer customer, List<OrderItem> items) {
		this.pseudoId = acquirePseudoId();
		this.customer = Objects.requireNonNull(customer);
		this.items = new ArrayList<>();
		if (items != null && !items.isEmpty()) {
			this.items.addAll(items);
		}
	}

	public Order(Customer customer) {
		this(customer, null);
	}

	public int getPseudoId() {
		return pseudoId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<OrderItem> getItems() {
		return List.copyOf(items);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public Order put(Product product, int amount) {
		Objects.requireNonNull(product, "product");
		if (amount <= 0) {
			throw new IllegalArgumentException("amount");
		}
		// ∃ 2-а варианта:
		// 1. товар ещё не добавлялся в заказ --> просто добавляем новый;
		// 2. товар уже есть в заказе --> заменяем существующую позицию на новую
		// с увеличенным на amount количеством.
		int index = IntStream.range(0, items.size())
				.filter(i -> product.equals(items.get(i).product()))
				.findAny().orElse(-1);

		if (index < 0) {
			items.add(new OrderItem(product, amount));
		} else {
			amount += items.get(index).amount();
			items.set(index, new OrderItem(product, amount));
		}

		return this;
	}
}
