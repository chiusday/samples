package com.samples.vertx.reactive.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.samples.market.model.GetBySymbolRequest;
import com.samples.market.model.Ticker;
import com.samples.market.model.TickerList;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;

import io.vertx.core.json.Json;
public abstract class MarketDataRestConsumer<T extends Ticker, L extends TickerList<T>> 
		implements MarketdataAPIConsumer<T> {
	
	private static final Logger log = LoggerFactory.getLogger(MarketDataRestConsumer.class);
	
	protected String sourceUrl;
	protected Class<L> lClass;
	

	@PostConstruct
	protected abstract void setup();
	
	@Override
	public List<T> getTickerList(String symbol){
		String url = sourceUrl + symbol;
		RestTemplate restTemplate = new RestTemplate();
		L tickers = null;
		try {
			tickers = restTemplate.getForObject(url, lClass, symbol);
		} catch(Exception e) {
			log.error("Error getting ticker list from {}", sourceUrl, e);
		}
		
		return tickers;
	}
	
	@Override
	public List<T> postForTickerList(String symbol) {
		RestTemplate restTemplate = new RestTemplate();
		GetBySymbolRequest request = new GetBySymbolRequest(symbol); 
		L tickers = null;
		try {
			tickers = restTemplate.postForObject(sourceUrl, request, lClass);
		} catch (Exception e) {
			log.error("Error getting ticker list from {} -> {}", sourceUrl, 
					Json.encodePrettily(request), e);
		}
		
		return tickers;
	}
}
