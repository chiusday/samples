package com.samples.market.model;

public class HistoricalTickerList extends TickerList<HistoricalTicker> {
	//Use actual UID in real apps
	private static final long serialVersionUID = 1L;

	public HistoricalTickerList() {}
	
	public HistoricalTickerList(String symbol) {
		super(symbol);
	}
}
