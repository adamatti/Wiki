package adamatti

import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.junit.Assert
import org.junit.Test

/**
 * Created by Marcelo on 11/12/2015.
 */
class MongoTest {
	@Test
	void testConnect(){
		String uri = System.env.MONGOLAB_URI
		MongoClientURI mongoClientURI = new MongoClientURI(uri)
		MongoClient mongoClient = new MongoClient(mongoClientURI)
		DB db = mongoClient.getDB(mongoClientURI.getDatabase())
		db.authenticate(mongoClientURI.getUsername(), mongoClientURI.getPassword())
		Assert.assertNotNull(db)
	}
}
