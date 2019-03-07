package com.samples.market.stocks.converter.interfaces;

import java.util.Map.Entry;

import com.samples.market.model.Ticker;
import com.samples.market.model.TickerList;
import com.samples.market.stocks.visitor.interfaces.ConvertibleJsonTicker;

import io.vertx.core.json.JsonObject;

/**
 * Converter between a ConvertibleJsonTicker<T extends Ticker> and a TickerList of T that
 * extends Ticker
 * @author chiusday
 *
 * @param <T> - Ticker or it's subclasses
 */
public interface JsonToTickerList<T extends Ticker> 
		extends IConverter<ConvertibleJsonTicker<T>, TickerList<T>> {

	void additionalFields(String symbol, JsonObject quote, Entry<String, Object> entry);

	@Override
	default TickerList<T> convertFrom(ConvertibleJsonTicker<T> from) {
		TickerList<T> tickers = new TickerList<>(from.getSymbol());
		//for each quote
		from.getData().forEach(entry -> {
			JsonObject quote = new JsonObject();
			//match each element in the quote with the ticker field. 
			//Then add to list to be returned
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
	default ConvertibleJsonTicker<T> convertTo(TickerList<T> to) {
		throw new UnsupportedOperationException("convertTo(List<T> is not supported");
	}
}
