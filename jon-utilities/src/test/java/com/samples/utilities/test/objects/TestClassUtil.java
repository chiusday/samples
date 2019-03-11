package com.samples.utilities.test.objects;

import static com.samples.utilities.objects.ClassUtil.getClazz;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

public class TestClassUtil {

	@Test
	public void testGetClazz() {
		Class<Instant> clazz = getClazz(Instant.now());
		Assert.assertEquals(clazz, Instant.class);
	}
}
