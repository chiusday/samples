package com.samples.vertx.reactive.verticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.samples.vertx.reactive.AppConfig;
import com.samples.vertx.reactive.service.DataAccessMessageRouter;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.Message;

@Component
public class DataAccessInterchange extends AbstractVerticle {
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private DataAccessMessageRouter messageRouter;

	@Override
	public void start() throws Exception {
		super.start();
		messageRouter.setDataConnections(vertx);
		vertx.eventBus().<JsonObject>consumer(appConfig.getAddressUser())
			.toFlowable().subscribe(this::processRequest);
	}
	
	private void processRequest(final Message<JsonObject> message){
		messageRouter.routeMessage(message);
	}
}
