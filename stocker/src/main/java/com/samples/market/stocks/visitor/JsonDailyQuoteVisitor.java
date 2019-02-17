package com.samples.market.stocks.visitor;

import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.samples.market.stocks.visitor.interfaces.JsonQuoteVisitor;

import io.vertx.core.json.JsonObject;

@Component
public class JsonDailyQuoteVisitor implements JsonQuoteVisitor {

	@Override
	public void additionalFields(String symbol, JsonObject quote, 
			Entry<String, Object> entry) {
		
		quote.put("symbol", symbol);
		quote.put("priceDate", entry.getKey());
	}

}
