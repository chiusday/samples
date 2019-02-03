package com.samples.market.stocks.stocker;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.service.StocksService;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteVisitor;
import com.samples.market.stocks.visitor.model.JsonQuote;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStocksService {
	@Autowired
	private StocksService stocksService;
	@Autowired
	private JsonQuoteVisitor jsonDailyQuoteVisitor;
	
	@Test
	public void testGetHistoricalTicker() {
		JsonQuote quote = stocksService.getHistoricalQuotes("TSLA");
		quote.accept(jsonDailyQuoteVisitor);
		List<HistoricalTicker> tickers = quote.getHistoricalQuotes();
		assertFalse(tickers.isEmpty());
	}
}
