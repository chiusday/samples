package com.samples.vertx.reactive.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Ticker extends TickerBase {
	private double open;
	private double close;
	private double high;
	private double low;
	
	@JsonProperty(access = Access.READ_ONLY)
	private double mid;
	
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

	public double getMid() {
		return (getLow() + getHigh()) / 2;
	}
}
