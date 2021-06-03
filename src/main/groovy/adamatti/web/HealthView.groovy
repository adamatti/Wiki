package adamatti.web

import adamatti.model.RedisCache
import adamatti.model.dao.TiddlerDAO
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark

import javax.annotation.PostConstruct

@Slf4j
@Component
class HealthView {
	@Autowired
	private TiddlerDAO tiddlerDAO

	@Autowired
	private RedisCache redisCache

	protected Closure healthController = { Request req, Response res->
		res.header("Content-Type", "application/json")

		Long mongoCount = countMongo()

		def response = [
			mongo: [
				status:  mongoCount > 0 ? "OK" : "Error",
				count: mongoCount
			],
			redis: [
				status: testRedis() ? "OK" : "Error"
			]
		]

		def json = JsonOutput.toJson(response)
		return JsonOutput.prettyPrint(json)
	}

	@PostConstruct
	void init(){
		Spark.get("/health", healthController)
	}

	private Boolean testRedis(){
		try {
			redisCache.set("healthChech", "OK")
			return true
		} catch (Throwable t){
			log.error("Error testing redis", t)
			return false
		}
	}

	private Long countMongo(){
		try {
			return tiddlerDAO.count()
		} catch (Throwable t) {
			log.error("Error counting mongo", t)
			return 0
		}
	}
}
