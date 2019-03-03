package com.samples.market.model;

import java.time.Instant;

public class IntradayTicker extends Ticker {
	private Instant time;

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}
}
