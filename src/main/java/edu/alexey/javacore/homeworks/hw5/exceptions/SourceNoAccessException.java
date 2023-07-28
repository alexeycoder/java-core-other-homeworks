package edu.alexey.javacore.homeworks.hw5.exceptions;

import java.nio.file.AccessDeniedException;

public class SourceNoAccessException extends AccessDeniedException {

	public SourceNoAccessException(String file) {
		super(file);
	}
}
