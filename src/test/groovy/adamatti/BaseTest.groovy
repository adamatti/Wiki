package adamatti

import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig)
@ComponentScan(basePackages = "adamatti")
abstract class BaseTest {
	protected Logger log = LoggerFactory.getLogger(this.class)
}
