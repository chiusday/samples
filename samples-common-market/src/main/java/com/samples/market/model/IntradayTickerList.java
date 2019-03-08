package com.samples.market.model;

public class IntradayTickerList extends TickerList<IntradayTicker> {
	//Use actual UID in real apps
	private static final long serialVersionUID = 1L;

	public IntradayTickerList() {}
	
	public IntradayTickerList(String symbol) {
		super.setSymbol(symbol);
	}
}
