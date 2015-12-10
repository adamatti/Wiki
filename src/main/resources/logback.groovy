appender("STDOUT", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level - %logger.%method [line: %line] - %msg%n"
	}
}

logger("adamatti", TRACE)
root(WARN,["STDOUT"])
