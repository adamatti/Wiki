package adamatti.bizo

import org.markdown4j.Markdown4jProcessor

@Singleton
class MarkdownProcessor {
	private Markdown4jProcessor markdown = new org.markdown4j.Markdown4jProcessor()

	String process(String text){
		return markdown.process(text)
	}
}
