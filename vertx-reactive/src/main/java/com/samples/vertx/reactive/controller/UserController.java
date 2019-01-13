package com.samples.vertx.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.samples.vertx.reactive.model.User;
import com.samples.vertx.reactive.service.UserService;
import com.samples.vertx.reactive.visitor.UserAddResponseVisitor;
import com.samples.vertx.reactive.visitor.UserGetResponseVisitor;
import com.samples.vertx.reactive.visitor.model.UserDataResponse;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAddResponseVisitor userAddResponseVisitor;
	
	@Autowired
	private UserGetResponseVisitor userGetResponseVisitor;
	
	@PostMapping("/user")
	public ResponseEntity<Object> insertUser(@RequestBody User user){
		UserDataResponse userOpResponse = userService.add(user);
		userOpResponse.accept(userAddResponseVisitor);
		
		return userOpResponse.getResponseEntity();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUser(@PathVariable int id){
		UserDataResponse userOpResponse = userService.get(id);
		userOpResponse.accept(userGetResponseVisitor);
		
		return userOpResponse.getResponseEntity();
	}
}
