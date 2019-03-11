package com.samples.utilities.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClassUtil {
	private static Logger log = LoggerFactory.getLogger(ClassUtil.class);

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClazz(T object) {
		try {
			return (Class<T>) Class.forName(object.getClass().getName());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getNewT(T object) {
		if (object != null) {
			try {
				return (T) object.getClass().newInstance();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return null;
	}
}
