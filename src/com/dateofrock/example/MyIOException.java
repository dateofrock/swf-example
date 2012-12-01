package com.dateofrock.example;

public class MyIOException extends Exception {

	private static final long serialVersionUID = -1282253389745735905L;

	public MyIOException(String message, Throwable e) {
		super(message, e);
	}

}
