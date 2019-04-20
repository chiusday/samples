package com.samples.vertx.reactive.dao;

import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;
import com.samples.vertx.reactive.DBConfig;
import com.samples.vertx.reactive.interfaces.TickerVertxSQLDataAccess;

import io.vertx.core.json.JsonArray;

@Service
public class IntradayTickerDAO extends TickerVertxSQLDataAccess<IntradayTicker> {
	public IntradayTickerDAO(DBConfig config) {
		super(IntradayTicker.class, config);
	}
	
	@Override
	protected String getTableName() {
		return "IntradayTicker";
	}

	@Override
	protected String getInsertSql() {
		return "INSERT INTO " + getTableName()
			+ " (symbol, open, close, high, low, price_time)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String getCreateSql() {
		return "CREATE TABLE IF NOT EXISTS " + getTableName()
			+" (id IDENTITY NOT NULL PRIMARY KEY, symbol VARCHAR(64),"
			+" open DECIMAL(11,4), close DECIMAL(11,4), high DECIMAL(11,4),"
			+" low DECIMAL(11,4), price_time Date)";	
	}


	@Override
	protected String getUpdateSql(String keyValue) {
		return "UPDATE "+getTableName()+" SET "
			+ "open=?, close=?, high=?, low=?, price_time=? "
			+ "WHERE symbol="+keyValue;
	}

	@Override
	public JsonArray noKeyJsonArray(IntradayTicker model) {
		return new JsonArray()
				.add(model.getSymbol())
				.add(model.getOpen())
				.add(model.getClose())
				.add(model.getHigh())
				.add(model.getLow())
				.add(model.getPriceTime());		
	}

	@Override
	public JsonArray toJsonArray(IntradayTicker model) {
		return noKeyJsonArray(model);
	}
}
