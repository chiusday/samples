package com.samples.market.model;

public class GetBySymbolRequest {
	protected String symbol;
	
	public GetBySymbolRequest() {}
	
	public GetBySymbolRequest(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
