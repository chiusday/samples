package com.samples.vertx;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.samples.vertx.dataaccess.UserDataAccess;
import com.samples.vertx.dataaccess.UserRestHandler;
import com.samples.vertx.dataaccess.model.User;
import com.samples.vertx.dataaccess.verticles.DataAccessInterchange;
import com.samples.vertx.dataaccess.verticles.DataAccessRest;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

@SpringBootApplication
public class ApplicationEntryPoint {
	public static final void main(String args[]){
		SpringApplication.run(ApplicationEntryPoint.class, args);
	}
	
	@PostConstruct
	public void deployVerticles(){
		VertxOptions options = new VertxOptions();
		options.setBlockedThreadCheckInterval(1000*60*60);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle(dataAccessInterchange());
		vertx.deployVerticle(dataAccessRest());
	}
	
	@Bean
	public AppConfig appConfig(){
		return new AppConfig();
	}
	
	@Bean
	public DBConfig dbConfig(){
		return new DBConfig();
	}
	
	@Bean
	public UserDataAccess userDataAccess(){
		return new UserDataAccess(dbConfig());
	}
	
	@Bean
	public DataAccessInterchange dataAccessInterchange(){
		return new DataAccessInterchange(appConfig());
	}
	
	@Bean
	public DataAccessRest dataAccessRest(){
		return new DataAccessRest(appConfig());
	}

	@Bean
	public UserRestHandler userRestHandler(){
		return new UserRestHandler(appConfig(), dataAccessInterchange().getVertx());
	}
	
}
