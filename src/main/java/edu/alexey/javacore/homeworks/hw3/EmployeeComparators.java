package edu.alexey.javacore.homeworks.hw3;

import java.util.Comparator;

public class EmployeeComparators {

	static final Comparator<Employee> BY_AGE = new NullFriendlyComparatorDecorator<>(new ComparatorByAge(), true);
	static final Comparator<Employee> BY_SALARY = new NullFriendlyComparatorDecorator<>(new ComparatorBySalary(), true);

	public static Comparator<Employee> byAge() {
		return BY_AGE;
	}

	public static Comparator<Employee> bySalary() {
		return BY_SALARY;
	}

	static class ComparatorByAge implements Comparator<Employee> {
		@Override
		public int compare(Employee e1, Employee e2) {

			// Поскольку дата рождения может быть не задана,
			// то и age() окажется null;
			// используем утилитный статический метод из декороатора
			// чтобы обойти это:
			return NullFriendlyComparatorDecorator.compareNullFriendly(
					e1.age(), e2.age(),
					true,
					Comparator.<Integer>naturalOrder());
		}
	}

	static class ComparatorBySalary implements Comparator<Employee> {
		@Override
		public int compare(Employee e1, Employee e2) {

			// Поскольку Salary может быть не задан (null), используем утилитный
			// статический метод из декороатора чтобы обойти это:
			return NullFriendlyComparatorDecorator.compareNullFriendly(
					e1.getSalary(), e2.getSalary(),
					true,
					Comparator.<Integer>naturalOrder());
		}
	}

	static class NullFriendlyComparatorDecorator<T> implements Comparator<T> {

		private final boolean nullFirst;
		private final Comparator<T> baseComparator;

		/**
		 * Компаратор-обёртка, дружественный к null-значениям.
		 * 
		 * @param baseComparator Если не задать базовый компаратор, то любые не-null
		 *                       значения будут рассматриваться как эквивалентные.
		 * @param nullFirst
		 */
		public NullFriendlyComparatorDecorator(Comparator<T> baseComparator, boolean nullFirst) {
			this.baseComparator = baseComparator;
			this.nullFirst = nullFirst;
		}

		@Override
		public int compare(T o1, T o2) {

			return compareNullFriendly(o1, o2, nullFirst, baseComparator);
		}

		public static <T> int compareNullFriendly(T o1, T o2, boolean nullFirst, Comparator<T> baseComparator) {
			if (o1 == null) {
				return (o2 == null) ? 0 : (nullFirst ? -1 : 1);
			} else if (o2 == null) {
				return nullFirst ? 1 : -1;
			} else {
				return (baseComparator == null) ? 0 : baseComparator.compare(o1, o2);
			}
		}
	}
}
