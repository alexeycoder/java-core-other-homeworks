package edu.alexey.javacore.homeworks.hw5.exceptions;

import java.nio.file.FileSystemException;

public class TargetUnableCreateException extends FileSystemException {

	public TargetUnableCreateException(String file) {
		super(file);
	}

	public TargetUnableCreateException(String file, Throwable cause) {
		super(file);
		this.initCause(cause);
	}

}
