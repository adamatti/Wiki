package adamatti.bizo

import org.markdown4j.Markdown4jProcessor
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
		} else if (tiddler.type == "markdown"){
			return new Markdown4jProcessor().process(tiddler.body)
		} else if (tiddler.type == "groovy"){
			Map variables = [tiddlers:tiddlerDao.findAll()]
		
			String result = TemplateHelper.processTemplate(tiddler.body,variables)
			result = new Markdown4jProcessor().process(result)
			return result
		}
		return "Type not found: ${tiddler.type}"
	}
}
