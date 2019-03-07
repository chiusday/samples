package com.samples.market.model;

public class IntradayTickerList extends TickerList<IntradayTicker> {

	public IntradayTickerList(String symbol) {
		super.setSymbol(symbol);
	}

	public IntradayTickerList(TickerList<IntradayTicker> tickerList) {
		this.symbol = tickerList.getSymbol();
		this.tickerList = tickerList.getTickerList();
	}
}
