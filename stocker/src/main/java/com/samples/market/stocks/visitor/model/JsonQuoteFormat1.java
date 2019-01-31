package com.samples.market.stocks.visitor.model;

import io.vertx.core.json.JsonObject;

public class JsonQuoteFormat1 extends JsonQuote {

	public JsonQuoteFormat1() {
		super();
		this.fields.add("close");
	}
	
	public JsonQuoteFormat1(String symbol,JsonObject rawData) {
		this();
		this.symbol = symbol;
		this.data = rawData;
	}
}
