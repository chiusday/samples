package com.samples.market.stocks.test.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.model.TickerList;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.converter.JsonToHistoricalTicker;
import com.samples.market.stocks.interfaces.DataSource;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;
import com.samples.market.stocks.visitor.model.JsonHistoricalTicker;

import io.vertx.core.json.JsonObject;
import org.junit.Assert;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestConverter {
	@Autowired
	private Statics statics;

	@Autowired
	private DataSource cloudDataSource;

	@Autowired
	private JsonToHistoricalTicker converter;
	
	private String symbol = "MSFT";

	@Test
	public void TestHistoricalTickerListConverter() {
		String data = cloudDataSource.getData(symbol);
		JsonObject rawData = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		
		JsonQuote<HistoricalTicker> jsonQuote = new JsonHistoricalTicker(symbol, rawData);
		TickerList<HistoricalTicker> tickerList = converter.convertFrom(jsonQuote);
		
		Assert.assertEquals(symbol, tickerList.getSymbol());
		Assert.assertFalse(tickerList.getTickerList().isEmpty());
	}
}