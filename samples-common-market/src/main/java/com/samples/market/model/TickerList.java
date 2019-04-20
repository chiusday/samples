package com.samples.market.model;

import java.util.ArrayList;

public class TickerList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 4037202515467050501L;
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
