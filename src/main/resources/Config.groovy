env = System.env.ENV ?: "dev"

mongo = [
	user   : "",
	passs  : "",
	host   : "docker.me",
	port   : 27017,
	dbname : System.env.MONGO_DBNAME ?: "adamatti-wiki-${env}",
	url    : System.env.MONGOLAB_URI ?: "mongodb://${mongo.user}:${mongo.pass}@${mongo.host}:${mongo.port}/${mongo.dbname}"
]

spark = [
	port : System.env.PORT ?: 8079
]
