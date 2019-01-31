package com.samples.market.stocks.visitor.interfaces;

import java.util.Map.Entry;

import com.samples.market.models.HistoricalTicker;
import com.samples.market.stocks.visitor.interfaces.IVisitor;
import com.samples.market.stocks.visitor.model.JsonQuote;

import io.vertx.core.json.JsonObject;

public interface JsonQuoteVisitor extends IVisitor {
	
	void additionalFields(JsonObject quote, Entry<String, Object> entry);
	
	default public void visit(JsonQuote json) {
		json.getData().forEach(entry -> {
			JsonObject quote = new JsonObject();
			JsonObject.mapFrom(entry.getValue()).getMap().entrySet().forEach(elem -> {
				json.getFields().forEach(field -> {
					if (elem.getKey().contains(field)) {
						quote.put(field, elem.getValue());
					}
				});
			});
			additionalFields(quote, entry);
			json.getHistoricalQuotes().add(quote.mapTo(HistoricalTicker.class));
		});		
	}
}
