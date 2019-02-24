package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.HistoricalTickerRequestBySymbol;
import com.samples.vertx.reactive.service.MarketDataService;
import com.samples.vertx.reactive.visitor.interfaces.RxResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@RestController()
public class HistoricalTickerController {
	@Autowired
	private MarketDataService<HistoricalTicker> marketDataService;
	
	@Autowired
	private RxResponseVisitor<HistoricalTicker> marketDataGetResponseVisitor;
	
	@Autowired
	private RxResponseVisitor<HistoricalTicker> marketDataAddResponseVisitor;

	//Post is used so this can be secured via spring oauth2
	@PostMapping("/market-data/historical")
	public ResponseEntity<Object> addHistoricalTicjer
			(@RequestBody HistoricalTicker ticker){
		
		RxResponse<HistoricalTicker> marketDataResponse = marketDataService.addMarketData(ticker);
		marketDataResponse.accept(marketDataAddResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}

	//Post is used so this can be secured via spring oauth2
	@PostMapping("/market-data/historical/get")
	public ResponseEntity<Object> getHistoricalTicker
			(@RequestBody HistoricalTickerRequestBySymbol request){
		
		RxResponse<HistoricalTicker> marketDataResponse = marketDataService
				.getMarketData(request.getSymbol(), HistoricalTicker.class);
		marketDataResponse.accept(marketDataGetResponseVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
}
