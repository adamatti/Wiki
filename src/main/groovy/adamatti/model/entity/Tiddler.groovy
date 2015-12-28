package adamatti.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection="tiddlers")
class Tiddler {
	Date created
	Date modified
	
	@Id
	String name
	String type
	String body
	
	Map meta
	List<String> tags
}
