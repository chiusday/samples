package com.samples.market.model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class TickerList<E> extends ArrayList<E> {
	protected String symbol;
	
	public TickerList() {}
	
	public TickerList(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
