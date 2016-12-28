package com.bottle.business.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.bottle.common.constants.ICommonConstants;

public class MessageVO {
	private long id = 0L;
	private String message = "";
	private long param1 = 0L;
	private long param2 = 0L;
	private boolean booleanParam3 = false;
	private List<Long> longListParameter4 = new ArrayList<Long>();
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
	public long getParam1() {
		return param1;
	}
	public void setParam1(long param1) {
		this.param1 = param1;
	}
	public long getParam2() {
		return param2;
	}
	public void setParam2(long param2) {
		this.param2 = param2;
	}
	public boolean getIsBooleanParam3() {
		return booleanParam3;
	}
	public void setBooleanParam3(boolean booleanParam3) {
		this.booleanParam3 = booleanParam3;
	}
	public List<Long> getLongListParameter4() {
		return longListParameter4;
	}
	public void setLongListParameter4(List<Long> longListParameter4) {
		this.longListParameter4 = longListParameter4;
	}
	@Override
	public String toString() {
		return "MessageVO [id=" + id + ", message=" + message + ", param1=" + param1 + ", param2=" + param2
				+ ", booleanParam3=" + booleanParam3 + ", messageSource=" + messageSource + ", subMessageType="
				+ subMessageType + "]";
	}
	
}
