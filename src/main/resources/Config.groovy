env = System.env.ENV ?: "dev"

//Security (basic, TODO need to improve)
user = System.env.USER ?: "admin"
pass = System.env.PASS ?: "admin"

mongo {
	user   = ""
	pass   = ""
	host   = "docker.me"
	port   = 27017
	dbname = System.env.MONGO_DBNAME ?: "adamatti-wiki-${env}"
	url    = System.env.MONGOLAB_URI ?: "mongodb://${user ? (user + ':' + pass + '@') : ''}${host}:${port}/${dbname}"
}

redis {
	URI redisUri = System.env.REDISCLOUD_URL ? new URI(System.env.REDISCLOUD_URL) : null
	host = redisUri?.host ?: "docker.me"
	port = redisUri?.port?: 6379
	pass = redisUri?.userInfo? redisUri.userInfo.split(":", 2)[1] : ''
}

spark {
	port = System.env.PORT ?: 8079
}
