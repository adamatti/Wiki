package adamatti.bizo

import spock.lang.Specification

class MarkdownProcessorSpec extends Specification {
	def "happy path"(){
		expect:
			MarkdownProcessor.instance.process("hello") != null
	}
}
