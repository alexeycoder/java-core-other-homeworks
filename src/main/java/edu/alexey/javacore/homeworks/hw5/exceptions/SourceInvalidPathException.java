package edu.alexey.javacore.homeworks.hw5.exceptions;

import java.nio.file.InvalidPathException;

public class SourceInvalidPathException extends InvalidPathException {

	public SourceInvalidPathException(String input, String reason) {
		super(input, reason);
	}
}
