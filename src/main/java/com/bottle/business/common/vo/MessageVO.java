package com.bottle.business.common.vo;

import com.bottle.common.constants.ICommonConstants;

public class MessageVO {
	private long id = 0L;
	private String message = "";
	private ICommonConstants.MessageSourceEnum messageSource = ICommonConstants.MessageSourceEnum._MessageSource_None;
	private ICommonConstants.SubMessageTypeEnum subMessageType = ICommonConstants.SubMessageTypeEnum._SubMessageType_None_;
	
	public ICommonConstants.MessageSourceEnum getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ICommonConstants.MessageSourceEnum messageType) {
		this.messageSource = messageType;
	}
	public ICommonConstants.SubMessageTypeEnum getSubMessageType() {
		return subMessageType;
	}
	public void setSubMessageType(ICommonConstants.SubMessageTypeEnum subMessageType) {
		this.subMessageType = subMessageType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}		
}
