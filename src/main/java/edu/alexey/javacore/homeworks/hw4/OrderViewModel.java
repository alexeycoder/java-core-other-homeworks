package edu.alexey.javacore.homeworks.hw4;

import java.util.Arrays;
import java.util.Objects;

import edu.alexey.javacore.homeworks.hw4.entities.Customer;
import edu.alexey.javacore.homeworks.hw4.entities.Order;
import edu.alexey.javacore.homeworks.hw4.entities.OrderItem;
import edu.alexey.javacore.homeworks.hw4.enums.Category;
import edu.alexey.utils.StringUtils;

public class OrderViewModel {

	final static String INDENT = "  ";
	final static int PRICE_LEN = 5;
	final static int AMOUNT_LEN = 2;
	final static int PROD_NAME_LEN = 28;
	final static int CAT_NAME_LEN = Arrays.stream(Category.values())
			.mapToInt(c -> c.getName().length())
			.max().getAsInt();

	private final Order order;
	private final DiscountHelper discountHelper;

	public OrderViewModel(Order order, DiscountHelper discountHelper) {
		this.order = Objects.requireNonNull(order);
		this.discountHelper = Objects.requireNonNull(discountHelper);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder("Заказ №")
				.append(order.getPseudoId())
				.append(System.lineSeparator())
				.append(INDENT).append("Покупатель: ")
				.append(toStrRepr(order.getCustomer()))
				.append(System.lineSeparator())
				.append(INDENT).append("Позиции: ");

		var items = order.getItems();
		int numLen = Integer.toString(items.size()).length();
		int num = 1;

		for (var item : items) {
			sb.append(System.lineSeparator())
					.append(INDENT).append(INDENT)
					.append(toStrRepr(item, num++, numLen));
		}
		sb.append(System.lineSeparator())
				.append(INDENT).append("Простая сумма процентов скидки по позициям: ")
				.append(discountHelper.totalDiscountVerbatim(order)).append("%")
				.append(System.lineSeparator())
				.append(INDENT).append("Стоимость итого: ")
				.append(discountHelper.calcTotalCost(order));

		return sb.toString();
	}

	private static String toStrRepr(Customer customer) {
		String repr = customer.getFirstName() + " " + customer.getLastName();
		var phone = customer.getPhone();
		if (phone != null && !phone.isBlank()) {
			repr += ", Тел.: " + phone;
		}
		return repr;
	}

	private String toStrRepr(OrderItem orderItem, int num, int numLen) {
		var product = orderItem.product();
		var amount = orderItem.amount();
		var category = product.getCategory();
		var price = product.getPrice();
		var discountPcnt = discountHelper.getPercentValue(orderItem);
		var cost = discountHelper.calcItemCost(orderItem);

		return new StringBuilder(StringUtils.padLeft(Integer.toString(num), " ", numLen))
				.append(". ").append(StringUtils.padRight(product.getName(), "\u2026", PROD_NAME_LEN))
				.append("\u2026").append(StringUtils.padCenter(category.getName(), "\u2026", CAT_NAME_LEN))
				.append("\u2026").append(StringUtils.padLeft(Integer.toString(amount), " ", AMOUNT_LEN))
				.append(" \u2715 ").append(StringUtils.padLeft(Integer.toString(price), " ", PRICE_LEN))
				.append(" ").append(discountPcnt != 0 ? "-" : " ")
				.append(Integer.toString(discountPcnt)).append("% = ")
				.append(cost).toString();
	}
}
