package com.samples.vertx.dataaccess.model;

import java.sql.Date;

import io.vertx.core.json.JsonObject;

public class User {
	private Long id;
	private String name;
	private int groupId;
	private Date creationDate;
	
	public User(){}
	
	public User(JsonObject json){
		this.id = json.getLong("ID");
		this.name = json.getString("NAME");
		this.groupId = json.getInteger("GROUPID");
		this.creationDate = Date.valueOf(json.getString("CREATIONDATE"));
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
