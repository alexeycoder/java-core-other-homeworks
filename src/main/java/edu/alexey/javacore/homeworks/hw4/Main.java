package edu.alexey.javacore.homeworks.hw4;

import java.util.ArrayList;
import java.util.Map;

import edu.alexey.javacore.homeworks.hw4.Store.OrderComposer;
import edu.alexey.javacore.homeworks.hw4.entities.Customer;
import edu.alexey.javacore.homeworks.hw4.entities.Order;
import edu.alexey.javacore.homeworks.hw4.entities.Product;
import edu.alexey.javacore.homeworks.hw4.enums.Category;
import edu.alexey.javacore.homeworks.hw4.exceptions.IllegalAmountException;
import edu.alexey.javacore.homeworks.hw4.exceptions.IncompleteOrderException;
import edu.alexey.javacore.homeworks.hw4.exceptions.NoSuchCustomerException;
import edu.alexey.javacore.homeworks.hw4.exceptions.NoSuchProductException;
import edu.alexey.javacore.homeworks.hw4.exceptions.TooMuchSaleException;

public class Main {

	// Testing Environment

	static final Product[] PRODUCTS = {
			new Product("Компьютерное кресло", Category.OFFICE, 10000),
			new Product("Стол руководителя", Category.OFFICE, 50000),
			new Product("Кресло посетителя", Category.OFFICE, 7000),
			new Product("Диван для гостиной", Category.SOFT, 30000),
			new Product("Пуф в прихожую", Category.SOFT, 5000),
			new Product("Кресло качалка", Category.SOFT, 24000),
			new Product("Платяной шкаф", Category.STORAGE, 12000),
			new Product("Стеллаж Лофт", Category.STORAGE, 9000),
			new Product("Шкаф купе", Category.STORAGE, 18000),
			new Product("Полка настенная", Category.STORAGE, 700),
			new Product("Прикроватная тумбочка", Category.BEDROOM, 5000),
			new Product("Кровать односпальная", Category.BEDROOM, 20000),
			new Product("Кровать двуспальная", Category.BEDROOM, 29000),
			new Product("Кухонный модуль напольный", Category.KITCHEN, 3000),
			new Product("Кухонный модуль навесной", Category.KITCHEN, 4000),
			new Product("Кухонный уголок трехместный", Category.KITCHEN, 9000),
			new Product("Стол обеденный", Category.KITCHEN, 12000)
	};

	static final Customer[] CUSTOMERS = {
			new Customer("Фёдор", "Матвеев", "+7-123-111-22-33"),
			new Customer("Полина", "Крылова", "+7-123-573-91-73"),
			new Customer("Александр", "Кузин", "+7-123-754-85-12")
	};

	// Тестовый поток заказов, строковые входные данные:
	static final Map<String[], String[][]> PURCHASES = Map.of(

			// bad product
			new String[] { "Александр", "Кузин" },
			new String[][] {
					{ "Стол руководителя", "2" },
					{ "Компьютерное кресло", "4" },
					{ "Кресло посетителя", "5" },
					{ "Стеллажж", "1" },
					{ "Пуф в прихожую", "2" },
					{ "Полка настенная", "3" }
			},

			new String[] { "Фёдор", "Матвеев" },
			new String[][] {
					{ "Шкаф купе", "1" },
					{ "Полка настенная", "4" },
					{ "Прикроватная тумбочка", "2" },
					{ "Диван для гостиной", "1" },
					{ "Стол обеденный", "1" },
			},

			new String[] { "Полина", "Крылова" },
			new String[][] {
					{ "Кухонный модуль напольный", "5" },
					{ "Кухонный уголок трехместный", "1" },
					{ "Пуф в прихожую", "2" },
					{ "Кресло качалка", "1" }
			},

			// bad customer
			new String[] { "Вероника", "Григорьева" },
			new String[][] {
					{ "Стеллаж Лофт", "2" },
					{ "Кухонный модуль напольный", "10" },
					{ "Кухонный модуль навесной", "10" },
			},

			new String[] { "Александр", "Кузин" },
			new String[][] {
					{ "Стол руководителя", "2" },
					{ "Компьютерное кресло", "4" },
					{ "Кресло посетителя", "5" },
					{ "Стеллаж Лофт", "1" },
					{ "Пуф в прихожую", "2" },
					{ "Полка настенная", "3" }
			},

			// bad amount
			new String[] { "Фёдор", "Матвеев" },
			new String[][] {
					{ "Полка настенная", "2" },
					{ "Кровать односпальная", "-1" },
					{ "Кресло качалка", "1" },
			},

			new String[] { "Полина", "Крылова" },
			new String[][] {
					{ "Кровать односпальная", "2" },
					{ "Диван для гостиной", "1" },
			}

	);

	public static void main(String[] args) {

		final var store = new Store(PRODUCTS, CUSTOMERS);

		final ArrayList<Order> orders = new ArrayList<>();

		for (var customerRepr : PURCHASES.keySet()) {

			OrderComposer orderComposer;

			// покупатель инициирует заказ
			try {
				orderComposer = store.startOrder(customerRepr[0], customerRepr[1]);
			} catch (NoSuchCustomerException e) {
				printError(String.format("Ошибка: Покупатель \"%s %s\" не зарегистрирован! Заказ будет отменён.",
						customerRepr[0], customerRepr[1]));
				printExceptionDetails(e);
				continue;
			}

			// комплектуем заказ
			for (var itemsRepr : PURCHASES.get(customerRepr)) {

				try {
					orderComposer.putItem(itemsRepr[0], itemsRepr[1]);
				} catch (IllegalAmountException e) {
					printError(String.format("Ошибка: Недопустимое количество товара \"%s\"! Заказ будет отменён.",
							itemsRepr[1]));
					printExceptionDetails(e);
				} catch (NoSuchProductException e) {
					printError(String.format("Ошибка: Несуществующий товар \"%s\" в заказе! Заказ будет отменён.",
							itemsRepr[0]));
					printExceptionDetails(e);
				}
			}

			// завершаем комплектование -- если всё в порядке с заказом,
			// то получаем сформированный Order
			Order order;
			try {
				order = orderComposer.complete();
			} catch (IncompleteOrderException e) {
				printError("Ошибка: Нельзя оформить пустой заказ! Заказ будет отменён.");
				printExceptionDetails(e);
				continue;
			} catch (TooMuchSaleException e) {
				printError(String.format(
						"Ошибка: Слишком большой кумулятивный процент скидки \"%d%%\"! Заказ будет отменён.",
						e.getBadValue()));
				printExceptionDetails(e);
				continue;
			}

			orders.add(order);
		}

		printEmphasized("\nОБРАБОТАННЫЕ ЗАКАЗЫ\n");
		orders.forEach(o -> {
			System.out.println(store.getOrderInfo(o));
			System.out.println();
		});
	}

	private static void printEmphasized(String text) {
		System.out.println("\u001b[1;3;93m" + text + "\u001b[0m");
	}

	private static void printError(String text) {
		System.out.println("\u001b[1;3;91m" + text + "\u001b[0m");
	}

	private static void printExceptionDetails(Throwable e) {
		System.err.println("\u001b[1;3mИнформация об исключении:\u001b[0m");
		e.printStackTrace();
	}
}
