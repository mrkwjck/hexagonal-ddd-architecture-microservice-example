package mrkwjck.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mrkwjck.application.port.out.PublishDomainEventPort;
import mrkwjck.domain.DomainEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
class ApplicationEventHandler {

    private final PublishDomainEventPort publishDomainEventPort;

    @TransactionalEventListener()
    void handleEvent(DomainEvent domainEvent) {
        log.info("Received domain event: {}", domainEvent);
        publishDomainEventPort.publishEvent(domainEvent);
    }

}
