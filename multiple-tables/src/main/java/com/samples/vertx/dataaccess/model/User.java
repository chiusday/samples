package com.samples.vertx.dataaccess.model;

import io.vertx.core.json.JsonObject;

public class User {
	private Long id;
	private String name;
	private int groupId;
	private String password;
	
	public User(){}
	
	public User(JsonObject json){
		this.id = json.getLong("ID");
		this.name = json.getString("NAME");
		this.groupId = json.getInteger("GROUPID");
		this.password = json.getString("PASSWORD");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
