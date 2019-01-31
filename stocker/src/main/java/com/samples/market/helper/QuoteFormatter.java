package com.samples.market.helper;

import java.util.Set;
import java.util.Map.Entry;

import io.vertx.core.json.JsonObject;

public class QuoteFormatter {

	public static JsonObject format1(Set<String> fields, JsonObject rawData) {
		JsonObject reply = new JsonObject();
		for (Entry<String,Object> elem : rawData.getMap().entrySet()) {
			fields.forEach(field -> {
				if (elem.getKey().contains(field)) {
					reply.put(field, elem.getValue());
				}
			});
//			if (elem.getKey().contains("open")) {
//				reply.put("open", elem.getValue());
//			} else if (elem.getKey().contains("close")) {
//				reply.put("close", elem.getValue());
//			} else if (elem.getKey().contains("high")) {
//				reply.put("high", elem.getValue());
//			} else if (elem.getKey().contains("low")) {
//				reply.put("low", elem.getValue());
//			}
		}
		
		return reply;
	}
}
