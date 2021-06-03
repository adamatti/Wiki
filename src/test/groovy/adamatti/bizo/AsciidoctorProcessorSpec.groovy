package adamatti.bizo

import spock.lang.Specification

class AsciidoctorProcessorSpec extends Specification {
	def "happy path"() {
		expect:
			AsciidoctorProcessor.instance.process("hello") != null
	}
}
