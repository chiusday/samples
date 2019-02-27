package com.samples.market.stocks.converter;

import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.converter.interfaces.JsonToTickerList;

import io.vertx.core.json.JsonObject;

@Service
public class JsonToHistoricalTicker implements JsonToTickerList<HistoricalTicker> {

	@Override
	public void additionalFields(String symbol, JsonObject quote, 
			Entry<String, Object> entry) {
		
		quote.put("symbol", symbol);
		quote.put("priceDate", entry.getKey());
	}

}
