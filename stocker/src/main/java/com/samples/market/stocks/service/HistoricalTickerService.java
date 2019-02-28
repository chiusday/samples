package com.samples.market.stocks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.converter.interfaces.JsonToTickerList;
import com.samples.market.stocks.interfaces.DataSource;
import com.samples.market.stocks.visitor.HistoricalTickerListVisitor;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;
import com.samples.market.stocks.visitor.model.JsonHistoricalTicker;

import io.vertx.core.json.JsonObject;

@Service
public class HistoricalTickerService {
	@Autowired
	private Statics statics;

	//online DataSource
	@Autowired
	private DataSource cloudDataSource;
	
	//offline DataSource
//	@Autowired
//	private DataSource staticDataSource;
	
	@Autowired
	private JsonToTickerList<HistoricalTicker> converter;
	
	@Autowired
	private HistoricalTickerListVisitor visitor;

	public List<HistoricalTicker> getHistoricalQuote(String symbol) {
		String data = cloudDataSource.getData(symbol);
//		String data = staticDataSource.getData();
		JsonObject rawData = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		
		JsonQuote<HistoricalTicker> jsonQuote = new JsonHistoricalTicker(symbol, rawData);
//		jsonQuote.accept(jsonDailyQuoteVisitor);
		
		return converter.convertFrom(jsonQuote).getTickerList();
	}
	
	public ResponseEntity<Object> getHistoricalTickers(String symbol) {
		String data = cloudDataSource.getData(symbol);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		JsonQuote<HistoricalTicker> jsonQuote = new JsonHistoricalTicker(symbol, raw);
		HistoricalTickerListVisitorModel hsitoricalTickerVisitorModel = 
				new HistoricalTickerListVisitorModel();
		hsitoricalTickerVisitorModel.setJsonQuote(jsonQuote);
		hsitoricalTickerVisitorModel.accept(visitor);
		
		return hsitoricalTickerVisitorModel.getResponseEntity();
	}
		
}
