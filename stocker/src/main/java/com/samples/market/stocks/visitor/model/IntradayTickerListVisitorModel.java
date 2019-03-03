package com.samples.market.stocks.visitor.model;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.converter.JsonToIntradayTickerList;
import com.samples.market.stocks.converter.interfaces.IConverter;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponse;

public class IntradayTickerListVisitorModel 
		extends JsonQuoteConvertibleResponse<IntradayTicker> {
	
	private JsonToIntradayTickerList converter;
	
	public IntradayTickerListVisitorModel() {
		this.converter = new JsonToIntradayTickerList();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public IConverter getConverter() {
		return converter;
	}	
}
