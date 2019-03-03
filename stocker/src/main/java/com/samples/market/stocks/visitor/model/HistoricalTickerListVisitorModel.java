package com.samples.market.stocks.visitor.model;

import com.samples.market.model.HistoricalTickerList;
import com.samples.market.stocks.converter.JsonToHistoricalTicker;
import com.samples.market.stocks.visitor.interfaces.IVisitor;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponse;

public class HistoricalTickerListVisitorModel 
		extends JsonQuoteConvertibleResponse<HistoricalTickerList> {
	
	private JsonToHistoricalTicker converter;
	
	public HistoricalTickerListVisitorModel() {
		converter = new JsonToHistoricalTicker();
	}

	@Override
	public JsonToHistoricalTicker getConverter(){
		return converter;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
