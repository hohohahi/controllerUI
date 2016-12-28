package com.bottle.business.template.exception;

import com.bottle.common.exception.AbstractBaseRuntimeException;
import com.bottle.common.exception.IExceptionConstants.ExceptionTypeEnum;

public class NotSupportIsMetalTypeException extends AbstractBaseRuntimeException {
	private static final long serialVersionUID = 1L;

	public NotSupportIsMetalTypeException(String extraErrorMessage) {
		super(extraErrorMessage);
	}

	@Override
	public ExceptionTypeEnum getExceptionType() {
		return ExceptionTypeEnum._ExceptionType_NotSupportedIsMetalType;
	}
}
