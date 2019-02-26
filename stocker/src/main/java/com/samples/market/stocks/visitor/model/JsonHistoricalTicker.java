package com.samples.market.stocks.visitor.model;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;

import io.vertx.core.json.JsonObject;

public class JsonHistoricalTicker extends JsonQuote<HistoricalTicker> {

	public JsonHistoricalTicker() {
		super(HistoricalTicker.class);
		this.fields.add("close");
	}
	
	public JsonHistoricalTicker(String symbol, JsonObject rawData) {
		this();
		this.symbol = symbol;
		this.data = rawData;
	}
}
