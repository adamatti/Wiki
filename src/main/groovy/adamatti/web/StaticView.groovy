package adamatti.web

import groovy.util.logging.Slf4j
import javax.annotation.PostConstruct
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark
import groovy.time.TimeCategory

import java.text.SimpleDateFormat

@Slf4j
@Component
class StaticView {
	@PostConstruct
	void init(){
		//TODO find a better place, it shouldn't be here
		Spark.get("favicon.ico") { Request req, Response res ->
			String path = "src/main/webapp/favicon.ico"
			return serveFile(path,req,res)
		}
	}

	private def serveFile(String path, Request req, Response res){
		this.addCacheHeaders(res)

		//InputStream inputStream = getClass().getResourceAsStream(path)
		def file = new File(path)
		if (!file.exists()){
			log.warn("File not found: " + path)
			return null
		}
		InputStream inputStream = new FileInputStream(file)

		if (inputStream != null) {
			res.status(200)

			byte[] buf = new byte[1024]
			OutputStream os = res.raw().getOutputStream()
			OutputStreamWriter outWriter = new OutputStreamWriter(os)
			int count
			while ((count = inputStream.read(buf)) >= 0) {
				os.write(buf, 0, count)
			}
			inputStream.close()
			outWriter.close()

			return ""
		}
		return null

	}

	private void addCacheHeaders(Response res){
		res.header("Cache-Control","public, max-age=14400")

		Date tomorrow = use(TimeCategory){
			new Date() + 1.days
		}
		Date yesterday = use(TimeCategory){
			new Date() - 1.days
		}

		res.header("Expires", format(tomorrow))
		res.header("Last-Modified", format(yesterday))
	}

	private String format(Date date){
		def dtFormat = "EEE, dd MMM yyyy HH:mm:ss zzz"
		def sdf = new SimpleDateFormat(dtFormat)
		return sdf.format(date)
	}
}
