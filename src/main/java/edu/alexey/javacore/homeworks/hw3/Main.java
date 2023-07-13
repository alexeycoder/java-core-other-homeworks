package edu.alexey.javacore.homeworks.hw3;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Main {

	static final Employee[] employeesRepo = {
			new Employee("Анна", "Александровна", "Кондратова",
					LocalDate.of(1980, 11, 5), "+7-123-456-78-90", "Менеджер", 50000),
			new Employee("София", "Марковна", "Виноградова",
					LocalDate.of(1995, 5, 5), "+7-123-159-75-36", "Секретарь", 45000),
			new Chief("Фёдор", "Александрович", "Матвеев",
					LocalDate.of(1974, 9, 5), "+7-123-111-22-33", "Генеральный директор", 100000),
			new Employee("Александр", "Михайлович", "Кузин",
					LocalDate.of(1968, 12, 5), "+7-123-754-85-12", "Менеджер", 55000),
			new Chief("Виктория", "Михайловна", "Кочергина",
					LocalDate.of(1983, 8, 5), "+7-123-444-55-66", "Главный бухгалтер", 75000),
			new Employee("Амалия", "Александровна", "Яшина",
					LocalDate.of(1988, 3, 5), "+7-123-258-59-53", "Инженер", null),
			null,
			new Employee("Амина", "Тимофеевна", "Николаева",
					LocalDate.of(1976, 4, 5), "+7-123-856-45-27", "Инженер", 60000),
			null,
			new Employee("Полина", "Савельевна", "Крылова",
					LocalDate.of(1990, 10, 5), "+7-123-573-91-73", "Инженер-системотехник", 60000),
			new Employee("Давид", "Владимирович", "Яковлев",
					null, "+7-123-157-35-48", "Техник-контролёр", 60000),
			new Chief("Елизавета", "Петровна", "Григорьева",
					LocalDate.of(1979, 6, 5), "+7-123-532-25-51", "Старший инженер", 75000)
	};

	public static void main(String[] args) {

		printEmphasized("\nВывод форматированных сведений:\n");
		Arrays.stream(employeesRepo).filter(Objects::nonNull).forEach(Employee::printInfo);

		printEmphasized("\nВывод в форме для отладки\n(добавлены null элементы и значения некоторых полей для проверки методов на null-friendly):\n");
		Arrays.stream(employeesRepo).forEach(System.out::println);

		printEmphasized("\nУвеличиваем оклад неруководящим работникам на 5000:\n");
		Chief.increaseOrdinaryEployeesSalary(employeesRepo, 5000);
		Arrays.stream(employeesRepo).forEach(System.out::println);

		printEmphasized("\nСортировка с помощью компаратора \"по-возрасту\" (null-friendly, nulls first):\n");
		Arrays.stream(employeesRepo).sorted(EmployeeComparators.byAge()).forEach(System.out::println);

		printEmphasized("\nСортировка с помощью компаратора \"по-окладу\" (null-friendly, nulls first):\n");
		Arrays.stream(employeesRepo).sorted(EmployeeComparators.bySalary()).forEach(System.out::println);

		System.out.println();
	}

	private static void printEmphasized(String text) {
		System.out.println("\u001b[1;3;93m" + text + "\u001b[0m");
	}
}
