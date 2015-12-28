package adamatti

import org.springframework.context.support.ClassPathXmlApplicationContext

import spark.Spark
import adamatti.commons.Resources
import groovy.util.logging.Slf4j;

@Slf4j
class WikiMain {
	public static void main(String [] args){
		int port = "${Resources.cfg.spark.port}".toInteger()
		Spark.port(port)
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring-app.xml")
		context.registerShutdownHook()
		
		log.info "Server started on ${port}"
	}
}
