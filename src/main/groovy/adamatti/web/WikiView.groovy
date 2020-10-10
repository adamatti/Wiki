package adamatti.web

import adamatti.commons.Resources
import adamatti.model.dao.TiddlerDAO

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import spark.Request
import spark.Response
import spark.Spark
import adamatti.bizo.TiddlerBO
import adamatti.bizo.TiddlerRenderBO
import adamatti.model.entity.Tiddler

@Component
class WikiView extends BaseView {
	private ConfigObject cfg = Resources.cfg

	@Autowired
	private TiddlerDAO tiddlerDao

	@Autowired
	private TiddlerBO tiddlerBo

	@Autowired
	private TiddlerRenderBO tiddlerRenderBo

	@PostConstruct
	void init(){
		Closure home = {Request req,Response res ->
			log.trace ("Redirect to home")
			res.redirect "${this.getBaseUrl(req)}/wiki/home"
		}

		Spark.get("/",home)
		Spark.get("/wiki",home)

		Spark.get("/wiki/:name"){Request req,Response res ->
			String tiddlerName = req.params("name")
			log.trace("Show ${tiddlerName}")

			Tiddler tiddler = tiddlerDao.findByName(tiddlerName)
			String page = tiddler ? "view" : "not_found"

			return this.processTemplatePath("tiddler/${page}.html",[
				tiddlerName : tiddlerName,
				tiddler : tiddler,
				html    : tiddler ? tiddlerRenderBo.process(tiddler) : ''
			])
		}

		Spark.get("/wiki/:name/edit"){Request req,Response res ->
			String tiddlerName = req.params("name")
			log.trace("Edit ${tiddlerName}")

			Tiddler tiddler = tiddlerDao.findByName(tiddlerName)
			return this.processTemplatePath("tiddler/edit.html",[
				tiddlerName : tiddlerName,
				tiddler : tiddler
			])
		}

		Spark.post("/wiki/:name"){Request req,Response res ->
			String tiddlerName = req.params("name")
			Tiddler tiddler = this.convert(req)
			tiddlerBo.save(tiddlerName, tiddler)
			res.redirect("${this.getBaseUrl(req)}/wiki/${tiddler.name}")
		}

		Spark.get("/search"){Request req,Response res ->
			def list = tiddlerDao.findAll().sort{it.name}

			def queryString = req.queryParams("q")
			if (queryString){
				queryString = queryString.toLowerCase()
				list = list.findAll{ it.name.toLowerCase().contains(queryString) || it.body.toLowerCase().contains(queryString)}
			}

			list = list.collect {[name:it.name, html: tiddlerRenderBo.process(it)]}

			return this.processTemplatePath("tiddler/search.html",[
				tiddlerName: "Search by ${queryString}",
				tiddlers: list
			])
		}

		Spark.get("/wiki/:name/delete"){Request req,Response res ->
			String tiddlerName = req.params("name")
			log.trace("Delete ${tiddlerName}")
			tiddlerDao.delete(tiddlerName)
			res.redirect("${this.getBaseUrl(req)}/")
		}
	}

	private Tiddler convert(Request req){
		Tiddler tiddler = new Tiddler()
		tiddler.with {
			name = req.queryParams("name")
			body = req.queryParams("body")
			type = req.queryParams("type") ?: "markdown"
			tags = req.queryParams("tags") ? req.queryParams("tags").split(",") : null
		}
		return tiddler
	}

	private String getBaseUrl(Request req){
		cfg.requireHTTPS ? "https://${req.host()}" : ""
	}
}
