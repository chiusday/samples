package com.samples.vertx.reactive.visitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.visitor.interfaces.UserDataResponseVisitor;

@Component
public class UserGetResponseVisitor implements UserDataResponseVisitor {
	
	@Value("${message.failed.internal-error.get}")
	private String errorMessage;
	
	@Value("${message.success.get}")
	private String successMessage;
	
	private HttpStatus httpStatus = HttpStatus.OK;
	private String currentMessage;
	
	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

	@Override
	public String getResultMessage() {
		return this.currentMessage;
	}

	@Override
	public ResponseEntity<Object> getResponseEntity(DataAccessMessage<User> message) {
		return new ResponseEntity<>
			(getModel(message)==null ? this.currentMessage : message.getModel(), 
					httpStatus);
	}
	
	@Override
	public User getModel(DataAccessMessage<User> messageUser) {
		if (messageUser.getRecords().isEmpty()) {
			this.currentMessage = "Record not found.";
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			this.currentMessage = this.successMessage;
			httpStatus = HttpStatus.OK;
		}
		
		return messageUser.getModel();
	}
}
