package com.samples.market.stocks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.TickerRequestBySymbol;
import com.samples.market.stocks.service.IntradayTickerService;
import com.samples.market.stocks.visitor.model.IntradayTickerListVisitorModel;

@RestController
public class IntradayTickerController {
	@Autowired
	private IntradayTickerService intradayTickerService;
	
	@GetMapping("/stock/intraday/{id}")
	public ResponseEntity<Object> getJsonList(@PathVariable String id){
		IntradayTickerListVisitorModel historicalTickerVisitorModel = 
				intradayTickerService.getIntradayList(id); 
		
		return historicalTickerVisitorModel.getResponseEntity();
	}

	@PostMapping("/stock/intraday")
	public ResponseEntity<Object> getInraday(@RequestBody TickerRequestBySymbol request) {
		IntradayTickerListVisitorModel visitorModel = 
				intradayTickerService.getIntradayList(request.getSymbol());
		
		return visitorModel.getResponseEntity();
	}
}
