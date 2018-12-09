package com.samples.vertx.dataaccess.verticles;

import org.springframework.beans.factory.annotation.Autowired;

import com.samples.vertx.AppConfig;
import com.samples.vertx.interfaces.IRestHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class DataAccessRest extends AbstractVerticle {
	public final static String CONTENT_TYPE = "content-type";
	public final static String APPLICATION_JSON = "application/json; charset=utf-8";
	public final static String CONTENT_LENGTH = "content-length";
	public final static String DEFAULT_CONTENT = "<h2>Welcome to Multiple Table Data Access Asynchronously with Vertx</h2>";
	
	public static final String DATA_ACCESS_ADDRESS = "pflonline.dataaccess.processitem";

	private JsonObject config;
	
	@Autowired
	private IRestHandler userRestHandler;

	public DataAccessRest(AppConfig config) {
		super();
		if (config!=null)
			//add vertx's config key for port (http.port) for interoperability
			this.config = JsonObject.mapFrom(config).put("http.port", Integer.valueOf(config.getPort()));
		else
			this.config = config();
	}

	@Override
	public void start(Future<Void> future){
		
		startWebApp(http -> {
			completeStartUp(http, future);
		});
	}
	
	@Override
	public void stop() throws Exception {
		//put your cleanup here.
		//close any connections and custom threads
	}
	
	public void startWebApp(Handler<AsyncResult<HttpServer>> http){
		Router router = setupEndpoints();

		vertx.createHttpServer()
			.requestHandler(router)
			.listen(config.getInteger("http.port", 8896),
					http::handle);
		
		System.out.println("Data Access Router is hosted");
	}
	
	public Router setupEndpoints(){
		Router router = Router.router(vertx);
		
		router.route("/")
			.handler(routingContext -> {
				HttpServerResponse response = routingContext.response();
				response
					.putHeader(CONTENT_TYPE, "text/html")
					.end(DEFAULT_CONTENT);
			});
		
		router.route("/rootContext*").handler(BodyHandler.create());
		router.get("/rootContext/MulitpleTable/User/:id").handler(userRestHandler::select);
		router.post("/rootContext/MulitpleTable/User").handler(userRestHandler::insert);
		router.put("/rootContext/MulitpleTable/User/:id").handler(userRestHandler::update);
		router.delete("/rootContext/MulitpleTable/User:id").handler(userRestHandler::delete);

		return router;
	}
	
	public void completeStartUp(AsyncResult<HttpServer> http, Future<Void> future){
		if (http.failed()){
			future.fail(http.cause());
		} else {
			future.complete();
		}
	}


}
