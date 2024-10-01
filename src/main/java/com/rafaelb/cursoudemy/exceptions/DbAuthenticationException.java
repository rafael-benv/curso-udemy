package com.rafaelb.cursoudemy.exceptions;

public class DbAuthenticationException extends RuntimeException{
	public DbAuthenticationException(String msg) {
		super(msg);
	}
}
