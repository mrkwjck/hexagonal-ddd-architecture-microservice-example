package mrkwjck.infrastructure.adapter.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mrkwjck.application.port.out.PublishDomainEventPort;
import mrkwjck.domain.DomainEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class KafkaDomainEventPublisher implements PublishDomainEventPort {

    private static final String DOMAIN_EVENT_OUTPUT_CHANNEL = "domainEventOutput-out-0";

    private final StreamBridge streamBridge;

    @Override
    public void publishEvent(DomainEvent event) {
        log.info("Publishing domain event {} to Kafka", event);
        streamBridge.send(DOMAIN_EVENT_OUTPUT_CHANNEL, MessageBuilder.withPayload(event).build());
    }

}
