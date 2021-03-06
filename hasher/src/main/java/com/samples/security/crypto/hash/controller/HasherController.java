package com.samples.security.crypto.hash.controller;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samples.security.crypto.hash.AppConfig;
import com.samples.security.crypto.hash.model.HashRequest;
import com.samples.security.crypto.hash.model.HashResponse;
import com.samples.security.crypto.hash.service.HashService;

@RestController
public class HasherController {
	private final static Logger LOG = LoggerFactory.getLogger(HasherController.class);
	
	@Autowired
	private HashService hashService;
	
	@Autowired
	private AppConfig config;
	
	@PostMapping("/hash")
	public HashResponse hash(@RequestBody HashRequest request) throws Exception {
		HashResponse response = new HashResponse();
		try {
			String hashed = hashService.hash(request.getOriginalText());
			response.setEncryptedText(hashed);
			LOG.info("returning: {}", hashed);
			return response;
		} catch (NoSuchAlgorithmException noSuchAlgo){
			LOG.error(config.getShaAlgo() + "is not a valid algorithm",
					noSuchAlgo);
			throw noSuchAlgo;
		} catch (Exception e){
			LOG.error("Unexpected error:\n", e);
			throw e;
		}
	}
}
