package com.samples.utilities.test.dates;

import static com.samples.utilities.dates.DateUtil.getDate;
import java.time.Instant;

import org.junit.Test;

import junit.framework.Assert;

public class TestDateUtil {

	@Test
	public void TestGetDate() {
		String sToday = "2019-02-10";
		Instant today = Instant.parse(sToday+"T00:00:00.00Z");
		String date = getDate(today);
		Assert.assertEquals(date, sToday);
	}
}
