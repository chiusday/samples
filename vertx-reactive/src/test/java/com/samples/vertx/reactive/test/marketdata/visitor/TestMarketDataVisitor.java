package com.samples.vertx.reactive.test.marketdata.visitor;

import static com.samples.vertx.reactive.test.helper.DataBuilder.createHistoricalQuote;
import static com.samples.vertx.reactive.test.helper.DataBuilder.createHistoricalQuotes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.enums.DBOperations;
import com.samples.vertx.model.DataAccessMessage;
import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.verticle.DataAccessInterchange;
import com.samples.vertx.reactive.visitor.HistoricalTickerBatchAddResponseVisitor;
import com.samples.vertx.reactive.visitor.HistoricalTickersVisitor;
import com.samples.vertx.reactive.visitor.MarketDataAddResponseVisitor;
import com.samples.vertx.reactive.visitor.interfaces.BatchRxResponseVisitor;
import com.samples.vertx.reactive.visitor.model.BatchRxResponse;
import com.samples.vertx.reactive.visitor.model.RxResponse;
import com.samples.vertx.reactive.visitor.model.Tickers;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestMarketDataVisitor {
	@Autowired
	private MarketDataAddResponseVisitor<HistoricalTicker> historicalTickerAddRxResponseVisitor;
	
	@Autowired
	private HistoricalTickersVisitor historicalTickersVisitor;
	
	@Autowired
	private HistoricalTickerBatchAddResponseVisitor historicalTickerBatchAdd;
	
	@Autowired
	private DataAccessInterchange dataAccessInterchange;
	
	@Autowired
	private AppConfig appConfig;
	
	private DataAccessMessage<HistoricalTicker> msg;
	private EventBus eventBus;
	
	@Before
	public void setup() {
		msg = new DataAccessMessage<>(HistoricalTicker.class);
		eventBus = dataAccessInterchange.getRxVertx().eventBus();
	}
	
	@Test
	public void testAddHistoricalQuoteRxResponseVisitor() {
		RxResponse<HistoricalTicker> response =  new RxResponse<>();
		msg.setModel(createHistoricalQuote());
		msg.setOperation(DBOperations.insert);
		response.setSingle(getResponseSingle(msg));
		response.accept(historicalTickerAddRxResponseVisitor);
		
		HttpStatus success = response.getResponseEntity().getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, success);
	}
	
	@Test
	public void testHistoricalTickerVisitor() {
		Tickers<HistoricalTicker> tickers = getProcessedTickers();
		
		Assert.assertNotNull(tickers.getListJsonArray());
		Assert.assertFalse(tickers.getListJsonArray().isEmpty());
	}
	
	@Test
	public void testHistoricalTickerBatchAddResponseVisitor() {
		BatchRxResponse<HistoricalTicker> response = 
				testBatchAddResponseVisitor(historicalTickerBatchAdd);
		
		Assert.assertEquals(HttpStatus.CREATED, response.getResponseEntity().getStatusCode());
		Assert.assertFalse(response.isHasError());
	}
	
	private BatchRxResponse<HistoricalTicker> testBatchAddResponseVisitor
			(BatchRxResponseVisitor<HistoricalTicker> batchAddResponseVisitor) {
		
		BatchRxResponse<HistoricalTicker> response = new BatchRxResponse<>();
		msg.setListJsonArray(getProcessedTickers().getListJsonArray());
		msg.setOperation(DBOperations.batchInsert);
		response.setSingle(getResponseSingle(msg));
		response.accept(batchAddResponseVisitor);
		
		return response;
	}
	
	private Single<Message<JsonObject>> getResponseSingle
			(DataAccessMessage<HistoricalTicker> msg) {
		
		return eventBus.rxSend(appConfig.getAddressMarketInfo(), JsonObject.mapFrom(msg));
	}
	
	private Tickers<HistoricalTicker> getProcessedTickers() {
		Tickers<HistoricalTicker> tickers = new Tickers<>();
		tickers.setTickers(createHistoricalQuotes());
		tickers.accept(historicalTickersVisitor);
		
		return tickers;
	}
}
