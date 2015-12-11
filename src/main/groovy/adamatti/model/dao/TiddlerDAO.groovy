package adamatti.model.dao

import org.springframework.data.mongodb.repository.MongoRepository

import adamatti.model.entity.Tiddler


interface TiddlerDAO extends MongoRepository<Tiddler, String> {
	public Tiddler findByName(String name)	
}
