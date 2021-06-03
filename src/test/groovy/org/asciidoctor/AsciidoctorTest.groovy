package org.asciidoctor

import spock.lang.Specification

class AsciidoctorSpec extends Specification {
	def "test create"(){
		given:
			def asciidoctor = org.asciidoctor.Asciidoctor.Factory.create()
		expect:
			asciidoctor != null
	}
}
