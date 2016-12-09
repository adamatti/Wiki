env = System.env.ENV ?: "dev"

//Security (basic, TODO need to improve)
user = System.env.APP_USER ?: "admin"
pass = System.env.APP_PASS ?: "admin"

mongo {
	user   = ""
	pass   = ""
	host   = "localhost"
	port   = 27017
	dbname = System.env.MONGO_DBNAME ?: "adamatti-wiki-${env}"
	url    = System.env.MONGODB_URI ?: System.env.MONGOLAB_URI ?: "mongodb://${user ? (user + ':' + pass + '@') : ''}${host}:${port}/${dbname}"
}

redis {
	URI redisUri = System.env.REDISCLOUD_URL ? new URI(System.env.REDISCLOUD_URL) : new URI("http://localhost:32769")
	host = redisUri?.host ?: "docker.me"
	port = redisUri?.port?: 6379
	pass = redisUri?.userInfo? redisUri.userInfo.split(":", 2)[1] : ''
}

spark {
	port = System.env.PORT ?: 8079
}

requireHTTPS = (System.env.REQUIRE_HTTPS ?: "false").equalsIgnoreCase("true")
