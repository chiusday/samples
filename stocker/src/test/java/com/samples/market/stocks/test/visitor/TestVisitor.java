package com.samples.market.stocks.test.visitor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.Statics;
import com.samples.market.stocks.interfaces.DataSource;
import com.samples.market.stocks.visitor.HistoricalTickerListVisitor;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;
import com.samples.market.stocks.visitor.model.JsonHistoricalTicker;

import io.vertx.core.json.JsonObject;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestVisitor {
	@Autowired
	private Statics statics;

	@Autowired
	private DataSource cloudDataSource;
	
	@Autowired
	private HistoricalTickerListVisitor visitor;
	
	private String symbol = "MSFT";

	@Test
	public void TestHistoricalTickerListVisitor() {
		String data = cloudDataSource.getData(symbol);
		JsonObject raw = new JsonObject(data).getJsonObject
				(statics.getTimeSeries().getDaily());
		JsonQuote<HistoricalTicker> jsonQuote = new JsonHistoricalTicker(symbol, raw);
		HistoricalTickerListVisitorModel hsitoricalTickerVisitorModel = 
				new HistoricalTickerListVisitorModel();
		hsitoricalTickerVisitorModel.setConvertible(jsonQuote);
		hsitoricalTickerVisitorModel.accept(visitor);
		
		Assert.assertFalse(hsitoricalTickerVisitorModel.isHasError());
	}
}
