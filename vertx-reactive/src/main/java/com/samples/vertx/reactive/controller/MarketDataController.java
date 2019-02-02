package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.Ticker;
import com.samples.vertx.reactive.service.MarketDataService;
import com.samples.vertx.reactive.visitor.MarketDataAddResponseVisitor;
import com.samples.vertx.reactive.visitor.MarketDataGetResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@RestController
public class MarketDataController {
	@Autowired
	private MarketDataService marketDataService;
	
	@Autowired
	private MarketDataGetResponseVisitor marketDataGetResponseVisitor;
	
	@Autowired
	private MarketDataAddResponseVisitor marketDataAddResponseVisitor;

	//Post is used so this can be secured via spring oauth2
	@PostMapping("/market-data")
	public <T extends Ticker> ResponseEntity<Object> addMarketData(@RequestBody T ticker){
		RxResponse<T> marketDataResponse = marketDataService.addMarketData(ticker);
		marketDataResponse.accept(marketDataAddResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}

	//Post is used so this can be secured via spring oauth2
	@PostMapping("/market-data/get/{id}")
	public ResponseEntity<Object> getMarketData(@PathVariable String id){
		RxResponse<Ticker> marketDataResponse = marketDataService.getMarketData(id);
		marketDataResponse.accept(marketDataGetResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
}
