package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.stocks.converter.interfaces.IConverter;

public abstract class ConvertibleResponseVisitor<T> implements IVisitor<T> {

	@Override
	public void visit(ConvertibleResponse<T> convertibleResponse) {
		IConverter converter = convertibleResponse.getConverter();
		convertibleResponse.setModel((T)converter.convertFrom
				(convertibleResponse.getJsonQuote()));
		convertibleResponse.setHasError(false);
	}
}
