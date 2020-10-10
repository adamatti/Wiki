package adamatti.bizo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service

import adamatti.model.dao.TiddlerDAO
import adamatti.model.entity.Tiddler

import javax.annotation.Resource

@Service
class TiddlerBO {
	@Autowired
	private TiddlerDAO tiddlerDao

	@Resource(name="redisTemplate")
	private ValueOperations valueOps

	Tiddler save(String oldName, Tiddler tiddler){
		Tiddler entity = tiddlerDao.findByName(oldName)

		tiddler.created = entity?.created ?: new Date()
		tiddler.modified = new Date()
		tiddler.type = tiddler.type ?: 'markdown'

		tiddler = tiddlerDao.save(tiddler)

		if (oldName != tiddler.name){
			tiddlerDao.delete(entity)
		}

		valueOps.set(tiddler.name,null)

		tiddler
	}
}
