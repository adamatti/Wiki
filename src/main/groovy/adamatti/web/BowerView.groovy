package adamatti.web

import javax.annotation.PostConstruct

import org.springframework.stereotype.Component

import spark.Request
import spark.Response
import spark.Spark

@Component
class BowerView {
	@PostConstruct
	public void init(){
		Spark.get("bower/*"){Request req, Response res ->
			String path = req.pathInfo().replaceFirst("/bower/", "build/bower/");
			//InputStream inputStream = getClass().getResourceAsStream(path)
			InputStream inputStream = new FileInputStream(new File(path))
			
			if (inputStream != null) {				
				res.status(200)
				
				byte[] buf = new byte[1024]
                OutputStream os = res.raw().getOutputStream()
                OutputStreamWriter outWriter = new OutputStreamWriter(os)
                int count = 0
                while ((count = inputStream.read(buf)) >= 0) {
                    os.write(buf, 0, count)
                }
                inputStream.close()
                outWriter.close()

				return ""
			}
			return null
		}
	}
}
