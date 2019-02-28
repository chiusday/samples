package com.samples.market.model;

import java.util.ArrayList;
import java.util.List;

public class TickerList<T> {
	protected String symbol;
	protected List<T> tickerList = new ArrayList<T>();
	
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

	public List<T> getTickerList() {
		return tickerList;
	}

	public void setTickerList(List<T> tickerList) {
		this.tickerList = tickerList;
	}
	
}
