package com.samples.vertx.interfaces;

import java.util.List;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;

public interface IVertxSQLDataAccess<T> {
	public abstract JsonArray toJsonArray(T model);
	public abstract JsonArray noKeyJsonArray(T model);

	Class<T> getType(); 

	void insert(Message<JsonObject> message);
	
	void insert(T model,  Handler<AsyncResult<T>> next); 
	
	void delete(Message<JsonObject> message);
	/**
	 * Deletes a single record identified by the given key
	 * @param key - unique record identifier
	 * @param next - Async Result
	 */
	void delete(String key, Handler<AsyncResult<Integer>> next); 

	/**
	 * Deletes multiple records based on the SQL Statement passed
	 * @param sql - DELETE sql statement to execute with '?' place holder for values on the WHERE clause. 
	 * @param parameters - JsonArray containing the values to replace '?' in the sql statement. 
	 * 	Sequence is strictly followed.
	 * @param next - Async Result
	 * 
	 */
	void delete(String sql, JsonArray parameters, Handler<AsyncResult<Integer>> next); 
	
	void select(Message<JsonObject> message);

	void select(String sql, JsonArray parameters, Handler<AsyncResult<List<JsonObject>>> next); 
	
	void selectStreamedResult(String sql, JsonArray parameters, Handler<AsyncResult<List<JsonArray>>> next); 

	void update(Message<JsonObject> message);

	void update(String key, String sql, T model, Handler<AsyncResult<T>> next); 
	
	void callStoredProcedure(String callStatement, JsonArray in, JsonArray out, Handler<AsyncResult<ResultSet>> next);
	
	void startBackend(Vertx vertx, Handler<AsyncResult<Void>> next); 

	void executeCreate();
}
