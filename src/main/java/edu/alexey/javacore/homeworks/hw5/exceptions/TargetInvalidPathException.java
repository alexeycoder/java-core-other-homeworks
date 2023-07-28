package edu.alexey.javacore.homeworks.hw5.exceptions;

import java.nio.file.InvalidPathException;

public class TargetInvalidPathException extends InvalidPathException {

	public TargetInvalidPathException(String input, String reason) {
		super(input, reason);
	}
}
