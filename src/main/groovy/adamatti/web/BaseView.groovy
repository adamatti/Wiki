package adamatti.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import adamatti.commons.TemplateHelper

abstract class BaseView {
	private String TEMPLATE_FOLDER = "src/main/webapp"
	protected Logger log = LoggerFactory.getLogger(this.class)
	
	protected String processTemplatePath(String templatePath){
		return this.processTemplatePath(templatePath,[:])
	}
	
	protected String processTemplatePath(String templatePath, Map variables){
		//Add extra variables
		variables.include = include
		
		//Process
		return TemplateHelper.processTemplatePath("${TEMPLATE_FOLDER}/${templatePath}", variables)
	}
	
	private Closure include = {script, page ->
		//log.trace("Include ${page}")
		String text = this.processTemplatePath(page,script.binding.variables)
		//script.binding.out.print text
		return text
	}
}
