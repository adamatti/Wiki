package adamatti

import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring-test.xml")
abstract class BaseTest {
	protected Logger log = LoggerFactory.getLogger(this.class)
}
