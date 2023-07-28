package edu.alexey.javacore.homeworks.hw5.enums;

public enum CopyStatus {

	SUCCESS("Успешно скопирован."),
	PASSED("Пропущен. Уже имеется в месте назначения."),
	SKIPPED("Пропущен. Не является регулярным файлом."),
	ERROR("Ошибка.");

	private final String name;

	public String getName() {
		return name;
	}

	private CopyStatus(String name) {
		this.name = name;
	}
}
