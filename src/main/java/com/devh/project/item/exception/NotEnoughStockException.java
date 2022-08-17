package com.devh.project.item.exception;

public class NotEnoughStockException extends Exception {
	private static final long serialVersionUID = -4211006149455534082L;

	public NotEnoughStockException() {
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
