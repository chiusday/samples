package com.samples.market.stocks.visitor.model;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;

import io.vertx.core.json.JsonObject;

public class AlphaVantageIntradayTicker extends JsonQuote<IntradayTicker> {

	public AlphaVantageIntradayTicker() {
		super(IntradayTicker.class);
		this.fields.add("close");
	}
	
	public AlphaVantageIntradayTicker(String symbol, JsonObject rawData) {
		this();
		this.symbol = symbol;
		this.data = rawData;
	}
}
