env = System.env.ENV ?: "dev"

mongo = [
	host   : "docker.me",
	port   : 27017,
	dbname : System.env.MONGO_DB_NAME ?: "adamatti-wiki-${env}" 
]

spark = [
	port : System.env.PORT ?: 8079
]