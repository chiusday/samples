package com.samples.vertx.reactive.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.samples.vertx.reactive.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public class TestUser {
	private String userUrl;
	private RestTemplate rest;
	
	@LocalServerPort
	int port;
	
	@Before
	public void setupTest(){
		rest = new RestTemplate();
		userUrl = String.format("http://localhost:%s/vertxrx/user", port);
	}

	@Test
	public void testFind1User(){
		User user = createUserModel();
		//Create record first
		System.out.println("About to call the rest handler");
		ResponseEntity<User> inserted = rest.postForEntity(userUrl, user, User.class);
		//System.out.println("inserted status code: " + inserted.getStatusCode());
		Assert.assertTrue(inserted.getStatusCode().equals(HttpStatus.CREATED));
		ResponseEntity<User> entity = rest.getForEntity(userUrl+"/1", User.class);
		Assert.assertTrue(entity.getStatusCode().equals(HttpStatus.OK));
		Assert.assertTrue(entity.getBody().getName().equals(user.getName()));
	}
	
	private User createUserModel(){
		User user = new User();
		user.setId(1L);
		user.setGroupId(1);
		user.setName("Jon");
		user.setPassword("Sample01");
		
		return user;
	}
	
	
}
