package com.samples.vertx.interfaces;

import io.vertx.ext.web.RoutingContext;

public interface IRestHandler {
	void insert(RoutingContext context);
	void select(RoutingContext context); 
	void update(RoutingContext context); 
	void delete(RoutingContext context); 	
}
