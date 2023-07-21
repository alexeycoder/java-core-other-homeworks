## Урок 4. Обработка исключений

### Задача

1. В класс Товаров добавить перечисление с категориями товаров, добавить в Товар
поле категория со значением созданного перечисления. Добавить геттеры, сеттеры.
2. Добавить перечисление с размерами скидок - 0, 5, 10, 15, 20%. Написать метод,
при вызове которого на переданную категорию товара незначается рандомная скидка
из перечисления. Добавить в заказ поле стоимость и пересчитать стоимость
согласно сгенерированным скидкам.
3. Если сумма величин скидок на товары из заказа получилась больше 50%,
выбросить исключение TooMuchSaleException(); То есть нужно сложить величины
скидок на категории и проверить больше 50 или нет.

*Исходная задача:*
Эмуляция интернет-магазина

### Решение

Перечисление с категориями товаров &mdash; […/hw4/enums/Category.java](src/main/java/edu/alexey/javacore/homeworks/hw4/enums/Category.java)

Перечисление с размерами скидок &mdash; […/hw4/enums/Discount.java](src/main/java/edu/alexey/javacore/homeworks/hw4/enums/Discount.java)

Метод рандомного назначения скидки на категорию &mdash; `generateDiscountByCategory()`
в […/hw4/DiscountHelper.java](src/main/java/edu/alexey/javacore/homeworks/hw4/DiscountHelper.java)

Типы кастомных исключений, включая `TooMuchSaleException` &mdash; […/hw4/exceptions/*](src/main/java/edu/alexey/javacore/homeworks/hw4/exceptions)

Поскольку условие подразумевает, что один заказ может иметь более одной позиции,
в том числе из разных категорий товаров, обработка заказа реализована как
фабрика заказов &mdash;
[…/hw4/OrderComposerImpl.java](src/main/java/edu/alexey/javacore/homeworks/hw4/OrderComposerImpl.java),
предоставляемая экземпляром магазина
[…/hw4/Store.java](src/main/java/edu/alexey/javacore/homeworks/hw4/Store.java).

### Пример

	mvn exec:java



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
