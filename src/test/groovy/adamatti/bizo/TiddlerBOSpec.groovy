package adamatti.bizo

import adamatti.model.RedisCache
import adamatti.model.dao.TiddlerDAO
import adamatti.model.entity.Tiddler
import spock.lang.Specification

class TiddlerBOSpec extends Specification {
	def "happy path"() {
		given:
			def tiddlerDao = Mock(TiddlerDAO)
			def redisCache = Mock(RedisCache)
			def tiddlerBO = new TiddlerBO(tiddlerDao: tiddlerDao, redisCache: redisCache)
			def tiddler = new Tiddler(name: "home")

			tiddlerDao.save(_) >> tiddler
		when:
			def response = tiddlerBO.save("home2", tiddler)
		then:
			response != null
			1 * tiddlerDao.delete(_)
			1 * redisCache.delete("home")
	}
}
