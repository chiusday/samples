package com.samples.vertx;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix="app-config")
public class AppConfig {
	private String port;
	private String url;
	private String user;
	private String password;
	private String addressUser;
	private String addressMarketInfo;
	private String headerContentType;
	private String headerApplicationJson;
	
	public String getPort(){ return this.port; }
	public void setPort(String port){ this.port = port; }
	public String getUrl(){ return this.url; }
	public void setUrl(String url){ this.url = url; }
	
	public String getUser(){ return this.user; }
	public void setUser(String username){ this.user = username; }
	
	public String getPassword(){ return this.password; }
	public void setPassword(String password){ this.password = password; }
	
	public String getAddressUser() {
		return addressUser;
	}
	public void setAddressUser(String addressUser) {
		this.addressUser = addressUser;
	}
	public String getAddressMarketInfo() {
		return addressMarketInfo;
	}
	public void setAddressMarketInfo(String addressMarketInfo) {
		this.addressMarketInfo = addressMarketInfo;
	}
	public String getHeaderContentType() {
		return headerContentType;
	}
	public void setHeaderContentType(String headerContentType) {
		this.headerContentType = headerContentType;
	}
	public String getHeaderApplicationJson() {
		return headerApplicationJson;
	}
	public void setHeaderApplicationJson(String headerApplicationJson) {
		this.headerApplicationJson = headerApplicationJson;
	}
}
