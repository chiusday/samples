package com.samples.market.stocks.visitor.interfaces;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.samples.market.model.Ticker;
import com.samples.market.stocks.visitor.interfaces.IVisitor;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public interface JsonQuoteVisitor extends IVisitor {
	Logger log = LoggerFactory.getLogger(JsonQuoteVisitor.class);
	
	void additionalFields(String symbol, JsonObject quote, Entry<String, Object> entry);
	
	default <T extends Ticker> void visit(JsonQuote<T> json) {
		try {
			json.getData().forEach(entry -> {
				JsonObject quote = new JsonObject();
				JsonObject.mapFrom(entry.getValue()).getMap().entrySet().forEach(elem -> {
					json.getFields().forEach(field -> {
						if (elem.getKey().contains(field)) {
							quote.put(field, elem.getValue());
						}
					});
				});
				additionalFields(json.getSymbol(), quote, entry);
				json.getQuotes().add(quote.mapTo(json.getType()));
			});		
			json.setMessage("json quote processing successful.");
			log.debug(json.getMessage()+"\n"+Json.encodePrettily(json.getQuotes()));
			json.setHasError(false);
			json.setResponseEntity(new ResponseEntity<>(json.getQuotes(), HttpStatus.OK));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setHasError(true);
			json.setMessage("Failed parsing json quote.");
			json.setResponseEntity(new ResponseEntity<>(json.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR));
			
		}
	}
}
