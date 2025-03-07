package mrkwjck

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.springframework.context.annotation.Import
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@EmbeddedKafka(ports = [9092])
@Import([TestChannelBinderConfiguration])
class IntegrationTest extends Specification{
}
