package adamatti.model.dao

import org.junit.Ignore
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import adamatti.BaseTest
import adamatti.model.entity.Tiddler

@Ignore // Integration test
class TiddlerDaoTest extends BaseTest {
	@Autowired
	private TiddlerDAO tiddlerDao

	@Test
	void testCrud(){
		Tiddler record = new Tiddler(name: "sample ${System.nanoTime()}", meta: [a:"b"])

		//Insert
		record = tiddlerDao.save(record)

		//Update
		record = tiddlerDao.save(record)

		//Delete
		tiddlerDao.delete(record)
	}
}
