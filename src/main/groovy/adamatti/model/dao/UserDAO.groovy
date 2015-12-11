package adamatti.model.dao

import org.springframework.data.mongodb.repository.MongoRepository

import adamatti.model.entity.User

interface UserDAO extends MongoRepository<User, String>{

}
