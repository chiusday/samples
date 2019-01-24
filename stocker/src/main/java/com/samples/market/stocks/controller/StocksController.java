package com.samples.market.stocks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.samples.market.stocks.service.StocksService;

import io.vertx.core.json.JsonObject;

@RestController
public class StocksController {
	@Autowired
	private StocksService stocksService;
	
	@GetMapping("/stock/get/list/{id}")
	public ResponseEntity<Object> getList(@PathVariable String id){
		JsonObject response = new JsonObject();
		response.put(id, stocksService.getHistoricalTicker(id));
		
		return new ResponseEntity<Object>(response.encodePrettily(), HttpStatus.OK);
	}
}
