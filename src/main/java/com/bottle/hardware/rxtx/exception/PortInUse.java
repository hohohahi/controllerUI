package com.bottle.hardware.rxtx.exception;

public class PortInUse extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PortInUse() {}

	@Override
	public String toString() {
		return "Port is already in used.";
	}
	
}
