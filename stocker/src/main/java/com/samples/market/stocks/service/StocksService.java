package com.samples.market.stocks.service;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.samples.market.models.HistoricalTicker;
import com.samples.market.stocks.AppConfig;

import io.vertx.core.json.JsonObject;

@Service
public class StocksService {
	@Autowired
	private AppConfig appConfig;

	public List<HistoricalTicker> getHistoricalTicker(String symbol){
		String url = appConfig.getUrl(symbol);
		RestTemplate rest = new RestTemplate();
		String data = rest.getForObject(url, String.class);
		JsonObject json = new JsonObject(data).getJsonObject("Time Series (Daily)");
		
		List<HistoricalTicker> reply = json.stream().map(
				entry -> 
					getTicker(symbol, JsonObject.mapFrom(entry.getValue()))
						.put("symbol", symbol)
						.put("priceDate", entry.getKey())
					.mapTo(HistoricalTicker.class)
				).collect(Collectors.toList());
		
		return reply;
	}
		
	private JsonObject getTicker(String symbol, JsonObject priceOfDay) {
//		JsonObject ticker = new JsonObject("{\"ticker\":\""+symbol+"\"}");
		JsonObject ticker = new JsonObject();
		for (Entry<String,Object> elem : priceOfDay.getMap().entrySet()) {
			if (elem.getKey().contains("open")) {
				ticker.put("open", elem.getValue());
			} else if (elem.getKey().contains("close")) {
				ticker.put("close", elem.getValue());
			} else if (elem.getKey().contains("high")) {
				ticker.put("high", elem.getValue());
			} else if (elem.getKey().contains("low")) {
				ticker.put("low", elem.getValue());
			}
		}
		
		return ticker;
	}
}
