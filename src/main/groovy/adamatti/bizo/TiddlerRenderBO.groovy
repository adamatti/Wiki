package adamatti.bizo

import org.asciidoctor.Asciidoctor
import org.markdown4j.Markdown4jProcessor
import org.asciidoctor.Asciidoctor.Factory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import adamatti.commons.TemplateHelper;
import adamatti.model.dao.TiddlerDAO
import adamatti.model.entity.Tiddler
@Service
class TiddlerRenderBO {
	@Autowired
	private TiddlerDAO tiddlerDao

	public String process(Tiddler tiddler){
		if (tiddler == null){
			return ""
		} else if (tiddler.type == "markdown") {
			return new Markdown4jProcessor().process(tiddler.body)
		} else if (tiddler.type == "asciidoctor") {
			def options  = [:]
			return Factory.create().convert(tiddler.body,options)
		} else if (tiddler.type == "groovy+md"){
			Map variables = [tiddlers:tiddlerDao.findAll()]
			String result = TemplateHelper.processTemplate(tiddler.body,variables)

			result = new Markdown4jProcessor().process(result)
			return result
		} else if (tiddler.type == "groovy+ad"){
			Map variables = [tiddlers:tiddlerDao.findAll()]
			String result = TemplateHelper.processTemplate(tiddler.body,variables)

			def options = [:]
			return Factory.create().convert(result,options)
		}

		return "Type not found: ${tiddler.type}"
	}
}
