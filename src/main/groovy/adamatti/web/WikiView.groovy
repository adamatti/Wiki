package adamatti.web

import javax.annotation.PostConstruct

import org.markdown4j.Markdown4jProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import spark.Request
import spark.Response
import spark.Spark
import adamatti.bizo.TiddlerBO
import adamatti.model.entity.Tiddler

@Component
class WikiView extends BaseView {
	@Autowired
	private TiddlerBO tiddlerBo
	
	@PostConstruct
	public void init(){
		Closure home = {Request req,Response res ->
			log.trace ("Redirect to home")
			res.redirect "/wiki/home"
		}
		
		Spark.get("/",home)
		Spark.get("/wiki",home)
		
		Spark.get("/wiki/:name"){Request req,Response res ->			
			String tiddlerName = req.params("name")
			log.trace("Show ${tiddlerName}")
			
			Tiddler tiddler = tiddlerBo.findByName(tiddlerName)
			String page = tiddler ? "view" : "not_found"
			
			return this.processTemplate("tiddler/${page}.html",[
				tiddlerName : tiddlerName,
				tiddler : tiddler,
				html    : tiddler ? new Markdown4jProcessor().process(tiddler.body) : ''
			])
		}
		
		Spark.get("/wiki/:name/edit"){Request req,Response res ->
			String tiddlerName = req.params("name")
			log.trace("Edit ${tiddlerName}")
			
			Tiddler tiddler = tiddlerBo.findByName(tiddlerName)
			return this.processTemplate("tiddler/edit.html",[
				tiddlerName : tiddlerName,
				tiddler : tiddler				
			])
		}
		
		Spark.post("/wiki/:name"){Request req,Response res ->
			String tiddlerName = req.params("name")
			Tiddler tiddler = this.convert(req)
			tiddlerBo.save(tiddlerName, tiddler)
			res.redirect("/wiki/${tiddler.name}")
		}
	}
	
	private Tiddler convert(Request req){
		Tiddler tiddler = new Tiddler()
		tiddler.with {
			name = req.queryParams("name")
			body = req.queryParams("body")
		}		
		return tiddler
	}
}
