package com.bottle.hardware.rxtx.exception;

public class SerialPortInputStreamCloseFailure extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerialPortInputStreamCloseFailure() {}

	@Override
	public String toString() {
		return "error happens when closing the port stream.";
	}
	
	
}
