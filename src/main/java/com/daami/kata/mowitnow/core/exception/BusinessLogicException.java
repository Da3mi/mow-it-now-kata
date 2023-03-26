package com.daami.kata.mowitnow.core.exception;

public class BusinessLogicException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BusinessLogicException(String message) {
		super(message);
	}

	public BusinessLogicException(String message, Throwable e) {
		super(message, e);
	}

}
