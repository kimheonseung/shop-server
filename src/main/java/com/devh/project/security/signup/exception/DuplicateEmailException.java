package com.devh.project.security.signup.exception;

public class DuplicateEmailException extends IllegalStateException {
	private static final long serialVersionUID = 715362994553798061L;

	public DuplicateEmailException(String message) {
        super(message);
    }
}
