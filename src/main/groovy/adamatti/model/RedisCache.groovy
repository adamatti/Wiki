package adamatti.model

import groovy.transform.CompileStatic
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component

import javax.annotation.Resource

@Component
@CompileStatic
class RedisCache {
	@Resource(name="redisTemplate")
	private ValueOperations valueOps

	void delete(String key) {
		valueOps.set(key, null)
	}

	String get(String key){
		return valueOps.get(key)
	}

	void set(String key, String value) {
		valueOps.set(key, value)
	}
}
