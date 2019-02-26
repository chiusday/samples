package com.samples.market.model;

import com.samples.market.model.interfaces.TickerList;

public class HistoricalTickerList extends TickerList<HistoricalTicker> {
	public HistoricalTickerList() {}
	
	public HistoricalTickerList(String symbol) {
		super(symbol);
	}
}
