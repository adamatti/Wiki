package adamatti.bizo

import org.asciidoctor.Asciidoctor
import org.asciidoctor.Options

@Singleton
class AsciidoctorProcessor {
	private Asciidoctor asciidoctor = org.asciidoctor.Asciidoctor.Factory.create()
	private Options options = Options.builder().build()

	String process(String text) {
		return asciidoctor.convert(text, options)
	}
}
