package com.samples.market.model;

public class TickerRequestBySymbol {
	protected String symbol;

	public TickerRequestBySymbol() {}
	public TickerRequestBySymbol(String symbol) {this.symbol = symbol;}
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
