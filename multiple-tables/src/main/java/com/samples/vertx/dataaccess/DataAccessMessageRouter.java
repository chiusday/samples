package com.samples.vertx.dataaccess;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.vertx.dataaccess.model.DataAccessMessage;
import com.samples.vertx.dataaccess.model.User;
import com.samples.vertx.enums.DBOperations;
import com.samples.vertx.interfaces.IVertxSQLDataAccess;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

@Service
public class DataAccessMessageRouter {

	@Autowired
	//private IDataAccessOperator<User> userDataAccess;
	private IVertxSQLDataAccess<User> userDataAccess;
	
	private Map<Class<?>, IVertxSQLDataAccess<?>> operators = 
			new ConcurrentHashMap<Class<?>, IVertxSQLDataAccess<?>>();
	
	public DataAccessMessageRouter(){ 
		super();
	}
	
	/**
	 * Builds the list of VertxDataAccess<? extends BaseModel> implementors 
	 * that will be available as the target of message routing.
	 * Establish data connections. Start all VertxDataAccess<> that can be routed.
	 * @param vertx
	 * 		Vertx context
	 */
	public void setDataConnections(final Vertx vertx){
		//build list of data access implementors
		operators.put(User.class, userDataAccess);

		//establish db connection for each
		//this a good use case for an observable interface
		operators.values().forEach(daImplementor -> {
			daImplementor.startBackend(vertx, next -> {
				//put post data connection requirements here if any
				daImplementor.executeCreate();
			});
		});
		
	}

	public void routeMessage(final Message<JsonObject> message){
		DataAccessMessage<?> daMessage = new DataAccessMessage<>(message.body());
		DBOperations operation = daMessage.getOperation();
		switch(operation) {
		case select : 
			operators.get(daMessage.getType()).select(message);
			break;
		case insert : 
			operators.get(daMessage.getType()).insert(message);
			break;
		case update :
			operators.get(daMessage.getType()).update(message);
			break;
		case delete :
			operators.get(daMessage.getType()).delete(message);
			break;
		default :
			daMessage.setFailure(new JsonObject().put("message", operation.name() 
					+ " operation handler is not found"));
			message.reply(JsonObject.mapFrom(daMessage));
		}
	}
	
}
