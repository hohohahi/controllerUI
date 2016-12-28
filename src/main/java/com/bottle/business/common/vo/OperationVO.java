package com.bottle.business.common.vo;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.OperationTypeEnum;

public abstract class OperationVO {
	private String timestampStr = "";
	private ICommonConstants.OperationTypeEnum type = OperationTypeEnum._Operation_Type_None_;
	private boolean isSuccess = false;
	private long errorCode = 0L;
	private String errorMessage = "";
	private long spentTime = 0L;
	
	public String getTimestampStr() {
		return timestampStr;
	}
	public void setTimestampStr(String timestampStr) {
		this.timestampStr = timestampStr;
	}
	public ICommonConstants.OperationTypeEnum getType() {
		return type;
	}
	public void setType(ICommonConstants.OperationTypeEnum type) {
		this.type = type;
	}
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public long getSpentTime() {
		return spentTime;
	}
	public void setSpentTime(long spentTime) {
		this.spentTime = spentTime;
	}
	
	@Override
	public String toString() {
		return "OperationVO [timestampStr=" + timestampStr + ", type=" + type + ", isSuccess=" + isSuccess
				+ ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", spentTime=" + spentTime + "]";
	}
}
