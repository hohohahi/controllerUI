package com.bottle.hardware.rxtx.exception;

public class SerialPortOutputStreamCloseFailure extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerialPortOutputStreamCloseFailure() {}

	@Override
	public String toString() {
		return "error happens when closing the output stream of port.";
	}
}
