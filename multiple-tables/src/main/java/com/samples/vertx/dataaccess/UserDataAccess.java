package com.samples.vertx.dataaccess;

import com.samples.vertx.interfaces.VertxSQLDataAccess;
import com.samples.vertx.proxies.HasherProxy;
import com.samples.vertx.proxies.model.HashingRequest;
import com.samples.vertx.proxies.model.HashingResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.samples.vertx.DBConfig;
import com.samples.vertx.dataaccess.model.DataAccessMessage;
import com.samples.vertx.dataaccess.model.User;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;

/**
 * Data access to User data.
 * <p>
 * This class is a vertx data access implementation and for this example's purposes,
 * this will show an implementation to code the abstract methods toJsonArray and noKeyJsonArray
 * from the abstract super class VertxSQLDataAccess.
 * Advantage is that this the implementation is generic. It doesn't require member(s)
 * or field(s) information about the model. If this is the preferred implementation,
 * then these methods don't need to be abstract. Since the implementation is generic,
 * it can be done in VertxSQLDataAccess itself.
 * The draw back is performance overhead because it uses reflection to successfully 
 * map fields and these methods will most likely be executed frequently. 
 * This may be negligible for a lot of application but, for very low
 * latency requirements, a direct field-to-value mapping will be the best option which
 * I will also show in MarketInfoDataAccess implementation of VertxSQLDataAccess.
 * 
 * @author Ricardo (Jon) Chiu
 *
 * @param <User>
 */
//@Component
public class UserDataAccess extends VertxSQLDataAccess<User> {
	Logger log = LoggerFactory.getLogger(UserDataAccess.class);

	@Autowired
	private HasherProxy hasher;
	
	public UserDataAccess(DBConfig config) {
		super(User.class, config);
	}
	
	@Override
	protected String getTableName() {
		return "User";
	}

	@Override
	public JsonArray toJsonArray(User model) {
		JsonArray reply = new JsonArray();
		JsonObject.mapFrom(model).stream().forEach(field -> {
			if (field.getValue() != null) reply.add(field.getValue());
		});
		return reply;
	}

	@Override
	public JsonArray noKeyJsonArray(User model) {
		JsonArray jsonArray = toJsonArray(model);
		jsonArray.remove(0);
		return jsonArray;
	}
	
	@Override
	public void select(Message<JsonObject> message){
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		select(userMessage.getCriteria(), userMessage.getParameters(), next -> {
			if (isTransactionFailed(next, userMessage) == false){
				userMessage.setRecords(next.result());
			}
			message.reply(JsonObject.mapFrom(userMessage));
		});
	}
	
	@Override
	public void insert(Message<JsonObject> message){
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		User user = userMessage.getModel();
		user.setPassword(hashPassword(user.getPassword()));
		insert(user, next -> {
			if (isTransactionFailed(next, userMessage) == false){
				userMessage.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(userMessage));
		});
	}
	
	@Override
	public void update(Message<JsonObject> message){
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		update(userMessage.getKey(), userMessage.getCriteria(), userMessage.getModel(), next -> {
			if (isTransactionFailed(next, userMessage) == false){
				userMessage.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(userMessage));
		});
	}
	
	@Override
	public void delete(Message<JsonObject> message){
		DataAccessMessage<User> userMessage = new DataAccessMessage<>(message.body());
		delete(userMessage.getKey(), next -> {
			if (isTransactionFailed(next, userMessage) == false){
				userMessage.setAffectedRecords(next.result());
			}
			message.reply(JsonObject.mapFrom(userMessage));
		});
	}
	
	@Override
	public void executeCreate(){
		this.jdbc.getConnection(asyncConn -> {
			SQLConnection connection = asyncConn.result();
			connection.execute("CREATE TABLE IF NOT EXISTS "+ getTableName() 
					+" (id BIGINT IDENTITY, name VARCHAR(100), " 
					+ "groupId INTEGER, password VARCHAR(32))", 
				result -> {
					if (result.failed()){
						System.out.println("Create USER table failed");
					} else {
						System.out.println("Create USER table Successful");
						createDummyData(connection);
					}
				});
		});
	}
	
	private void createDummyData(SQLConnection connection){
		String password = hashPassword("Password1");
		connection.execute("INSERT INTO "+ getTableName()
					+" (name, groupId, password)"
					+" values('Jon', 1, '"+ password +"')", 
				result -> {
					if (result.failed()){
						System.out.println("Create USER: 'Jon' failed");
					} else {
						System.out.println("Create USER: 'Jon' Successful");
					}
				});
	}
	
	private String hashPassword(String text){
		log.info("calling hasher with: {}", text);
		HashingResponse response = hasher.hash(new HashingRequest(text));
		log.info("hasher returned with: {}", response.getEncryptedText());
		return response.getEncryptedText();
	}
}
