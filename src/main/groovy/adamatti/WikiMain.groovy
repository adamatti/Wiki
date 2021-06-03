package adamatti

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.util.StopWatch
import spark.Spark
import adamatti.commons.Resources
import groovy.util.logging.Slf4j

@Slf4j
@ComponentScan(basePackages = ["adamatti"])
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
		ApplicationContext context = new AnnotationConfigApplicationContext(WikiMain.class)
		context.registerShutdownHook()
	}
}
