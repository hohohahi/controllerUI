package com.bottle.hardware.rxtx.exception;

public class SendDataToSerialPortFailure extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SendDataToSerialPortFailure() {}

	@Override
	public String toString() {
		return "error happens when sending data to serial port.";
	}
	
}
