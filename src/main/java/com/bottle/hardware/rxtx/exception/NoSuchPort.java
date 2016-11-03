package com.bottle.hardware.rxtx.exception;

public class NoSuchPort extends Exception {
	private static final long serialVersionUID = 1L;

	public NoSuchPort() {}

	@Override
	public String toString() {
		return "Can not find such port.";
	}
	
}
