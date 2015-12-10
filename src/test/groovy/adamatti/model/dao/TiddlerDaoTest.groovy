package adamatti.model.dao;

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import adamatti.BaseTest
import adamatti.model.entity.Tiddler

public class TiddlerDaoTest extends BaseTest {
	@Autowired
	private TiddlerDAO tiddlerDao
	
	@Test
	public void test(){
		log.debug("Test started")
		
		assert tiddlerDao
		
		//Insert
		log.debug("Insert")
		Tiddler record = [name:"Adamatti ${System.nanoTime()}"]
		record = tiddlerDao.save(record)
		
		//Update
		log.debug("Update")
		record.lastName = "Marcelo"
		record = tiddlerDao.save(record)
		
		//Search
		log.debug("Search")
		record = tiddlerDao.find{it.lastName == "Marcelo"}
		assert record
		
		//Delete
		log.debug("Delete")
		tiddlerDao.delete(record)
		
		log.debug("Test ended")
	}
}
