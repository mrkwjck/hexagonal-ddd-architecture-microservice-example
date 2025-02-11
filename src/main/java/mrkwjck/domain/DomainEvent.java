package mrkwjck.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class DomainEvent {

    protected final String name;
    protected final LocalDateTime timestamp;

    protected DomainEvent(String name) {
        timestamp = LocalDateTime.now();
        this.name = name;
    }

}
