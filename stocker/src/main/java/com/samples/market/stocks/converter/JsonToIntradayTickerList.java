package com.samples.market.stocks.converter;

import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.converter.interfaces.JsonToTickerList;

import io.vertx.core.json.JsonObject;

@Component
public class JsonToIntradayTickerList implements JsonToTickerList<IntradayTicker> {
	
	@Override
	public void additionalFields
			(String symbol, JsonObject quote, Entry<String, Object> entry) {
		
		quote.put("symbol", symbol);
		quote.put("time", entry.getKey());
	}
}
