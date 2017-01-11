package com.bottle.ui.components.player.sub;

import com.bottle.ui.components.common.BaseTableCandidate;

public class RealCheckResultTableCandidate extends BaseTableCandidate
{
	private long id = 0L;
	private String name = "";
	private long errorCode = 0L;
	private double price = 0.0d;
	
	public RealCheckResultTableCandidate(final long id, final String name, final long errorCode, final double price) {
		this.id = id;
		this.name = name;
		this.errorCode = errorCode;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
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

	@Override
	public String toString() {
		return "RealCheckResultTableCandidate [id=" + id +  ", name=" + name + ", errorCode="
				+ errorCode + ", price=" + price + "]";
	}
}
