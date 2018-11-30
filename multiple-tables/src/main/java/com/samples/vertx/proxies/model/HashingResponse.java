package com.samples.vertx.proxies.model;

public class HashingResponse {
	private String encryptedText;
	
	public HashingResponse() {}

	public HashingResponse(String encryptedText) {
		this.encryptedText = encryptedText;
	}

	public String getEncryptedText() {
		return encryptedText;
	}

	public void setEncryptedText(String encryptedText) {
		this.encryptedText = encryptedText;
	}
	
}
