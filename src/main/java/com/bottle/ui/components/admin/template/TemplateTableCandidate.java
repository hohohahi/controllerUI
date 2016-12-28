package com.bottle.ui.components.admin.template;

import com.bottle.ui.components.common.BaseTableCandidate;

public class TemplateTableCandidate extends BaseTableCandidate
{
	private long id = 0L;
	private String name = "";
	private String barCode = "";
	private long isMetal = 0L;
	private long weight = 0L;
	private double price = 0.0d;
	private String imageCharacteristic = "";
	
	public TemplateTableCandidate(final long id, final String name, final String barCode, final long isMetal, final long weight, final double price) {
		this.id = id;
		this.name = name;
		this.barCode = barCode;
		this.isMetal = isMetal;
		this.weight = weight;
		this.price = price;
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

	public long getIsMetal() {
		return isMetal;
	}

	public void setIsMetal(long isMetal) {
		this.isMetal = isMetal;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageCharacteristic() {
		return imageCharacteristic;
	}

	public void setImageCharacteristic(String imageCharacteristic) {
		this.imageCharacteristic = imageCharacteristic;
	}

	@Override
	public String toString() {
		return "TemplateTableCandidate [id=" + id + ", name=" + name + ", barCode=" + barCode + ", isMetal=" + isMetal
				+ ", weight=" + weight + ", price=" + price + ", imageCharacteristic=" + imageCharacteristic + "]";
	}
}
