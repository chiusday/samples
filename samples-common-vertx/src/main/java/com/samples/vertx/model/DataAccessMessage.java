package com.samples.vertx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.samples.vertx.enums.DBOperations;

import io.vertx.core.json.JsonArray;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;

public class DataAccessMessage<T> {
	//public constants
	public static final String FAILURE_MESSAGE = "failureMessage";
	
	private Class<T> type;
	private T model;
	private String key;
	private String criteria;
	private int affectedRecords;
	private List<Integer> batchResult;
	private JsonArray parameters;
	private JsonArray outParameters;
	private List<T> records;
	private List<JsonArray> listJsonArray;
	private ResultSet storedProcResult;
	private DBOperations operation;
	private JsonObject failure;
	
	public DataAccessMessage() {}
	
	/**
	 * Constructor that will set the "type" name property of this DataAccessMessage
	 * This is mandatory for message routing in DataAcessMessageRouter to work
	 * @param typeName
	 * 		Class<T>.getType()
	 */		
	public DataAccessMessage(Class<T> type){
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	public DataAccessMessage(JsonObject json){
		try{
			this.type = (Class<T>) Class.forName(json.getString("type"));
		} catch (ClassNotFoundException classNotFound){
			System.out.println("Error casting data type.\n" + classNotFound.getStackTrace());
		}
		this.key = json.getString("key");
		this.criteria = json.getString("criteria");
		this.affectedRecords = json.getInteger("affectedRecords");
		this.batchResult = json.getValue("batchResult")==null ? null 
				: json.getJsonArray("batchResult").getList();
		this.parameters = json.getJsonArray("parameters");
		this.outParameters = json.getJsonArray("outParameters");
		System.out.println("printing out json.getValue(\"records\") -> "+json.getValue("records"));
		this.records = json.getValue("records")==null ? null 
				: toListModel(json.getJsonArray("records"));
		this.listJsonArray = json.getJsonArray("listJsonArray")==null ? null : 
			json.getJsonArray("listJsonArray").getList();
		this.storedProcResult = (ResultSet)json.getValue("storedProcResult");
		this.operation = DBOperations.valueOf(json.getString("operation"));
		this.failure = JsonObject.mapFrom(json.getJsonObject("failure"));
		setModel(
				(json.getValue("model") != null) ? json.getJsonObject("model").mapTo(type) : null
			);
	}

	public Class<T> getType() {
		return type;
	}

	/**
	 * POJO to be persisted. Used in:
	 * insert and update
	 * @param model
	 */
	public void setModel(T model){ this.model = model; }
	public T getModel(){ return this.model; }
	
	/**
	 * Unique record identifier. Used in:
	 * delete (single record) and update
	 * @param key
	 */
	public void setKey(String key){ this.key = key; }
	public String getKey(){ return this.key; }
	
	/**
	 * SQL WHERE clause criteria. Used in:
	 * Stored procedure call and delete (multiple records)
	 * @param criteria
	 */
	public void setCriteria(String criteria){ this.criteria = criteria; }
	public String getCriteria(){ return this.criteria; }
	
	/**
	 * Number of updated or deleted record(s). Used in:
	 * delete and update
	 * @param affectedRecords
	 */
	public void setAffectedRecords(int affectedRecords){ 
		this.affectedRecords = affectedRecords; 
	}
	public int getAffectedRecords() { return this.affectedRecords; }
	
	/**
	 * Batch Result
	 * @return List of results
	 */
	public List<Integer> getBatchResult() { return this.batchResult; }
	public void setBatchResult(List<Integer> batchResult) {
		this.batchResult = batchResult;
	}
	
	/**
	 * Parameters for the Prepared SQL Statement. Used in:
	 * select and delete (multiple records)
	 * @return
	 */
	public JsonArray getParameters() { return this.parameters; }
	public void setParameters(JsonArray parameters){ this.parameters = parameters; }
	
	/**
	 * Database records. Used in:
	 * select
	 * @return
	 * 	ResultSet returned by executing the DBOperation
	 */
	public List<T> getRecords(){ return this.records; }
	@SuppressWarnings("unchecked")
	public void setRecords(List<T> records){
		this.records = records; 
		if (!records.isEmpty()) {
			this.setModel((T)records.get(0));
		}
	}
	
	/**
	 * Database records. Used in:
	 * select streamed records
	 * @return
	 */
	public List<JsonArray> getListJsonArray(){ return this.listJsonArray; }
	public void setListJsonArray(List<JsonArray> listJsonArray) {this.listJsonArray = listJsonArray;}

	public DBOperations getOperation(){ return this.operation; }
	public void setOperation(DBOperations operation){ this.operation = operation; }
	
	public void setOutParameters(JsonArray outParameters) {
		this.outParameters = outParameters;
	}
	public JsonArray getOutParameters() {
		return outParameters;
	}
	
	/**
	 * Records returned by stored procedure
	 * @param storedProcResult
	 */
	public void setStoredProcResult(ResultSet storedProcResult) {
		this.storedProcResult = storedProcResult;
	}
	public ResultSet getStoredProcResult() {
		return storedProcResult;
	}
	
	public JsonObject getFailure() {
		return failure;
	}
	public void setFailure(JsonObject failure) {
		this.failure = failure;
	}

	private List<T> toListModel(JsonArray records){
		return records.stream().map(rec -> ((JsonObject)rec).mapTo(type))
				.collect(Collectors.toList());
//		List<T> reply = new ArrayList<T>();
//		for(Object record : records) {
//			JsonObject rec = (JsonObject)record;
//			reply.add(rec.mapTo(type));
//		}
//		return reply;
	}
}
