package com.douglas.os.domain.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2083676090616756547L;

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ObjectNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
