package com.samples.vertx.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.enums.DBOperations;
import com.samples.vertx.reactive.model.DataAccessMessage;
import com.samples.vertx.reactive.model.Ticker;
import com.samples.vertx.reactive.verticle.DataAccessInterchange;
import com.samples.vertx.reactive.visitor.model.RxResponse;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

@Service
public class MarketDataService {
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private DataAccessInterchange dataAccessInterchange;
	
	public RxResponse<Ticker> addMarketData(Ticker ticker) {
		DataAccessMessage<Ticker> tickerMessage = new DataAccessMessage<>(Ticker.class);
		tickerMessage.setModel(ticker);
		tickerMessage.setOperation(DBOperations.insert);
		
		return processMarketData(tickerMessage);
	}

	public RxResponse<Ticker> getMarketData(String id) {
		DataAccessMessage<Ticker> tickerMessage = new DataAccessMessage<>(Ticker.class);
		tickerMessage.setCriteria("symbol=?");
		tickerMessage.setParameters(new JsonArray().add(id));
		tickerMessage.setOperation(DBOperations.select);
		
		return processMarketData(tickerMessage);
	}

	private RxResponse<Ticker> processMarketData(DataAccessMessage<Ticker> tickerMsg){
		RxResponse<Ticker> marketDataResponse = new RxResponse<>();
		EventBus eventBus = dataAccessInterchange.getRxVertx().eventBus();
		Single<Message<JsonObject>> response = eventBus.rxSend
				(appConfig.getAddressMarketInfo(), JsonObject.mapFrom(tickerMsg));
		marketDataResponse.setSingle(response);
		
		return marketDataResponse;
	}
}
