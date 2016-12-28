package com.bottle.ui.components.player.sub;

import com.bottle.ui.components.common.BaseTableCandidate;

public class RealCheckResultTableCandidate extends BaseTableCandidate
{
	private long id = 0L;
	private String timestamp = "";
	private String name = "";
	private String barCode = "";
	private double price = 0.0d;
	
	public RealCheckResultTableCandidate(final long id, final String timestamp, final String name, final String barCode, final double price) {
		this.id = id;
		this.timestamp = timestamp;
		this.name = name;
		this.barCode = barCode;
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "RealCheckResultTableCandidate [id=" + id + ", timestamp=" + timestamp + ", name=" + name + ", barCode="
				+ barCode + ", price=" + price + "]";
	}
}
