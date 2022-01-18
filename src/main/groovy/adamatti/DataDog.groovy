package adamatti

import com.timgroup.statsd.NonBlockingStatsDClientBuilder
import com.timgroup.statsd.StatsDClient

@Singleton
class DataDog {
	@Delegate
	final StatsDClient client = new NonBlockingStatsDClientBuilder()
		.prefix("statsd")
		.hostname("localhost")
		.port(8125)
		.build()
}
