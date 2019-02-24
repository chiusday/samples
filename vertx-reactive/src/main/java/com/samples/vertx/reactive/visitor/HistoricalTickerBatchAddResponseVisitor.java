package com.samples.vertx.reactive.visitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.visitor.interfaces.BatchRxResponseVisitor;

@Component
public class HistoricalTickerBatchAddResponseVisitor 
		extends BatchRxResponseVisitor<HistoricalTicker> {
	
	@Value("${message.failed.internal-error.ins}")
	private String errorMessage;
	
	@Value("${message.success.ins}")
	private String successMessage;
	
	protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	protected String currentMessage = successMessage;
	
	@Override
	public String getErrorText() {
		return this.errorMessage;
	}

	@Override
	public String getResultText() {
		return this.successMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<HistoricalTicker> daMessage) {
		return new ResponseEntity<>(
					getBatchResult(daMessage).size()==0
						? getResultText()
						: daMessage.getBatchResult(),
					this.httpStatus
				);
	}

	@Override
	protected List<Integer> getBatchResult(DataAccessMessage<HistoricalTicker> daMessage) {
		if (daMessage.getFailure() != null && daMessage.getFailure().getMap() != null) {
			this.currentMessage = this.errorMessage;
			this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ArrayList<Integer>();
		} else {
			this.currentMessage = this.successMessage;
			this.httpStatus = HttpStatus.CREATED;
			return daMessage.getBatchResult();
		}
	}
}