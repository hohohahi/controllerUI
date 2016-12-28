package com.bottle.common.exception;

public abstract class AbstractBaseRuntimeException extends RuntimeException implements IMyRuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String extraErrorMessage = "";
	
	public AbstractBaseRuntimeException(final String extraErrorMessage) {
		this.extraErrorMessage = extraErrorMessage;
	}

	final public String getExtraErrorMessage() {
		return extraErrorMessage;
	}
}
