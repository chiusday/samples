package com.samples.market.stocks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.service.StocksService;
import com.samples.market.stocks.visitor.model.JsonQuote;

import io.vertx.core.json.JsonObject;

@RestController
public class StocksController {
	@Autowired
	private StocksService stocksService;
	
	@GetMapping("/stock/get/list/json/{id}")
	public ResponseEntity<Object> getJsonList(@PathVariable String id){
		JsonObject response = new JsonObject();
//		response.put(id, stocksService.getHistoricalTicker(id));
		
		JsonQuote jsonQuote = stocksService.getHistoricalQuotes(id);
		response.put(id, jsonQuote.getHistoricalQuotes());
		
		return new ResponseEntity<Object>(response.encodePrettily(), HttpStatus.OK);
	}
	
	@GetMapping("/stock/get/list/{id}")
	public List<HistoricalTicker> getList(@PathVariable String id){
		JsonQuote jsonQuote = stocksService.getHistoricalQuotes(id);
		
		return jsonQuote.getHistoricalQuotes();
	}
}
