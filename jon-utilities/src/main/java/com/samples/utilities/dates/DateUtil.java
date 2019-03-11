package com.samples.utilities.dates;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {
//	private static Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	public static String getDate(Instant instant) {
		DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd")
				.withZone(ZoneId.systemDefault());
		
		return formatter.format(instant);
	}
}
