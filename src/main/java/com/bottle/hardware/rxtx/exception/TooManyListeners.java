package com.bottle.hardware.rxtx.exception;

public class TooManyListeners extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooManyListeners() {}

	@Override
	public String toString() {
		return "too many ports are in listening.";
	}
	
}
