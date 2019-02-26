package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.model.Ticker;

public interface IVisitor {

	default <T extends Ticker> void visit(JsonQuote<T> model) {
		throw new UnsupportedOperationException("visit(JsonQuote)");
	}
}
