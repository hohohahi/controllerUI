package com.bottle.hardware.rxtx.vo;

import java.util.Arrays;

import com.bottle.common.constants.ICommonConstants;

public class ByteArrayParseResultVO {
	private byte pid = ICommonConstants._Zero_Byte_;
	private byte aid = ICommonConstants._Zero_Byte_;
	private int dataLength = 0;
	private byte [] dataArray = new byte[0];
	private byte [] contentArray = new byte[0];
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
	
	public byte[] getDataArray() {
		return dataArray;
	}
	public void setDataArray(byte[] dataArray) {
		this.dataArray = dataArray;
	}
	
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	
	public byte[] getContentArray() {
		return contentArray;
	}
	public void setContentArray(byte[] contentArray) {
		this.contentArray = contentArray;
	}
	@Override
	public String toString() {
		return "ByteArrayParseResultVO [pid=" + pid + ", aid=" + aid + ", dataLength=" + dataLength + ", dataArray="
				+ Arrays.toString(dataArray) + "]";
	}

}
