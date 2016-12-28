package com.bottle.ui.components.admin.serialdebug;

import com.bottle.ui.components.common.BaseTableCandidate;

public class SerailDebugTableCandidate extends BaseTableCandidate
{
	private String timestamp = "";
	private String direction = "";
	private String pid = "00";
	private String aid = "00";
	private String bytesStr = "";

	public SerailDebugTableCandidate(final String timestamp, final String direction, final String pid, final String aid, final String bytesStr) {
		this.timestamp = timestamp;
		this.direction = direction;
		this.pid = pid;
		this.aid = aid;
		this.bytesStr = bytesStr; 
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getBytesStr() {
		return bytesStr;
	}
	public void setBytesStr(String bytesStr) {
		this.bytesStr = bytesStr;
	}

	
}
