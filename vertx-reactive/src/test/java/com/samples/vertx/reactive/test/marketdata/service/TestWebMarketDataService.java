package com.samples.vertx.reactive.test.marketdata.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.samples.market.model.HistoricalTicker;
import com.samples.vertx.reactive.service.WebMarketDataService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestWebMarketDataService {

	@Autowired
	private WebMarketDataService<HistoricalTicker> webHistoricalTickerSource;
}
