package com.bottle.business.common.vo;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.SerialCommandOperationDirectionEnum;

//src input array -> content  -> data
public class SerialCommandOperationVO extends OperationVO {
	private SerialCommandOperationDirectionEnum direction = SerialCommandOperationDirectionEnum._Operation_SerialCommand_Direction_Up_;
	private byte [] srcInputArray = new byte[0];
	private byte pid = ICommonConstants._Zero_Byte_;
	private byte aid = ICommonConstants._Zero_Byte_;
	private byte [] data = new byte[0];
	private int dataLength = 0;
	
	public SerialCommandOperationDirectionEnum getDirection() {
		return direction;
	}
	public void setDirection(SerialCommandOperationDirectionEnum direction) {
		this.direction = direction;
	}
	public byte[] getSrcInputArray() {
		return srcInputArray;
	}
	public void setSrcInputArray(byte[] srcInputArray) {
		this.srcInputArray = srcInputArray;
	}
	public byte getPid() {
		return pid;
	}
	public void setPid(byte pid) {
		this.pid = pid;
	}
	public byte getAid() {
		return aid;
	}
	public void setAid(byte aid) {
		this.aid = aid;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
}
