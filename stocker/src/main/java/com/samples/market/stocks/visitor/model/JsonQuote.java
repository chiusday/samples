package com.samples.market.stocks.visitor.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.visitor.interfaces.IVisitor;
import com.samples.market.stocks.visitor.interfaces.iVisitorModel;

import io.vertx.core.json.JsonObject;

public class JsonQuote implements iVisitorModel {

	protected String symbol;
	protected JsonObject data;
	protected JsonObject quote;
	protected Set<String> fields;
	protected List<HistoricalTicker> historicalQuotes;
	
	public JsonQuote() {
		this.data = new JsonObject();
		this.quote = new JsonObject();
		this.fields = new HashSet<>();
		this.historicalQuotes = new ArrayList<>();
		this.fields.add("open");
		this.fields.add("high");
		this.fields.add("low");
	}
	
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

	public List<HistoricalTicker> getHistoricalQuotes() {
		return historicalQuotes;
	}

	public void setHistoricalQuotes(List<HistoricalTicker> historicalQuotes) {
		this.historicalQuotes = historicalQuotes;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
