package com.bottle.hardware.rxtx.exception;

public class NotASerialPort extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotASerialPort() {}

	@Override
	public String toString() {
		return "The port is not serial port.";
	}
	
	
}
