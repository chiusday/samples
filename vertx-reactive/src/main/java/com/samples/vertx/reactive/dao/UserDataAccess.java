package com.samples.vertx.reactive.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.DBConfig;
import com.samples.vertx.reactive.interfaces.VertxSQLDataAccess;
import com.samples.vertx.reactive.model.User;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.core.eventbus.Message;

@Component
public class UserDataAccess extends VertxSQLDataAccess<User> {
	private Logger log = LoggerFactory.getLogger(UserDataAccess.class);

	public UserDataAccess(DBConfig config) {
		super(User.class, config);
	}

	@Override
	public void executeCreate() {
		String s = "CREATE TABLE IF NOT EXISTS "+ getTableName() 
		+" (id BIGINT IDENTITY, name VARCHAR(100), " 
		+ "groupId INTEGER, password VARCHAR(32))";
		
		this.jdbc.rxGetConnection()
			.flatMap(conn -> {
				Single<UpdateResult> result = conn.rxUpdate(s);
				return result.doAfterTerminate(conn::close);
			})
			.subscribe(result -> {
				log.info("Result >> " + JsonObject.mapFrom(result).encode());
			}, error -> {
				log.error("Error creating table "+getTableName()+"\n"+error.getMessage());
			});
	}

	@Override
	public void insert(Message<JsonObject> message) {
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		User user = userMessage.getModel();
		insert(user, next -> {
			if (isTransactionFailed(next, userMessage) == false){
				userMessage.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(userMessage));
		});
	}

	@Override
	public void delete(Message<JsonObject> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void select(Message<JsonObject> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Message<JsonObject> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray toJsonArray(User model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray noKeyJsonArray(User model) {
		// TODO Auto-generated method stub
		return null;
	}

}
