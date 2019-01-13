package com.samples.vertx.reactive.visitor.interfaces;


public interface IVisitorModel {
	
	default public void accept(IVisitor visitor) {
		throw new UnsupportedOperationException("accept("+visitor.getClass().getName()
				+") is not implemented.");
	}
}
