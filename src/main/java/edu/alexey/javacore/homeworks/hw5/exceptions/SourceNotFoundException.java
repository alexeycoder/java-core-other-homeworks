package edu.alexey.javacore.homeworks.hw5.exceptions;

import java.nio.file.FileSystemException;

public class SourceNotFoundException extends FileSystemException {

	public SourceNotFoundException(String file) {
		super(file);
	}
}
