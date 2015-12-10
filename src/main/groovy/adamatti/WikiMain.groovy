package adamatti

import org.springframework.context.support.ClassPathXmlApplicationContext

class WikiMain {
	public static void main(String [] args){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring-app.xml")
		context.registerShutdownHook()
		
		println "working"
	}
}
