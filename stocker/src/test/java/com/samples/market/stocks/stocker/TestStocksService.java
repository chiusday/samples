package com.samples.market.stocks.stocker;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.service.HistoricalTickerService;
import com.samples.market.stocks.visitor.interfaces.JsonQuote;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteVisitor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStocksService {
	@Autowired
	private HistoricalTickerService stocksService;
	@Autowired
	private JsonQuoteVisitor jsonDailyQuoteVisitor;
	
	@Test
	public void testGetHistoricalTicker() {
		JsonQuote<HistoricalTicker> quote = stocksService.getHistoricalQuote("MSFT");
		quote.accept(jsonDailyQuoteVisitor);
		List<HistoricalTicker> tickers = quote.getQuotes();
		assertFalse(tickers.isEmpty());
	}
}
