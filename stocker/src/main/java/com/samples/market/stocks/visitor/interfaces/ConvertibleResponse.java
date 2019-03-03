package com.samples.market.stocks.visitor.interfaces;

import com.samples.market.stocks.converter.interfaces.IConvertible;

@SuppressWarnings("rawtypes")
public abstract class ConvertibleResponse<T> extends BaseVisitorModelResp<T> 
		implements IConvertible {

	protected JsonQuote convertible;
	
	public JsonQuote getConvertible() {
		return convertible;
	}

	public void setConvertible(JsonQuote convertible) {
		this.convertible = convertible;
	}

	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}
