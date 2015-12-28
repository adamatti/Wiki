package adamatti

import org.springframework.context.support.ClassPathXmlApplicationContext

import spark.Spark
import adamatti.commons.Resources
import groovy.util.logging.Slf4j;

@Slf4j
class WikiMain {
	private static int port = "${Resources.cfg.spark.port}".toInteger() 
	
	public static void main(String [] args){
		startSpark()
		startSpring()		
		log.info "Server started on ${port}"
	}
	
	private static startSpark(){
		Spark.port(port)
		//Spark.externalStaticFileLocation(new File("build/bower").path)
	}
	
	private static startSpring(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring-app.xml")
		context.registerShutdownHook()
	}
}
