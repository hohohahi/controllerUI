package com.bottle.business.template.vo;

import java.util.Arrays;

public class TemplateVO {
	private long id = 0L;
	private String name = "";
	private String barCode = "";
	private double price = 0d;
	private double weight = 0d;
	private byte [] outlineData = new byte[0];
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public byte[] getOutlineData() {
		return outlineData;
	}
	public void setOutlineData(byte[] outlineData) {
		this.outlineData = outlineData;
	}
	@Override
	public String toString() {
		return "TemplateVO [id=" + id + ", barCode=" + barCode + ", price=" + price + ", weight=" + weight
				+ ", outlineData=" + Arrays.toString(outlineData) + ", getId()=" + getId() + ", getBarCode()="
				+ getBarCode() + ", getPrice()=" + getPrice() + ", getWeight()=" + getWeight() + ", getOutlineData()="
				+ Arrays.toString(getOutlineData()) + "]";
	}
}
