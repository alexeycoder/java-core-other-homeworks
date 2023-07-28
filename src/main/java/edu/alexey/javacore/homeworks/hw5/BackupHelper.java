package edu.alexey.javacore.homeworks.hw5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

import edu.alexey.javacore.homeworks.hw5.enums.CopyStatus;
import edu.alexey.javacore.homeworks.hw5.exceptions.SourceInvalidPathException;
import edu.alexey.javacore.homeworks.hw5.exceptions.SourceNoAccessException;
import edu.alexey.javacore.homeworks.hw5.exceptions.SourceNotFoundException;
import edu.alexey.javacore.homeworks.hw5.exceptions.TargetInvalidPathException;
import edu.alexey.javacore.homeworks.hw5.exceptions.TargetNoAccessException;
import edu.alexey.javacore.homeworks.hw5.exceptions.TargetUnableCreateException;

public class BackupHelper {

	private final Path sourcePath;
	private final Path targetPath;
	private final boolean incremental;

	public Path getSourcePath() {
		return sourcePath;
	}

	public Path getTargetPath() {
		return targetPath;
	}

	public boolean isIncremental() {
		return incremental;
	}

	/**
	 * Утилита резервного копирования файлов из корневого уровня директории
	 * источника (т.е. с игнорированием поддиректорий), в директорию назначения.
	 * 
	 * @param sourcePath      Строковое представление пути к директории источнику.
	 * @param targetPath      Строковое представление пути к директории назначения.
	 * @param incrementalMode Инкрементальный режим если true. В данном режиме
	 *                        фактически копироваться будут только более новые по
	 *                        времени модификации
	 *                        файлы, либо файлы отстутсвующие в директории
	 *                        назначения.
	 * @throws SourceInvalidPathException Если строка пути к директории источнику
	 *                                    содержит недопустимые символы, или строка
	 *                                    пути недопустима по другим причинам,
	 *                                    зависящим от файловой системы.
	 * @throws TargetInvalidPathException Если строка пути к директории назначения
	 *                                    содержит недопустимые символы, или строка
	 *                                    пути недопустима по другим причинам,
	 *                                    зависящим от файловой системы.
	 */
	public BackupHelper(String sourcePath, String targetPath, boolean incrementalMode)
			throws SourceInvalidPathException, TargetInvalidPathException {

		this.incremental = incrementalMode;

		try {
			this.sourcePath = Paths.get(sourcePath);
		} catch (InvalidPathException e) {
			throw new SourceInvalidPathException(sourcePath, e.getReason());
		}

		try {
			this.targetPath = Paths.get(targetPath);
		} catch (InvalidPathException e) {
			throw new TargetInvalidPathException(targetPath, e.getReason());
		}

	}

	/**
	 * Выполняет резервное копирование.
	 * 
	 * @return Список с результатом резервного копирования по каждому из файлов.
	 * @throws SourceNotFoundException     Если директории источника не существует.
	 * @throws SourceNoAccessException     Если нет прав на чтение директории
	 *                                     источника.
	 * @throws TargetUnableCreateException Если директория назначения не существует,
	 *                                     а создать новую не удалось.
	 * @throws TargetNoAccessException     Если нет прав на запись в директорию
	 *                                     назначения.
	 */
	public List<FileBackupResult> backup()
			throws SourceNotFoundException,
			SourceNoAccessException,
			TargetUnableCreateException,
			TargetNoAccessException {

		if (!Files.isDirectory(sourcePath)) {
			throw new SourceNotFoundException(sourcePath.toString());
		}
		if (!Files.isReadable(sourcePath)) {
			throw new SourceNoAccessException(sourcePath.toString());
		}
		if (!Files.isDirectory(targetPath)) {
			try {
				Files.createDirectory(targetPath);
			} catch (Exception e) {
				throw new TargetUnableCreateException(targetPath.toString(), e);
			}
			if (!Files.isDirectory(targetPath)) {
				throw new TargetUnableCreateException(targetPath.toString());
			}
		}
		if (!Files.isWritable(targetPath)) {
			throw new TargetNoAccessException(targetPath.toString());
		}

		try (Stream<Path> filePathStream = Files.list(sourcePath)) {

			return filePathStream.filter(f -> !Files.isDirectory(f))
					.map(f -> backupFile(f)).toList();

		} catch (Exception e) {
			throw new SourceNoAccessException(sourcePath.toString());
		}
	}

	private FileBackupResult backupFile(Path filePath) {

		// на случай реализации копирования включая содержимое поддиректорий
		// для логов берём не просто имя файла, а относительный путь
		// (вернёт просто имя файла для файла, находящегося непосредственно
		// в директории-источнике):
		String shortFilePathStr = sourcePath.relativize(filePath).toString();

		if (!Files.isRegularFile(filePath)) {
			return new FileBackupResult(shortFilePathStr, CopyStatus.SKIPPED, null);
		}
		if (!Files.isReadable(filePath)) {
			return new FileBackupResult(
					shortFilePathStr,
					CopyStatus.ERROR,
					"Недостаточно прав для чтения файла.");
		}

		String fileName = filePath.getFileName().toString();

		try {
			Path destFilePath = Paths.get(targetPath.toString(), fileName);

			if (Files.isDirectory(destFilePath)) {
				return new FileBackupResult(
						shortFilePathStr,
						CopyStatus.ERROR,
						"В месте назначения существует директория с тем же именем.");
			}

			boolean doCopy = true;
			if (incremental) {
				// в инкрементальном режиме (цель -- уменьшить количество
				// лишних операций) отменяем копирование, если:
				// в месте назначения существует файл с тем же именем
				// И ( он новее файла в источнике
				// ЛИБО ( его дата модификации та же И размер тот же ) )
				if (Files.exists(destFilePath)) {
					var srcModTime = Files.getLastModifiedTime(filePath);
					var destModTime = Files.getLastModifiedTime(destFilePath);
					int cmp = destModTime.compareTo(srcModTime);
					if (cmp > 0
							|| (cmp == 0 && Files.size(destFilePath) == Files.size(filePath))) {
						doCopy = false;
					}
				}
			}

			if (doCopy) {
				Files.copy(filePath,
						destFilePath,
						StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES);
				Files.setLastModifiedTime(destFilePath, Files.getLastModifiedTime(filePath));
				return new FileBackupResult(shortFilePathStr, CopyStatus.SUCCESS, null);
			} else {
				return new FileBackupResult(shortFilePathStr, CopyStatus.PASSED, null);
			}

		} catch (IOException e) {
			return new FileBackupResult(shortFilePathStr, CopyStatus.ERROR, e.getLocalizedMessage());
		}
	}
}
