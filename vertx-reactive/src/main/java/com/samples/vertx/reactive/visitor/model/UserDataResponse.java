package com.samples.vertx.reactive.visitor.model;

import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.visitor.interfaces.IVisitor;

public class UserDataResponse extends BaseVisitorModelRxResp<User> {

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
