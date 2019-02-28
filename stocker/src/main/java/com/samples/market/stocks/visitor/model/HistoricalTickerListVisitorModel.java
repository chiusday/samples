package com.samples.market.stocks.visitor.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.HistoricalTickerList;
import com.samples.market.stocks.converter.JsonToHistoricalTicker;
import com.samples.market.stocks.visitor.interfaces.ConvertibleResponse;
import com.samples.market.stocks.visitor.interfaces.IVisitor;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;

public class HistoricalTickerListVisitorModel 
		extends ConvertibleResponse<HistoricalTickerList> {
	
	@Autowired
	private JsonToHistoricalTicker converter;

	@Override
	public JsonToHistoricalTicker getConverter(){
		return converter;
	}
}
