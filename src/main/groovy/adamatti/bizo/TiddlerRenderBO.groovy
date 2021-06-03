package adamatti.bizo

import adamatti.model.RedisCache
import groovy.util.logging.Slf4j
import org.asciidoctor.Asciidoctor
import org.markdown4j.Markdown4jProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import adamatti.commons.TemplateHelper
import adamatti.model.dao.TiddlerDAO
import adamatti.model.entity.Tiddler

@Slf4j
@Service
class TiddlerRenderBO {
	private Asciidoctor asciidoctor = org.asciidoctor.Asciidoctor.Factory.create()
	private Markdown4jProcessor markdown = new org.markdown4j.Markdown4jProcessor()

	@Autowired
	private TiddlerDAO tiddlerDao

	@Autowired
	private RedisCache redisCache

	//TODO refactor this
	String process(Tiddler tiddler) {
		if (!tiddler) {
			return ""
		} else if (redisCache.get(tiddler.name)) {
			return redisCache.get(tiddler.name)
		}

		String content = this.processWithoutCache(tiddler)
		redisCache.set(tiddler.name, content)
		content
	}

	String processWithoutCache(Tiddler tiddler){
		log.trace("processWithoutCache[name: ${tiddler.name}]")
		if (tiddler.type == "markdown") {
			return markdown.process(tiddler.body)
		} else if (tiddler.type == "asciidoctor") {
			def options  = [:]
			return asciidoctor.convert(tiddler.body,options)
		} else if (tiddler.type == "groovy+md"){
			Map variables = [tiddlers:tiddlerDao.findAll()]
			String result = TemplateHelper.processTemplate(tiddler.body,variables)

			result = markdown.process(result)
			return result
		} else if (tiddler.type == "groovy+ad"){
			Map variables = [tiddlers:tiddlerDao.findAll()]
			String result = TemplateHelper.processTemplate(tiddler.body,variables)

			def options = [:]
			return asciidoctor.convert(result,options)
		}

		log.trace("Type not found [tiddlerName: ${tiddler.name}, type: ${tiddler.type}] ")
		return tiddler.body
	}
}
