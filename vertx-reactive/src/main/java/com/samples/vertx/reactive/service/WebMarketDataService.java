package com.samples.vertx.reactive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.interfaces.WebMarketdataConsumer;
import com.samples.vertx.reactive.visitor.AsyncMarketDataBatchAddRxResponseVisitor;
import com.samples.vertx.reactive.visitor.model.BaseVisitorModelResp;
import com.samples.vertx.reactive.visitor.model.BatchRxResponse;

@Service
public class WebMarketDataService<T extends Ticker> {
	@Value("${stocker.url}")
	private String sourceUrl;
	
	@Autowired
	private MarketDataService<T> marketDataService;
	
	@Autowired
	private AsyncMarketDataBatchAddRxResponseVisitor<T> asyncBatchAddRespVisitor;
	
	public List<T> getWebMarketData(String symbol, WebMarketdataConsumer<T> webConsumer) {
		List<T> tickers = webConsumer.getTickerList(symbol);
		//batch add the list to DB Asynchronously
		asyncBatchAddMarketData(tickers);
		//return the market data that corresponds to 'symbol' while a number of data are
		//being saved in the DB concurrently
		return tickers;
	}
	
	public ResponseEntity<Object> getWebMarketDataAsEntity(String symbol,
			WebMarketdataConsumer<T> webConsumer) {
		
		List<T> tickers = webConsumer.getTickerList(symbol);
		//batch add the list to DB Asynchronously
		BaseVisitorModelResp<T> response = asyncBatchAddMarketData(tickers);
		//return the market data that corresponds to 'symbol' while a number of data are
		//being saved in the DB concurrently
		return response.getResponseEntity();
	}
	
	private BaseVisitorModelResp<T> asyncBatchAddMarketData(List<T> tickers){
		BatchRxResponse<T> response = marketDataService.batchAddMarketData(tickers);
		response.accept(asyncBatchAddRespVisitor);
		
		return response;
	}
}
