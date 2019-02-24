package com.samples.vertx.reactive.visitor.interfaces;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

public abstract class AddRxResponseVisitor<T> extends RxResponseVisitor<T> {
	@Value("${message.failed.internal-error.ins}")
	protected String errorMessage;
	
	@Value("${message.success.ins}")
	protected String successMessage;
	
	protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	protected String currentMessage = successMessage;
	
	@Override
	public String getErrorText() {
		return this.errorMessage;
	}
	
	@Override
	public String getResultText() {
		return this.currentMessage;
	}
}
