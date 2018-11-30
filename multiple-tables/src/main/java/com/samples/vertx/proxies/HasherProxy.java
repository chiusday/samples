package com.samples.vertx.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.samples.vertx.proxies.model.HashingRequest;
import com.samples.vertx.proxies.model.HashingResponse;

@FeignClient(name="hasher", url="localhost:31012")
public interface HasherProxy {

	@PostMapping("/hasher/hash")
	public HashingResponse hash(@RequestBody HashingRequest request);

}
