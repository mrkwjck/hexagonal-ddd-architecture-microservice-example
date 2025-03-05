package mrkwjck.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class DomainEvent {

    protected final String name;
    protected final LocalDateTime timestamp;

    protected DomainEvent(String name) {
        timestamp = LocalDateTime.now();
        this.name = name;
    }
}
