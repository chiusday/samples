package com.samples.market.model;

public class HistoricalTickerList extends TickerList<HistoricalTicker> {

	public HistoricalTickerList(String symbol) {
		super(symbol);
	}
	
	public HistoricalTickerList(TickerList<HistoricalTicker> tickerList) {
		this.symbol = tickerList.getSymbol();
		this.tickerList = tickerList.getTickerList();
	}
}
