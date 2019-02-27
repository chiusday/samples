package com.samples.market.stocks.converter.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.samples.market.model.Ticker;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;

import io.vertx.core.json.JsonObject;

public interface JsonToTickerList<T extends Ticker> 
		extends IConverter<JsonQuote<T>, List<T>> {

	void additionalFields(String symbol, JsonObject quote, Entry<String, Object> entry);

	@Override
	default List<T> convertFrom(JsonQuote<T> from) {
		List<T> tickers = new ArrayList<>();
		from.getData().forEach(entry -> {
			JsonObject quote = new JsonObject();
			JsonObject.mapFrom(entry.getValue()).getMap().entrySet().forEach(elem -> {
				from.getFields().forEach(field -> {
					if (elem.getKey().contains(field)) {
						quote.put(field, elem.getValue());
					}
				});
			});
			additionalFields(from.getSymbol(), quote, entry);
			tickers.add(quote.mapTo(from.getType()));
		});				
		
		return tickers;
	}
	
	@Override
	default JsonQuote<T> convertTo(List<T> to) {
		throw new UnsupportedOperationException("convertTo(List<T> is not supported");
	}
}
