package com.samples.market.stocks.visitor.interfaces;

public abstract class JsonQuoteConvertibleResponse<T> extends ConvertibleResponse<T> {

	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}
