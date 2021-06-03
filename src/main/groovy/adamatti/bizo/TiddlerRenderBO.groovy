package adamatti.bizo

import adamatti.model.RedisCache
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import adamatti.commons.TemplateHelper
import adamatti.model.dao.TiddlerDAO
import adamatti.model.entity.Tiddler

@Slf4j
@Service
class TiddlerRenderBO {
	private AsciidoctorProcessor asciidoctorProcessor = AsciidoctorProcessor.instance
	private MarkdownProcessor markdownProcessor = MarkdownProcessor.instance

	@Autowired
	private TiddlerDAO tiddlerDao

	@Autowired
	private RedisCache redisCache

	String process(Tiddler tiddler) {
		if (!tiddler) {
			return ""
		}

		if (redisCache.get(tiddler.name)) {
			return redisCache.get(tiddler.name)
		}

		String content = this.processWithoutCache(tiddler)
		redisCache.set(tiddler.name, content)
		content
	}

	String processWithoutCache(Tiddler tiddler){
		log.trace("processWithoutCache[name: ${tiddler.name}]")
		if (tiddler.type == "markdown") {
			return markdownProcessor.process(tiddler.body)
		} else if (tiddler.type == "asciidoctor") {
			return asciidoctorProcessor.process(tiddler.body)
		} else if (tiddler.type == "groovy+md"){
			return markdownProcessor.process(groovyProcess(tiddler.body))
		} else if (tiddler.type == "groovy+ad"){
			return asciidoctorProcessor.process(groovyProcess(tiddler.body))
		}

		log.trace("Type not found [tiddlerName: ${tiddler.name}, type: ${tiddler.type}] ")
		return tiddler.body
	}

	private String groovyProcess(String text){
		Map variables = [tiddlers:tiddlerDao.findAll()]
		return TemplateHelper.processTemplate(text,variables)
	}
}
