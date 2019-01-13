package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.visitor.interfaces.UserDataResponseVisitor;

@Component
public class UserAddResponseVisitor implements UserDataResponseVisitor {
	
	@Value("${message.failed.internal-error.ins}")
	private String errorMessage;
	
	@Value("${message.success.ins}")
	private String successMessage;
	
	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

	@Override
	public String getResultMessage() {
		return this.successMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<User> message) {
		return new ResponseEntity<>(message.getModel(), HttpStatus.CREATED);
	}
}
