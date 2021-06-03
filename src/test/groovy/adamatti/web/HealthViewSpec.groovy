package adamatti.web

import adamatti.model.RedisCache
import adamatti.model.dao.TiddlerDAO
import groovy.json.JsonSlurper
import spark.Request
import spark.Response
import spock.lang.Specification

class HealthViewSpec extends Specification {
	def "happy path"() {
		given:
			def req = Mock(Request)
			def res = Mock(Response)
			def tiddlerDAO = Mock(TiddlerDAO)
			def redisCache = Mock(RedisCache)
			def view = new HealthView(tiddlerDAO: tiddlerDAO, redisCache: redisCache)

			 tiddlerDAO.count() >> 1
		when:
			def response = view.healthController(req, res) as String
			def json = new JsonSlurper().parseText(response) as Map
		then:
			json.mongo.status == "OK"
			json.redis.status == "OK"
	}
}
