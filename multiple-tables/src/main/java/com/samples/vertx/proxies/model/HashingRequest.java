package com.samples.vertx.proxies.model;

public class HashingRequest {
	private String originalText;
	
	public HashingRequest() {}

	public HashingRequest(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	
}
