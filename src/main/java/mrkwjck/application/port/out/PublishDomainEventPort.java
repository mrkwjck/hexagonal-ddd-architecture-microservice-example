package mrkwjck.application.port.out;

import mrkwjck.domain.DomainEvent;

public interface PublishDomainEventPort {

    void publishEvent(DomainEvent event);

}
