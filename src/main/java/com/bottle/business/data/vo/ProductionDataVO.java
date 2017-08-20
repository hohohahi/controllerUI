package com.bottle.business.data.vo;

public class ProductionDataVO {	
	private long errorCode = 0L;
	private long templateId = 0L;
	private String barCode = "";
	private String templateName = "";
	private double price = 0.0d;
	private long weight = 0L;
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
	
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
	}
	
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	@Override
	public String toString() {
		return "ProductionDataVO [errorCode=" + errorCode + ", templateId=" + templateId + ", barCode=" + barCode
				+ ", templateName=" + templateName + ", price=" + price + ", weight=" + weight + ", timestampStr="
				+ timestampStr + ", isSuccessful=" + isSuccessful + "]";
	}
}
