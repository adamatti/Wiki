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

spark {
	port = System.env.PORT ?: 8079
}
