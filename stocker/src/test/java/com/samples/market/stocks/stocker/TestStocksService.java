package com.samples.market.stocks.stocker;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.models.HistoricalTicker;
import com.samples.market.stocks.service.StocksService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStocksService {
	@Autowired
	private StocksService stocksService;
	
	@Test
	public void testGetHistoricalTicker() {
		List<HistoricalTicker> tickers = stocksService.getHistoricalTicker("TSLA");
		assertFalse(tickers.isEmpty());
	}
}
