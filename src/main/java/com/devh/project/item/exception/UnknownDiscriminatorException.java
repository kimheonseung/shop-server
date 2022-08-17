package com.devh.project.item.exception;

public class UnknownDiscriminatorException extends Exception {
	private static final long serialVersionUID = -775103772084154536L;

	public UnknownDiscriminatorException(String message) {
        super(message);
    }
}
