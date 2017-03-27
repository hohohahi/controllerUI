package com.bottle.ui.components.player.sub;

import com.bottle.ui.components.common.BaseTableCandidate;

public class RealCheckResultTableCandidate extends BaseTableCandidate
{
	private String name = "";
	private double price = 0.0d;
	private String barCode = "";
	
	public RealCheckResultTableCandidate(final String name, final String barCode, final double price) {
		this.name = name;
		this.price = price;
		this.barCode = barCode;
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Override
	public String toString() {
		return "RealCheckResultTableCandidate [name=" + name + ", price=" + price + "]";
	}
}
