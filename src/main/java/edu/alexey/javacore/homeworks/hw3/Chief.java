package edu.alexey.javacore.homeworks.hw3;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Chief extends Employee {

	public Chief(String firstName,
			String middleName,
			String lastName,
			LocalDate birthDate,
			String phone,
			String position,
			Integer salary) {
		super(firstName,
				middleName,
				lastName,
				birthDate,
				phone,
				position,
				salary);
	}

	@Override
	public String formattedInfo() {
		return super.formattedInfoBase("Руководитель", "\t");
	}

	// utility mtods:

	/**
	 * Увеличивает оклад работнику на указанную величину.
	 * 
	 * @param employee    Работник.
	 * @param amountToAdd Величина, на которую увеличивается оклад.
	 * @exception IllegalArgumentException если указана отрицательная величина.
	 */
	public static void increaseSalary(Employee employee, int amountToAdd) {
		Objects.requireNonNull(employee);
		if (amountToAdd < 0) {
			throw new IllegalArgumentException("amountToAdd");
		}
		Integer salary = employee.getSalary();
		if (salary == null) {
			return;
		}
		employee.setSalary(salary + amountToAdd);
	}

	/**
	 * Увеличивает оклад на указанную величину <em>только рядовым работникам</em>
	 * из массива.
	 * 
	 * @param employees   Массив работников.
	 * @param amountToAdd Величина, на которую увеличивается оклад.
	 * @exception IllegalArgumentException если указана отрицательная величина.
	 */
	public static void increaseOrdinaryEployeesSalary(Employee[] employees, int amountToAdd) {
		Objects.requireNonNull(employees);
		if (amountToAdd < 0) {
			throw new IllegalArgumentException("amountToAdd");
		}

		Arrays.stream(employees)
				// .filter(e -> e != null && !(e instanceof Chief))
				.filter(e -> e != null && e.getClass().isAssignableFrom(Employee.class))
				.forEach(e -> Chief.increaseSalary(e, amountToAdd));

		// Альтернативное решение с использованием рефлексии:
		// .filter(e -> e != null && e.getClass().isAssignableFrom(Employee.class))
		// isAssignableFrom проверяет, можно ли переменной такого класса
		// присвоить экземпляр класса, указанного в параметре...
		// или, иначе говоря, является ли данный класс по отношению к тому,
		// что указан в параметре, тем же или родительским, или не является.
	}

	public static void main(String[] args) {
		System.out.println(Chief.class.isAssignableFrom(Employee.class));
	}
}
