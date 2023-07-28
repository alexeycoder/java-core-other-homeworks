package edu.alexey.javacore.homeworks.hw5.exceptions;

import java.nio.file.AccessDeniedException;

public class TargetNoAccessException extends AccessDeniedException {

	public TargetNoAccessException(String file) {
		super(file);
	}
}
