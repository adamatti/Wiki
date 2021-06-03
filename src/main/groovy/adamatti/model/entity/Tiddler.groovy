package adamatti.model.entity

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@CompileStatic
@Document(collection="tiddlers")
class Tiddler {
	Date created
	Date modified

	@Id
	String name
	String type //TODO create an enum
	String body

	Map meta
	List<String> tags
}
