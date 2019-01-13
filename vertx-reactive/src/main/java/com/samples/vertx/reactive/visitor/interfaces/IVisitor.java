package com.samples.vertx.reactive.visitor.interfaces;

import com.samples.vertx.reactive.visitor.model.UserDataResponse;

public interface IVisitor {

	default public void visit(UserDataResponse model) {
		throw new UnsupportedOperationException
			("visit(UserGetResponse) is not implemented");
	}
}
