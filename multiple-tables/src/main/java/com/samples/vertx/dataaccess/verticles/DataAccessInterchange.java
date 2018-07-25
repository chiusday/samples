package com.samples.vertx.dataaccess.verticles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.samples.vertx.AppConfig;
import com.samples.vertx.dataaccess.DataAccessMessageRouter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

@Component
public class DataAccessInterchange extends AbstractVerticle {
	private AppConfig config;
	
	@Autowired
	DataAccessMessageRouter messageRouter;
	
	public DataAccessInterchange(AppConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void start() throws Exception {
		super.start();
		messageRouter.setDataConnections(vertx);
		vertx.eventBus().<JsonObject>consumer(config.getAddressUser()).handler(this::processRequest);
	}
	
	private void processRequest(final Message<JsonObject> message){
		messageRouter.routeMessage(message);
	}
}
