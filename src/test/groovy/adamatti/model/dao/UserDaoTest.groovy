package adamatti.model.dao

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

import adamatti.BaseTest
import adamatti.model.entity.User

class UserDaoTest extends BaseTest {
	@Autowired
	private UserDAO userDao
	
	@Autowired
	private MongoTemplate mongoTemplate
	
	@Test
	public void testUsingDao(){
		log.debug("Test started")
		
		assert userDao
		
		//Insert
		log.debug("Insert")
		User record = [lastName:"Adamatti ${System.nanoTime()}"]
		record = userDao.save(record)
		
		//Update
		log.debug("Update")
		record.firstName = "Marcelo"
		record = userDao.save(record)
		
		//Search
		/*
		log.debug("Search")
		record = tiddlerDao.find{it.lastName == "Marcelo"}
		assert record
		*/
		
		//Delete
		log.debug("Delete")
		userDao.delete(record)
		
		log.debug("Test ended")
	}
	
	@Test
	public void testQuery1(){
		Query query = new Query(Criteria.where("firstName").is("Marcelo"))
		def a = mongoTemplate.findOne(query, User.class)
		println a
	}
	
	@Test
	public void testQuery2(){
		BasicQuery query = new BasicQuery("{firstName: 'Marcelo'}")
		def a = mongoTemplate.findOne(query, User.class)
		println a
	}
}
