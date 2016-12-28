package com.bottle.hardware.rxtx.vo;

import java.util.ArrayList;
import java.util.List;

import com.bottle.common.constants.ICommonConstants;

public class RxTxResponseVO {
	private ICommonConstants.MachineCommandEnum commandType = ICommonConstants.MachineCommandEnum._MachineCommand_None_;
	private boolean isSuccess = false;
	private long errorCode = 0L;
	private String errorMessage = "";
	private String response = "";
	private long param1 = 0L;
	private List<Long>param2List = new ArrayList<Long>();
	private ByteArrayParseResultVO byteParseResultVO = new ByteArrayParseResultVO();
	
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return commandType;
	}
	public void setCommandType(ICommonConstants.MachineCommandEnum commandType) {
		this.commandType = commandType;
	}
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public long getParam1() {
		return param1;
	}
	public void setParam1(long param1) {
		this.param1 = param1;
	}
	public ByteArrayParseResultVO getByteParseResultVO() {
		return byteParseResultVO;
	}
	public void setByteParseResultVO(ByteArrayParseResultVO byteParseResultVO) {
		this.byteParseResultVO = byteParseResultVO;
	}
	
	public List<Long> getParam2List() {
		return param2List;
	}
	public void setParam2List(List<Long> param2List) {
		this.param2List = param2List;
	}
	@Override
	public String toString() {
		return "RxTxResponseVO [commandType=" + commandType + ", isSuccess=" + isSuccess + ", errorCode=" + errorCode
				+ ", errorMessage=" + errorMessage + ", response=" + response + ", param1=" + param1
				+ ", byteParseResultVO=" + byteParseResultVO + "]";
	}
}
