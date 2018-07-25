package com.samples.vertx.test;

import java.time.Instant;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.samples.vertx.AppConfig;
import com.samples.vertx.ApplicationEntryPoint;
import com.samples.vertx.dataaccess.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVertxSQLDataAccess {
	private String userUrl;
	private RestTemplate rest;
	
	@Autowired
	private AppConfig config;
	
	@Before
	public void setupTest(){
		rest = new RestTemplate();
		userUrl = String.format("http://localhost:%s/rootContext/MulitpleTable/User", config.getPort());
		//Wait for the verticles to finish it's start process
		//Do this gracefully when have time
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFind1User(){
		User user = createUserModel();
		//Create record first
		System.out.println("About to call the rest handler");
		ResponseEntity<User> inserted = rest.postForEntity(userUrl, user, User.class);
		//System.out.println("inserted status code: " + inserted.getStatusCode());
		Assert.isTrue(inserted.getStatusCode().equals(HttpStatus.CREATED));
		ResponseEntity<User> entity = rest.getForEntity(userUrl+"/1", User.class);
		Assert.isTrue(entity.getStatusCode().equals(HttpStatus.OK));
		Assert.isTrue(entity.getBody().getName().equals(user.getName()));
	}
	
	private User createUserModel(){
		User user = new User();
		user.setId(1L);
		user.setGroupId(1);
		user.setName("Jon");
		user.setCreationDate(new Date(new java.util.Date().getTime()));
		
		return user;
	}
	
}
