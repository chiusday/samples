package com.samples.common.exception.model.dto;

import java.time.Instant;

public class BaseExceptionResponse {
	protected Instant timestamp;
	protected String message;
	protected String details;
	
	public BaseExceptionResponse(String message, String details) {
		super();
		this.timestamp = Instant.now();
		this.message = message;
		this.details = details;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
