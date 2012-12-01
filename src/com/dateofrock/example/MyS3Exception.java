package com.dateofrock.example;

public class MyS3Exception extends Exception {

	private static final long serialVersionUID = 3547726729798929464L;

	public MyS3Exception(String message, Throwable e) {
		super(message, e);
	}

}
