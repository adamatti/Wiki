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
	void init(){
		log.trace("SecurityView: started")
		Spark.before("*"){Request req, Response res ->
			String header = req.headers("Authorization")
			String user = "${cfg.user}:${cfg.pass}".bytes.encodeBase64().toString()

			if (header != "Basic ${user}"){
				res.header("WWW-Authenticate", "Basic")
				Spark.halt(401)
			}
		}

		Spark.before("*"){Request req, Response res ->
			String xfp = req.headers("x-forwarded-proto") ?: "http"
			if (!xfp.equalsIgnoreCase("https") && cfg.requireHTTPS){
				Spark.halt(403,"Error: HTTPS required")
			}
		}
	}
}
