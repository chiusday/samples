package com.samples.common.exception.model.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.samples.common.exception.model.interfaces.BaseHttpException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends BaseHttpException {
	//use real UID in real app
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String source, String message) {
		super(source, message);
		this.setStatus(HttpStatus.NOT_FOUND);
	}
}
