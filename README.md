## Урок 3. Классы и объекты

### Задача 1

Опишите класс руководителя, наследник от сотрудника. Перенесите
статический метод повышения зарплаты в класс руководителя, модифицируйте
метод таким образом, чтобы он мог поднять заработную плату всем, кроме
руководителей.\
В основной программе создайте руководителя и поместите его
в общий массив сотрудников. Повысьте зарплату всем сотрудникам и проследите,
чтобы зарплата руководителя не повысилась.


### Решение

См. статический метод `increaseOrdinaryEployeesSalary(…)` в классе руководителя
&mdash; […/hw3/Chief.java](src/main/java/edu/alexey/javacore/homeworks/hw3/Chief.java)

Основная программа &mdash; […/hw3/Main.java](src/main/java/edu/alexey/javacore/homeworks/hw3/Main.java)

Команды для запуска (предварительно установить корневой каталог проекта в качестве рабочей директории):

	mvn exec:java

или

	javac -sourcepath src/main/java/ -d out src/main/java/edu/alexey/javacore/homeworks/hw3/Main.java
	java -classpath out edu.alexey.javacore.homeworks.hw3.Main

### Задача 2

Написать 2 класса компараторов по возрасту и зарплате (implements Comparator)

### Решение

См. вложенные классы `ComparatorByAge` и `ComparatorBySalary` в фабрике
компараторов `EmployeeComparators` &mdash;
[…/hw3/EmployeeComparators.java](src/main/java/edu/alexey/javacore/homeworks/hw3/EmployeeComparators.java)

### Пример

![hw3-ex1](https://github.com/alexeycoder/java-core-other-homeworks/assets/109767480/c6d3acf0-d8eb-406b-b095-e20bf2f58881)

![hw3-ex2](https://github.com/alexeycoder/java-core-other-homeworks/assets/109767480/0645dcdd-f0b8-4108-ae17-fff3eb50aa5e)
