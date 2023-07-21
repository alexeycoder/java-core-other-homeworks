package edu.alexey.javacore.homeworks.hw4.exceptions;

public class IllegalAmountException extends Exception {

	public IllegalAmountException(Integer provided, Integer min, Integer max) {
		super(composeMessage(provided, min, max));
	}

	public IllegalAmountException() {
		super(composeMessage(null, null, null));
	}

	private static String composeMessage(Integer provided, Integer min, Integer max) {
		if (provided == null) {
			return "Указано недопустимое количество товара.";
		}

		StringBuilder sb = new StringBuilder("Указано недопустимое количество товара: ")
				.append(Integer.toString(provided)).append(".");

		boolean isMinGiven = isValidLimit(min);
		boolean isMaxGiven = isValidLimit(max);
		if (isMinGiven || isMaxGiven) {
			sb.append(" Количество должно быть ");
			if (isMinGiven) {
				sb.append("не менее ").append(Integer.toString(min));
				if (isMaxGiven) {
					sb.append(" и ");
				} else {
					sb.append(".");
				}
			}
			if (isMaxGiven) {
				sb.append("не более ").append(Integer.toString(max))
						.append(".");
			}
		}
		return sb.toString();
	}

	private static boolean isValidLimit(Integer value) {
		return value != null && !value.equals(Integer.MIN_VALUE) && !value.equals(Integer.MAX_VALUE);
	}

	// public static void main(String[] args) {
	// System.out.println(composeMessage(null, 10, 20));
	// System.out.println(composeMessage(5, 1, 20));
	// System.out.println(composeMessage(5, 1, null));
	// System.out.println(composeMessage(5, null, 20));
	// System.out.println(composeMessage(5, null, null));
	// }
}
