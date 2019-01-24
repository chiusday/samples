package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.model.Ticker;
import com.samples.vertx.reactive.visitor.interfaces.IRxResponseVisitor;

@Component
public class MarketDataGetResponseVisitor implements IRxResponseVisitor<Ticker> {

	@Value("${message.failed.internal-error.get}")
	private String errorText;
	
	@Value("${message.success.get}")
	private String successText;
	
	private HttpStatus httpStatus = HttpStatus.OK;
	private String currentMessage;
	
	@Override
	public String getErrorText() {
		return this.errorText;
	}

	@Override
	public String getResultText() {
		return this.currentMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<Ticker> message) {
		return new ResponseEntity<>
			(getModel(message)==null ? getResultText() : message.getModel(), 
				this.httpStatus);
	}

	@Override
	public Ticker getModel(DataAccessMessage<Ticker> messageTicker) {
		if (messageTicker.getRecords().isEmpty()) {
			this.currentMessage = "Record not found.";
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			this.currentMessage = this.successText;
			httpStatus = HttpStatus.OK;
		}
		
		return messageTicker.getModel();		
	}
}
