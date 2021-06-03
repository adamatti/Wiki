package groovy

import spock.lang.Specification
import groovy.time.TimeCategory

/**
 * Test created because some version updates have breaking changes
 */
class LangSpec extends Specification {
	def "date operations" () {
		when:
			def result = use(TimeCategory){
				new Date() + 1.days
			}
		then:
			result instanceof Date
	}
}
