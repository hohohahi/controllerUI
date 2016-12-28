package com.bottle.common.exception;

public interface IMyRuntimeException {
	String getExtraErrorMessage();
	IExceptionConstants.ExceptionTypeEnum getExceptionType();
}
