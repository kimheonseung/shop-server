package com.devh.project.cafe.exception;

public class CafeOrderServiceException extends RuntimeException {
	private static final long serialVersionUID = -887952913721772035L;

	public CafeOrderServiceException(String message) {
        super(message);
    }
}
