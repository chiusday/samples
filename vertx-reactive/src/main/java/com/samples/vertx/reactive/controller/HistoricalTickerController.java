package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.common.exception.model.dto.DataNotFoundException;
import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.TickerRequestBySymbol;
import com.samples.vertx.reactive.interfaces.MarketdataAPIConsumer;
import com.samples.vertx.reactive.service.HistoricalTickerService;
import com.samples.vertx.reactive.service.WebHistoricalTickerService;
import com.samples.vertx.reactive.visitor.MarketDataAddResponseVisitor;
import com.samples.vertx.reactive.visitor.MarketDataGetResponseVisitor;
import com.samples.vertx.reactive.visitor.model.RxResponse;

@RestController()
public class HistoricalTickerController {
//		extends BaseController<HistoricalTicker> {
	
//	public HistoricalTickerController() {
//		super(HistoricalTicker.class);
//	}

	@Autowired
	private HistoricalTickerService histTickService;
	
	@Autowired
	private MarketDataAddResponseVisitor<HistoricalTicker> historicalAddVisitor;
	
	@Autowired
	private MarketDataGetResponseVisitor<HistoricalTicker> historicalGetVisitor;
	
	@Autowired 
//	private WebMarketDataService<HistoricalTicker> historicalWeb;
	private WebHistoricalTickerService historicalWebSourceService;
	
	@Autowired
	private MarketdataAPIConsumer<HistoricalTicker> webConsumer;

//	@Override
//	public void setBeans() {
//		this.marketDataService = new MarketDataService<>(historicalTickersVisitor);
//		this.addResponseVisitor = historicalAddVisitor;
//		this.getResponseVisitor = historicalGetVisitor;
//		this.webMarketDataService = new WebMarketData;
//	}

	@PostMapping("/market-data/historical")
	public ResponseEntity<Object> addHistoricalTicker
			(@RequestBody HistoricalTicker ticker) {
		
		RxResponse<HistoricalTicker> marketDataResponse = 
				(RxResponse<HistoricalTicker>) histTickService.addMarketData(ticker);
		marketDataResponse.accept(historicalAddVisitor);
		
		return marketDataResponse.getResponseEntity();
	}
	
	@PostMapping("market-data/historical/get")
	public ResponseEntity<Object> getHistoricalTicker
			(@RequestBody TickerRequestBySymbol request) {
		
		RxResponse<HistoricalTicker> marketDataResponse = 
				(RxResponse<HistoricalTicker>) histTickService
				.getMarketData(request.getSymbol(), HistoricalTicker.class);
		
		try {
			marketDataResponse.accept(historicalGetVisitor);
		} catch (DataNotFoundException dnfEx) {
			return historicalWebSourceService.getWebMarketDataAsEntity
					(request.getSymbol(), webConsumer);
		}
		
		return marketDataResponse.getResponseEntity();
	}

//	@Autowired
//	private HistoricalTickerService marketDataService;
//	
//	@Autowired
//	private HistoricalTickerGetResponseVisitor getResponseVisitor;
//	
//	@Autowired
//	private HistoricalTickerAddResponseVisitor addResponseVisitor;
//	
//	@Autowired
//	private WebHistoricalTickerService webMarketDataService;
//	
//	@Autowired
//	private RestHistoricalTickerConsumer webConsumer;
//
//	@PostMapping("/market-data/historical")
//	public ResponseEntity<Object> addHistoricalTicker
//			(@RequestBody HistoricalTicker ticker){
//		
//		HistoricalTickerRxResponse marketDataResponse = 
//				(HistoricalTickerRxResponse) marketDataService.addMarketData(ticker);
//		marketDataResponse.accept(addResponseVisitor);
//		
//		return marketDataResponse.getResponseEntity();
//	}
//
//	//Post is used so this can be secured via spring oauth2
//	@PostMapping("/market-data/historical/get")
//	public ResponseEntity<Object> getHistoricalTicker
//			(@RequestBody TickerRequestBySymbol request){
//		
//		HistoricalTickerRxResponse marketDataResponse = 
//				(HistoricalTickerRxResponse) marketDataService.getMarketData
//				(request.getSymbol(), HistoricalTicker.class);
//		try {
//			marketDataResponse.accept(getResponseVisitor);
//		} catch (DataNotFoundException dnfEx) {
//			return webMarketDataService.getWebMarketDataAsEntity(
//					request.getSymbol(), webConsumer);
//		}
//		
//		return marketDataResponse.getResponseEntity();
//	}	
}
