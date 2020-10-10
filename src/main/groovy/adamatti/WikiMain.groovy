package adamatti

import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.util.StopWatch
import spark.Spark
import adamatti.commons.Resources
import groovy.util.logging.Slf4j

@Slf4j
class WikiMain {
	private static int port = "${Resources.cfg.spark.port}".toInteger()

	static void main(String [] args){
		log.info("Server starting...")

		StopWatch clock = new StopWatch(); clock.start()

		startSpark()
		startSpring()

		clock.stop()

		log.info "Server started on ${port} - ${clock.getTotalTimeMillis()} ms"
	}

	private static startSpark(){
		Spark.port(port)
	}

	private static startSpring(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring-app.xml")
		context.registerShutdownHook()
	}
}
