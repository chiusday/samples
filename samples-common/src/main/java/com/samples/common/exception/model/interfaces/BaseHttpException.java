package com.samples.common.exception.model.interfaces;

import org.springframework.http.HttpStatus;

public abstract class BaseHttpException extends BaseException {
	//use real UID in real app
	private static final long serialVersionUID = 1L;
	
	protected HttpStatus status;
	
	public BaseHttpException(String source, String message) {
		super(source, message);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
