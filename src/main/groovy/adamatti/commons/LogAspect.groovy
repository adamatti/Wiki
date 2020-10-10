package adamatti.commons

import groovy.util.logging.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Slf4j
@Aspect
@Component
class LogAspect {
	@Around("execution(* *(..))")
	Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch clock = new StopWatch()
		clock.start()

		def retVal
		try {
			retVal = pjp.proceed();
		} finally {
			clock.stop()
			if (clock.getTotalTimeMillis() > 0 ) {
				log.trace "Time elapsed for ${pjp.getSignature().toShortString()} - ${clock.getTotalTimeMillis()} ms"
			}
		}

		return retVal
	}


}
