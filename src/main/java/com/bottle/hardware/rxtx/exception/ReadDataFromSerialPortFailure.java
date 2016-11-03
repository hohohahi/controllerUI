package com.bottle.hardware.rxtx.exception;

public class ReadDataFromSerialPortFailure extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReadDataFromSerialPortFailure() {}

	@Override
	public String toString() {
		return "error happens when read from serial port.";
	}
	
}
