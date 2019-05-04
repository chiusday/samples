package com.samples.market.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public class IntradayTicker extends Ticker {
	@JsonAlias("PRICE_TIME")
	private String priceTime;

	public String getPriceTime() {
		return priceTime;
	}

	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	public void setPriceTime(String time) {
		this.priceTime = time;
	}
}
