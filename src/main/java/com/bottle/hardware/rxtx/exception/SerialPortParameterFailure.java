package com.bottle.hardware.rxtx.exception;

public class SerialPortParameterFailure extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerialPortParameterFailure() {}

	@Override
	public String toString() {
		return "error happens when setting port.";
	}
	
}
