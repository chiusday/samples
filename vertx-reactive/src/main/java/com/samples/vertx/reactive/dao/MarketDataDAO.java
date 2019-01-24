package com.samples.vertx.reactive.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.samples.vertx.reactive.DBConfig;
import com.samples.vertx.reactive.interfaces.VertxSQLDataAccess;
import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.model.Ticker;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.core.eventbus.Message;

@Component
public class MarketDataDAO extends VertxSQLDataAccess<Ticker> {
	private Logger log = LoggerFactory.getLogger(MarketDataDAO.class);

	public MarketDataDAO(DBConfig config) {
		super(Ticker.class, config);
	}

	@Override
	protected String getTableName() {
		return "Ticker";
	}
	
	//Since this is latency sensitive, JsonObject.mapFrom will not be used
	//because it will have an overhead on speed but not much gain in shortening
	//the code.
	@Override
	public JsonArray toJsonArray(Ticker ticker) {
		return new JsonArray()
				.add(ticker.getSymbol())
				.add(ticker.getOpen())
				.add(ticker.getClose())
				.add(ticker.getHigh())
				.add(ticker.getLow());
	}
	
	//Latency is priority here so I will recode everything without getSymbol
	//instead of calling toJsonArray then remove index 0
	@Override
	public JsonArray noKeyJsonArray(Ticker ticker) {
		return new JsonArray()
				.add(ticker.getOpen())
				.add(ticker.getClose())
				.add(ticker.getHigh())
				.add(ticker.getLow());
	}
	
	@Override
	public void executeCreate() {
		String s = "CREATE TABLE IF NOT EXISTS " + getTableName()
			+" (symbol VARCHAR(64) IDENTITY, open DECIMAL(11,4), close DECIMAL(11,4),"
			+" high DECIMAL(11,4), low DECIMAL(11,4))";
		
		this.jdbc.rxGetConnection()
			.flatMap(conn -> {
				Single<UpdateResult> result = conn.rxUpdate(s);
				return result.doAfterTerminate(conn::close);
			})
			.subscribe(result -> {
				log.info("Create table "+getTableName()+ " successful.");
			}, error -> {
				log.error("Error creating table "+getTableName()+"!\n"
						+error.getMessage());
			});
	}
	
	@Override
	public void insert(Message<JsonObject> message) {
		DataAccessMessage<Ticker> tickerMessage = new DataAccessMessage<>(message.body());
		Ticker ticker = tickerMessage.getModel();
		insert(ticker, next -> {
			if (isTransactionFailed(next, tickerMessage) == false){
				tickerMessage.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(tickerMessage));
		});
	}

	@Override
	public void select(Message<JsonObject> message) {
		DataAccessMessage<Ticker> tickerMessage = new DataAccessMessage<>(message.body());
		select(tickerMessage.getCriteria(), tickerMessage.getParameters(), next -> {
			if (isTransactionFailed(next, tickerMessage) == false){
				tickerMessage.setRecords(next.result());
			}
			message.reply(JsonObject.mapFrom(tickerMessage));
		});
	}

	@Override
	public void update(Message<JsonObject> message) {
		DataAccessMessage<Ticker> msgTicker = new DataAccessMessage<>(message.body());
		msgTicker.setKey(msgTicker.getModel().getSymbol());
		String sql = "UPDATE "+getTableName()+" SET "
				+ "open=?, close=?, high=?, low=? "
				+ "WHERE symbol="+msgTicker.getKey();
		update(msgTicker.getKey(), sql, msgTicker.getModel(), next -> {
			if (isTransactionFailed(next, msgTicker) == false) {
				msgTicker.setModel(next.result());
			}
			message.reply(JsonObject.mapFrom(msgTicker));
		});
	}
	
	@Override
	public void delete(Message<JsonObject> message) {
		DataAccessMessage<Ticker> msgTicker = new DataAccessMessage<>(message.body());
		msgTicker.setCriteria("symbol="+msgTicker.getModel().getSymbol());
		
		delete("id=?", new JsonArray().add(msgTicker.getModel().getSymbol()), 
			next -> {
				if (isTransactionFailed(next, msgTicker) == false) {
					msgTicker.setAffectedRecords(next.result());
				}
				message.reply(JsonObject.mapFrom(msgTicker));
			});
	}
}
