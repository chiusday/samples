package com.samples.vertx.reactive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;

@Service
public class WebIntradayTickerService extends WebMarketDataService<IntradayTicker> {
	
	@Autowired
	private IntradayTickerService intrTickService;
	
	@Override
	public void setMarketDataService() {
		this.marketDataService = intrTickService;
	}

	public ResponseEntity<Object> getMarketDataAsEntity(String symbol, String priceTime,
			MarketdataAPIConsumer<IntradayTicker> webConsumer) {
		
		List<IntradayTicker> tickers = getWebMarketDataThenAdd(symbol, webConsumer);
		IntradayTicker ticker = tickers.stream().filter(t -> 
				((IntradayTicker)t).getPriceTime().equals(priceTime))
			.findFirst().get();
		
		return new ResponseEntity<Object>(ticker, HttpStatus.OK);
	}
}
