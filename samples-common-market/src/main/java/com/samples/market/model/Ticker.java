package com.samples.market.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Ticker extends TickerBase {
	@JsonAlias("ID")
	private int id;
	@JsonAlias("SYMBOL")
	protected String symbol;
	@JsonAlias("OPEN")
	protected double open;
	@JsonAlias("CLOSE")
	protected double close;
	@JsonAlias("HIGH")
	protected double high;
	@JsonAlias("LOW")
	protected double low;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
}
