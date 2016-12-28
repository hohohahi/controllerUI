package com.bottle.common.exception;

public interface IExceptionConstants {
	enum ExceptionTypeEnum {
		_ExceptionType_None(0L, "ok"),
		_ExceptionType_NotSupportedIsMetalType(1L, "the isMetal type is not supported"),
		_ExceptionType_DataLenghNotEqualActualLength(1L, "the data-length value is not equal the actual length of data.");
		 
		private long id = 0L;
		private String message = "";
		
		ExceptionTypeEnum(final long id, final String message) {
			this.id = id;
			this.message = message;
		}

		public long getId() {
			return id;
		}

		public String getMessage() {
			return message;
		}
	}
}
