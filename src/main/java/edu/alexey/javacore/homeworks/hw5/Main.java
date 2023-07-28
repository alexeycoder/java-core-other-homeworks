package edu.alexey.javacore.homeworks.hw5;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import edu.alexey.javacore.homeworks.hw5.enums.CopyStatus;
import edu.alexey.javacore.homeworks.hw5.exceptions.SourceInvalidPathException;
import edu.alexey.javacore.homeworks.hw5.exceptions.SourceNoAccessException;
import edu.alexey.javacore.homeworks.hw5.exceptions.SourceNotFoundException;
import edu.alexey.javacore.homeworks.hw5.exceptions.TargetInvalidPathException;
import edu.alexey.javacore.homeworks.hw5.exceptions.TargetNoAccessException;
import edu.alexey.javacore.homeworks.hw5.exceptions.TargetUnableCreateException;
import edu.alexey.utils.ConsoleUtils;
import edu.alexey.utils.UncloseableInputStream;

public class Main {

	static final String BACKUP_DEST_PATH = "./backup";

	static final Locale LOCALE = Locale.forLanguageTag("RU");
	static final Charset CHARSET = Charset.defaultCharset();
	static final Scanner inputScanner = new Scanner(UncloseableInputStream.wrap(System.in), CHARSET);

	public static void main(String[] args) {

		printEmphasized("\nРезервное копирование директории в " + BACKUP_DEST_PATH);

		BackupHelper backupHelper = null;

		while (true) {
			System.out.println("\nВведите путь к директории-источнику (или пустой ввод чтобы выйти):");
			var rawInp = inputScanner.nextLine();
			if (rawInp == null || rawInp.isBlank()) {
				System.out.println("Отмена. Вы завершили приложение.");
				return;
			}

			try {
				backupHelper = new BackupHelper(rawInp, BACKUP_DEST_PATH, true);
				break;

			} catch (SourceInvalidPathException e) {
				printError("Путь к директории источнику задан некорректно. " + ConsoleUtils.PLEASE_REPEAT);
			} catch (TargetInvalidPathException e) {
				printError("Путь назначения для резервного копирования"
						+ " задан некорректно в настройках приложения."
						+ "\nДальнейшая работа невозможна.");
				printExceptionDetails(e);
				return;
			}
		}

		List<FileBackupResult> results = null;

		try {
			results = backupHelper.backup();

		} catch (SourceNoAccessException e) {
			printError("Ошибка при инициализации копирования:"
					+ " У вас нет прав не чтение директории источника.");
			printExceptionDetails(e);
			return;

		} catch (TargetNoAccessException e) {
			printError("Ошибка при инициализации копирования:"
					+ " У вас нет прав на запись в директорию назначения.");
			printExceptionDetails(e);
			return;

		} catch (SourceNotFoundException e) {
			printError("Ошибка при инициализации копирования:"
					+ " Указанной директоиии источника не существует.");
			printExceptionDetails(e);
			return;

		} catch (TargetUnableCreateException e) {
			printError("Ошибка при инициализации копирования:"
					+ " Не удалось создать директорию назначения.");
			printExceptionDetails(e);
			return;
		}

		assert results != null : "results list is unexpectedly null";

		if (results.isEmpty()) {
			System.out.println("\nНечего копировать.");
			return;
		}

		System.out.printf("\nРезервное копирование из '%s' в '%s' завершено.\n\n",
				backupHelper.getSourcePath(),
				backupHelper.getTargetPath());

		printEmphasized("Статистика:");

		printEmphasized("\nФайлы, пропущенные из-за ошибки:\n");

		var errResults = results.stream().filter(r -> r.copyStatus().equals(CopyStatus.ERROR)).toList();
		if (errResults.isEmpty()) {
			System.out.println("нет");
		} else {
			errResults
					.forEach(r -> System.out.printf("%s\t%s\n", r.filePath(), r.details() != null ? r.details() : ""));
		}

		printEmphasized("\nФайлы, пропущенные, как уже имеющиеся в директории назначения:\n");
		var passedResults = results.stream().filter(r -> r.copyStatus().equals(CopyStatus.PASSED)).toList();
		if (passedResults.isEmpty()) {
			System.out.println("нет");
		} else {
			passedResults.forEach(r -> System.out.println(r.filePath()));
		}

		printEmphasized("\nПропущенные нерегулярные файлы (сокеты, блочные или символьные устройства):\n");
		var skippedResults = results.stream().filter(r -> r.copyStatus().equals(CopyStatus.SKIPPED)).toList();
		if (skippedResults.isEmpty()) {
			System.out.println("нет");
		} else {
			skippedResults.forEach(r -> System.out.println(r.filePath()));
		}

		printEmphasized("\nУспешно скопированные файлы:\n");
		var successResults = results.stream().filter(r -> r.copyStatus().equals(CopyStatus.SUCCESS)).toList();
		if (successResults.isEmpty()) {
			System.out.println("нет");
		} else {
			successResults.forEach(r -> System.out.println(r.filePath()));
		}

		System.out.println();
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
