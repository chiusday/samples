package com.samples.vertx.reactive.visitor.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.visitor.model.UserDataResponse;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.Message;

public interface UserDataResponseVisitor extends IVisitor {
	Logger log = LoggerFactory.getLogger(UserDataResponseVisitor.class);
	
	String getErrorMessage();
	String getResultMessage();
	ResponseEntity<Object> getResponseEntity(DataAccessMessage<User> message);
	
	default User getModel(DataAccessMessage<User> message) {
		return message.getModel();
	}
	
	@Override
	default void visit(UserDataResponse userDataResp) {
		try {
			Message<JsonObject> message = userDataResp.getSingle().blockingGet();
			log.debug("Message recieved >> \n" + Json.encodePrettily(message.body()));
			DataAccessMessage<User> userPayload = new DataAccessMessage<>(message.body());
			if (userPayload.getFailure() != null && userPayload.getFailure().getMap() != null){
				userDataResp.setHasError(true);
				userDataResp.setResponseEntity(getResponseEntity(userPayload));
				log.error(userPayload.getFailure().getString
						(DataAccessMessage.FAILURE_MESSAGE));
			} else {
				userDataResp.setModel(getModel(userPayload));
				userDataResp.setHasError(false);
				userDataResp.setResponseEntity(getResponseEntity(userPayload));
				log.info(getResultMessage()+"\n"+ (userDataResp.getModel() == null ? "{}" :
						Json.encodePrettily(userDataResp.getModel())));
			}
		} catch (Exception e) {
			userDataResp.setHasError(true);
			userDataResp.setResponseEntity(new ResponseEntity<Object>
				(getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
			log.error(getErrorMessage()+"\n"+e);
		}
	}
}
