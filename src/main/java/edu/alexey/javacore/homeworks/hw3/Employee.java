package edu.alexey.javacore.homeworks.hw3;

import java.time.LocalDate;

public class Employee {

	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate birthDate;
	private String phone;
	private String position;
	private Integer salary;

	public Employee(String firstName,
			String middleName,
			String lastName,
			LocalDate birthDate,
			String phone,
			String position,
			Integer salary) {

		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;

		this.phone = phone;

		this.position = position;
		this.salary = salary;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	// evaluated

	public Integer age() {
		var todayDate = LocalDate.now();
		if (birthDate == null || birthDate.isAfter(todayDate)) {
			return null;
		}

		return birthDate.until(LocalDate.now()).getYears();
	}

	//

	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ String.format("[firstName=%s, middleName=%s, lastName=%s,"
						+ " birthDate=%s (age=%d), phone=%s,"
						+ " position=%s, salary=%d]",
						firstName, middleName, lastName,
						birthDate, age(), phone,
						position, salary);
	}

	private String nullFriendly(Object o) {
		if (o == null) {
			return "не задано";
		}
		return o.toString();
	}

	protected String formattedInfoBase(String title, String indent) {
		StringBuilder sb = new StringBuilder(title).append(System.lineSeparator())
				.append(indent).append("Фамилия: ").append(nullFriendly(lastName)).append(System.lineSeparator())
				.append(indent).append("Имя: ").append(nullFriendly(firstName)).append(System.lineSeparator())
				.append(indent).append("Отчество: ").append(nullFriendly(middleName)).append(System.lineSeparator())
				.append(indent).append("Возраст: ").append(nullFriendly(age())).append(System.lineSeparator())
				.append(indent).append("Тел.: ").append(nullFriendly(phone)).append(System.lineSeparator())
				.append(indent).append("Должность: ").append(nullFriendly(position)).append(System.lineSeparator())
				.append(indent).append("Оклад: ").append(nullFriendly(salary)).append(System.lineSeparator());
		return sb.toString();
	}

	public String formattedInfo() {
		return formattedInfoBase("Работник", "\t");
	}

	public void printInfo() {
		System.out.println(formattedInfo());
	}

}
