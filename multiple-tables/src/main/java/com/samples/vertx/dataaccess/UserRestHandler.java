package com.samples.vertx.dataaccess;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.samples.vertx.AppConfig;
import com.samples.vertx.dataaccess.model.DataAccessMessage;
import com.samples.vertx.dataaccess.model.User;
import com.samples.vertx.enums.DBOperations;
import com.samples.vertx.interfaces.IRestHandler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

@Component
public class UserRestHandler implements IRestHandler {
	private Vertx vertx;
	private AppConfig config;
	
	@Value("${message.ins.failed.internal-error}")
	private String insFailedInternalErr;
	
	public UserRestHandler(AppConfig config, Vertx vertx){
		this.config = config;
		this.vertx = vertx;
	}
	
	public void insert(RoutingContext context){
		User user = Json.decodeValue(context.getBodyAsString(), User.class);
		DataAccessMessage<User> message = new DataAccessMessage<>(User.class);
		message.setModel(user);
		message.setOperation(DBOperations.insert);
		vertx.eventBus().<JsonObject>send(config.getAddressUser(), JsonObject.mapFrom(message), next -> {
			if(next.failed()){
				context.response().setStatusCode(500).end(insFailedInternalErr);
			} else {
				DataAccessMessage<User> returnedPayload = new DataAccessMessage<>(next.result().body());
				if (returnedPayload.getFailure() != null && returnedPayload.getFailure().getMap() != null){
					//log this
					System.out.println(insFailedInternalErr+"\n"+returnedPayload.getFailure().getString
							(DataAccessMessage.FAILURE_MESSAGE));
					context.response().setStatusCode(500).end(insFailedInternalErr);
				} else {
					String returnedUser = Json.encodePrettily(returnedPayload.getModel()); 
					System.out.println("Insert successful!\n"+returnedUser);
					context.response()
					.setStatusCode(201)
					.putHeader(config.getHeaderContentType(), config.getHeaderApplicationJson())
					.end(returnedUser);
				}
			}
		});
		
	}
	
	public void select(RoutingContext context){
		String criteria = "id=?";
		DataAccessMessage<User> message = new DataAccessMessage<>(User.class);
		message.setCriteria(criteria);
		message.setParameters(new JsonArray().add(context.request().getParam("id")));
		message.setOperation(DBOperations.select);
		vertx.eventBus().<JsonObject>send(config.getAddressUser(), JsonObject.mapFrom(message), response -> {
			if (response.failed()){
				context.response().setStatusCode(404)
					.end(response.cause().toString());
				
			} else if (response.result().body().getJsonArray("records").isEmpty()) {
				context.response().setStatusCode(404)
					.end("record not found");
				
			} else {
				DataAccessMessage<Process> outMessage = new DataAccessMessage<Process>(response.result().body());
				User user = new User(outMessage.getRecords().get(0));
				
				context.response()
					.setStatusCode(200)
					.putHeader(config.getHeaderContentType(), config.getHeaderApplicationJson())
					.end(Json.encodePrettily(user));
			}
				
		});
	}
	
	public void update(RoutingContext context){
//		String unique = context.request().getParam("id");
//		String sql = "UPDATE " + pflDataAccess.getTableName() + " SET " +
//				"ProcessName=?, Description=?, Initiation=?, Tag=? WHERE id=" + unique;
//		Process process = Json.decodeValue(context.getBodyAsString(), Process.class);
//
//		pflDataAccess.update(context.request().getParam("id"), sql, process, ar -> {
//			if(ar.failed()){
//				context.response().setStatusCode(404).end();
//			}else{
//				context.response()
//					.setStatusCode(202)
//					.putHeader(PFLDataAccessVerticle.CONTENT_TYPE, PFLDataAccessVerticle.APPLICATION_JSON)
//					.end(Json.encodePrettily(process));
//			}
//		});
	}
	
	public void delete(RoutingContext context){
		String id=context.request().getParam("id");
		if (id==null){
			context.response().setStatusCode(500);
			return;
		}
	}
	
	public void completeStartUp(AsyncResult<HttpServer> http, Future<Void> future){
		if (http.failed()){
			future.fail(http.cause());
		} else {
			future.complete();
		}
	}

}
