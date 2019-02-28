package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.model.HistoricalTicker;
import com.samples.market.stocks.converter.interfaces.IConvertible;

public abstract class ConvertibleResponse<T> extends BaseVisitorModelResp<T> 
		implements IConvertible {

	protected JsonQuote<HistoricalTicker> jsonQuote;
	
	public JsonQuote<HistoricalTicker> getJsonQuote() {
		return jsonQuote;
	}

	public void setJsonQuote(JsonQuote<HistoricalTicker> jsonQuote) {
		this.jsonQuote = jsonQuote;
	}

	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}
