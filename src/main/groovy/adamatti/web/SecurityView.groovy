package adamatti.web

import groovy.util.logging.Slf4j

import javax.annotation.PostConstruct

import org.springframework.stereotype.Component

import adamatti.commons.Resources;
import spark.Request
import spark.Response
import spark.Spark


@Slf4j
@Component
class SecurityView {
	private ConfigObject cfg = Resources.cfg
	
	@PostConstruct	
	public void init(){
		log.trace("SecurityView: started")
		Spark.before("*"){Request req, Response res ->
			String header = req.headers("Authorization")
			String user = "${cfg.user}:${cfg.pass}".bytes.encodeBase64().toString()
			
			if (header != "Basic ${user}"){
				println header
				println user
				
				res.header("WWW-Authenticate", "Basic")
				Spark.halt(401)
			}			
		}
	}	
}
