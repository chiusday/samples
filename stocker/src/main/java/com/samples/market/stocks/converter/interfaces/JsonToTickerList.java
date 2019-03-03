package com.samples.market.stocks.converter.interfaces;

import java.util.Map.Entry;

import com.samples.market.model.Ticker;
import com.samples.market.model.TickerList;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;

import io.vertx.core.json.JsonObject;

public interface JsonToTickerList<T extends Ticker> 
		extends IConverter<JsonQuote<T>, TickerList<T>> {

	void additionalFields(String symbol, JsonObject quote, Entry<String, Object> entry);

	@Override
	default TickerList<T> convertFrom(JsonQuote<T> from) {
		TickerList<T> tickers = new TickerList<>(from.getSymbol());
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
			tickers.getTickerList().add(quote.mapTo(from.getType()));
		});	
		
		return tickers;
	}
	
	@Override
	default JsonQuote<T> convertTo(TickerList<T> to) {
		throw new UnsupportedOperationException("convertTo(List<T> is not supported");
	}
}
