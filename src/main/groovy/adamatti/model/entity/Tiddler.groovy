package adamatti.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection="tiddlers")
class Tiddler {
	@Id
	String id
	String name
	String lastName	
}
