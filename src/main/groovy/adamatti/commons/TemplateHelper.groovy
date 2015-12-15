package adamatti.commons

import groovy.text.SimpleTemplateEngine
import groovy.text.TemplateEngine
import groovy.util.logging.Slf4j
@Slf4j
class TemplateHelper {
	public static String processTemplatePath(String templatePath){
		return processTemplatePath(templatePath,[:])
	}
	
	public static String processTemplatePath(String templatePath, Map variables){
		//Process
		String source = new File(templatePath).getText()
		return processTemplate(source,variables)
	}
	
	public static String processTemplate(String source, Map variables){
		TemplateEngine engine = new SimpleTemplateEngine()
		source = engine.createTemplate(source).make(variables)
		return source
	}
}
