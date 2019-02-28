package com.samples.market.stocks.visitor.interfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.samples.market.model.Ticker;

import io.vertx.core.json.JsonObject;

public abstract class JsonQuote<T extends Ticker> {	
	protected Class<T> type;
	protected String symbol;
	protected JsonObject data;
	protected JsonObject quote;
	protected Set<String> fields;
	protected List<T> historicalQuotes;
	
	public JsonQuote(Class<T> type) {
		this.type = type;
		this.data = new JsonObject();
		this.quote = new JsonObject();
		this.fields = new HashSet<>();
		this.historicalQuotes = new ArrayList<>();
		this.fields.add("open");
		this.fields.add("high");
		this.fields.add("low");
	}
	
	public Class<T> getType() { return this.type; }
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public Set<String> getFields() {
		return fields;
	}

	public void setFields(Set<String> fields) {
		this.fields = fields;
	}

	public JsonObject getQuote() {
		return quote;
	}
	public void setQuote(JsonObject quote) {
		this.quote = quote;
	}

	public List<T> getQuotes() {
		return historicalQuotes;
	}

	public void setQuotes(List<T> historicalQuotes) {
		this.historicalQuotes = historicalQuotes;
	}
}
