package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;

public interface IVisitor<T> {

	default void visit(ConvertibleResponse<T> visitorModel) {
		throw new UnsupportedOperationException
			("visit(HistoricalTickerListVisitorModel) is not supported.");
	}

	default void visit(JsonQuoteConvertibleResponse<T> visitorModel) {
		throw new UnsupportedOperationException
			("visit(JsonQuoteConvertibleResponse) is not supported.");
	}
	
	default void visit(	HistoricalTickerListVisitorModel visitorModel) {
		throw new UnsupportedOperationException
			("visit(JsonQuoteConvertibleResponse) is not supported.");
	}
}
