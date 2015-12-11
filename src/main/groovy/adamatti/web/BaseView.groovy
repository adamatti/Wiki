package adamatti.web

import groovy.text.SimpleTemplateEngine
import groovy.text.TemplateEngine

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseView {
	private String TEMPLATE_FOLDER = "src/main/webapp"
	protected Logger log = LoggerFactory.getLogger(this.class)
	
	protected String processTemplate(String templatePath){
		return this.processTemplate(templatePath,[:])
	}
	
	protected String processTemplate(String templatePath, Map variables){
		//Add extra variables
		variables.include = include
		
		//Process
		String source = new File("${TEMPLATE_FOLDER}/${templatePath}").getText()		
		TemplateEngine engine = new SimpleTemplateEngine()
		source = engine.createTemplate(source).make(variables)
		return source
	}
	
	private Closure include = {script, page ->
		//log.trace("Include ${page}")
		String text = this.processTemplate(page,script.binding.variables)
		//script.binding.out.print text
		return text
	}
}
