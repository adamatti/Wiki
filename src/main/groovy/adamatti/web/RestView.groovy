package adamatti.web

import adamatti.commons.TemplateHelper
import adamatti.model.dao.TiddlerDAO
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark

import javax.annotation.PostConstruct

@Component
class RestView {
	@Autowired
	private TiddlerDAO tiddlerDao

	@PostConstruct
	public void init(){
		Spark.get("/api"){ Request req, Response res->
			return TemplateHelper.processTemplatePath("src/main/webapp/api/home.html")
		}

		Spark.get("/api/tiddlers"){Request req, Response res->
			res.header("Content-Type", "application/json")

			def list = tiddlerDao.findAll().sort{it.name}

			def queryString = req.queryParams("q")
			if (queryString){
				queryString = queryString.toLowerCase()
				list = list.findAll{ it.name.toLowerCase().contains(queryString) || it.body.toLowerCase().contains(queryString)}
			}

			def json = JsonOutput.toJson(list)
			return JsonOutput.prettyPrint(json)
		}

		Spark.get("/api/tiddlers/:name"){Request req, Response res->
			res.header("Content-Type", "application/json")

			String tiddlerName = req.params("name")
			def json = JsonOutput.toJson(tiddlerDao.findByName(tiddlerName))
			return JsonOutput.prettyPrint(json)
		}

		Spark.delete("/api/tiddlers/:name") { Request req, Response res ->
			res.header("Content-Type", "application/json")

			String tiddlerName = req.params("name")
			this.tiddlerDao.delete(tiddlerName)

			return "{}"
		}

		//TODO implement POST
	}
}
