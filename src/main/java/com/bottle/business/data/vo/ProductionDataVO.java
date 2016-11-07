package com.bottle.business.data.vo;

public class ProductionDataVO {
	private long errorCode = 0L;
	private String barCode = "";
	private String templateName = "";
	private double price = 0.0d;
	private String timestampStr = "";
	private boolean isSuccessful = false;
	
	public boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTimestampStr() {
		return timestampStr;
	}
	public void setTimestampStr(String timestampStr) {
		this.timestampStr = timestampStr;
	}
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "ProductionDataVO [errorCode=" + errorCode + ", barCode=" + barCode + ", templateName=" + templateName
				+ ", price=" + price + ", timestampStr=" + timestampStr + ", isSuccessful=" + isSuccessful + "]";
	}
}
