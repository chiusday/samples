package com.samples.vertx.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.samples.vertx.proxies.model.HashingRequest;
import com.samples.vertx.proxies.model.HashingResponse;

@FeignClient(name="hasher")
@RibbonClient(name="hasher")
public interface HasherProxy {

	@PostMapping("/hasher/hash")
	public HashingResponse hash(@RequestBody HashingRequest request);

}
