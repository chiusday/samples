package com.samples.common.exception.model.interfaces;

import java.time.Instant;

public abstract class BaseException extends RuntimeException {
	//use real UID in real app
	private static final long serialVersionUID = 1L;
	private Instant instant;
	private String source;
	private String message;
	
	public BaseException(String source, String message) {
		super();
		this.instant = Instant.now();
		this.source = source;
		this.message = message;
	}
	public Instant getInstant() {
		return instant;
	}
	public void setInstant(Instant instant) {
		this.instant = instant;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
