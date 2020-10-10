package adamatti.model.dao

import org.springframework.data.mongodb.repository.MongoRepository

import adamatti.model.entity.Tiddler

interface TiddlerDAO extends MongoRepository<Tiddler, String> {
	Tiddler findByName(String name)
}
