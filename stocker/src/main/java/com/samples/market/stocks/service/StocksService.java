package com.samples.market.stocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.samples.market.stocks.AppConfig;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteVisitor;
import com.samples.market.stocks.visitor.model.JsonQuote;
import com.samples.market.stocks.visitor.model.JsonQuoteFormat1;

import io.vertx.core.json.JsonObject;

@Service
public class StocksService {
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private Statics statics;
	
	@Autowired
	private JsonQuoteVisitor jsonDailyQuoteVisitor;

	public JsonQuote getHistoricalQuotes(String symbol) {
		String url = appConfig.getUrl(symbol);
		RestTemplate rest = new RestTemplate();
		String data = rest.getForObject(url, String.class);
		JsonObject rawData = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		
		JsonQuote jsonQuote = new JsonQuoteFormat1(symbol, rawData);
		jsonQuote.accept(jsonDailyQuoteVisitor);
		
		return jsonQuote;
	}
		
//	private JsonObject getTicker(JsonObject priceOfDay) {
//		JsonObject ticker = new JsonObject();
//		for (Entry<String,Object> elem : priceOfDay.getMap().entrySet()) {
//			if (elem.getKey().contains("open")) {
//				ticker.put("open", elem.getValue());
//			} else if (elem.getKey().contains("close")) {
//				ticker.put("close", elem.getValue());
//			} else if (elem.getKey().contains("high")) {
//				ticker.put("high", elem.getValue());
//			} else if (elem.getKey().contains("low")) {
//				ticker.put("low", elem.getValue());
//			}
//		}
//		
//		return ticker;
//	}
	
}
