package com.devh.project.common.exception;

public class SignUpException extends IllegalStateException {
	private static final long serialVersionUID = -7165186682626588013L;

	public SignUpException(String message) {
        super(message);
    }
}
