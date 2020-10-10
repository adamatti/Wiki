package adamatti.commons

import groovy.text.SimpleTemplateEngine
import groovy.text.TemplateEngine
import groovy.util.logging.Slf4j
@Slf4j
class TemplateHelper {
	static String processTemplatePath(String templatePath){
		return processTemplatePath(templatePath,[:])
	}

	static String processTemplatePath(String templatePath, Map variables){
		//Process
		String source = new File(templatePath).getText()
		return processTemplate(source,variables)
	}

	static String processTemplate(String source, Map variables){
		TemplateEngine engine = new SimpleTemplateEngine()
		source = engine.createTemplate(source).make(variables)
		return source
	}
}
