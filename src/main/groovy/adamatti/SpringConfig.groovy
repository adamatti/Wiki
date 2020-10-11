package adamatti

import adamatti.commons.Resources
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoExceptionTranslator
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@EnableMongoRepositories(basePackages = ["adamatti.model.dao"])
class SpringConfig {
	private ConfigObject cfg = Resources.cfg

	@Bean
	MongoDatabaseFactory mongoDbFactory() {
		new SimpleMongoClientDatabaseFactory(cfg.mongo.url as String)
	}

	@Bean(name = "mongoTemplate")
	MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory){
		new MongoTemplate(mongoDbFactory)
	}

	@Bean
	MongoExceptionTranslator mongoExceptionTranslator(){
		new MongoExceptionTranslator()
	}

	@Bean
	RedisConnectionFactory jedisConnectionFactory(){
		new JedisConnectionFactory(
			usePool: true,
			hostName: cfg.redis.host,
			port: cfg.redis.port,
			password: cfg.redis.pass
		)
	}

	@Bean
	RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
		new RedisTemplate(connectionFactory: connectionFactory)
	}
}
