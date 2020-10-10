package org.asciidoctor

import org.junit.Test
import spark.utils.Assert

class AsciidoctorTest {
	@Test
	void testCreate(){
		def asciidoctor = org.asciidoctor.Asciidoctor.Factory.create()
		Assert.notNull(asciidoctor)
	}
}
