package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.stocks.visitor.model.JsonQuote;

public interface IVisitor {

	default void visit(JsonQuote model) {
		throw new UnsupportedOperationException("visit(JsonQuote)");
	}
}
