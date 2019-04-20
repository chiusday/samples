package com.samples.market.model;

public class IntradayTicker extends Ticker {
	private String priceTime;

	public String getPriceTime() {
		return priceTime;
	}

	public void setPriceTime(String time) {
		this.priceTime = time;
	}
}
