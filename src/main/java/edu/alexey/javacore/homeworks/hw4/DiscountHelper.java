package edu.alexey.javacore.homeworks.hw4;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import edu.alexey.javacore.homeworks.hw4.entities.Order;
import edu.alexey.javacore.homeworks.hw4.entities.OrderItem;
import edu.alexey.javacore.homeworks.hw4.enums.Category;
import edu.alexey.javacore.homeworks.hw4.enums.Discount;

public class DiscountHelper {

	private final Map<Category, Discount> discountByCategory = generateDiscountByCategory();

	private static Map<Category, Discount> generateDiscountByCategory() {

		final Supplier<Discount> rndDiscSupp = () -> {
			var ds = Discount.values();
			int rndIdx = ThreadLocalRandom.current().nextInt(ds.length);
			return ds[rndIdx];
		};

		var map = Arrays.stream(Category.values())
				.collect(Collectors.toMap(c -> c, c -> rndDiscSupp.get()));
		return map;
	}

	public int totalDiscountVerbatim(Order order) {
		var items = order.getItems();
		var total = items.stream().mapToInt(this::getPercentValue).sum();
		return total;
	}

	public int getPercentValue(OrderItem orderItem) {
		var product = orderItem.product();
		var category = product.getCategory();
		var discount = discountByCategory.get(category);
		return discount.getPercentValue();
	}

	public int calcItemCost(OrderItem orderItem) {
		int value = orderItem.product().getPrice() * orderItem.amount();
		int discPcntVal = getPercentValue(orderItem);
		assert discPcntVal >= 0 && discPcntVal < 100 : "illegal discount";
		value *= 100 - discPcntVal;
		value /= 100;
		return value;
	}

	public int calcTotalCost(Order order) {
		return order.getItems().stream().mapToInt(this::calcItemCost).sum();
	}
}
