package mrkwjck.domain;

public interface DomainEventPublisher {

    void publishEvent(DomainEvent event);

}
