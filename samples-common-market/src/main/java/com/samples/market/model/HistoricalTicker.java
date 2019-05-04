package com.samples.market.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class HistoricalTicker extends Ticker {
	@JsonAlias("PRICE_DATE")
	public String priceDate;

	public String getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(String priceDate) {
		this.priceDate = priceDate;
	}
}
