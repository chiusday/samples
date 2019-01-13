package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.enums.DBOperations;
import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.verticle.DataAccessInterchange;
import com.samples.vertx.reactive.visitor.model.UserDataResponse;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

@Service
public class UserService {
	@Autowired
	private AppConfig appConfig;
	@Autowired
	private DataAccessInterchange dataAccessInterchange;
	
	public UserDataResponse add(User user) {
//		UserAddResponse userDataResponse = new UserAddResponse();
		DataAccessMessage<User> userDAMessage = new DataAccessMessage<>(User.class);
		userDAMessage.setModel(user);
		userDAMessage.setOperation(DBOperations.insert);
//		EventBus eventBus = dataAccessInterchange.getRxVertx().eventBus();
//		Single<Message<JsonObject>> response = eventBus.<JsonObject>rxSend
//				(appConfig.getAddressUser(), JsonObject.mapFrom(userDAMessage));
//		userDataResponse.setSingle(response);
//		
//		return userDataResponse;
		
		return processUser(userDAMessage);
	}
	
	public UserDataResponse get(int id) {
		DataAccessMessage<User> userDAMessage = new DataAccessMessage<>(User.class);
		userDAMessage.setCriteria("id=?");
		userDAMessage.setParameters(new JsonArray().add(id));
		userDAMessage.setOperation(DBOperations.select);
		
		return processUser(userDAMessage);
	}
	
	private UserDataResponse processUser(DataAccessMessage<User> userDAMessage) {
		UserDataResponse userDataResponse = new UserDataResponse();
		EventBus eventBus = dataAccessInterchange.getRxVertx().eventBus();
		Single<Message<JsonObject>> response = eventBus.<JsonObject>rxSend
				(appConfig.getAddressUser(), JsonObject.mapFrom(userDAMessage));
		userDataResponse.setSingle(response);
		
		return userDataResponse;
	}
}
