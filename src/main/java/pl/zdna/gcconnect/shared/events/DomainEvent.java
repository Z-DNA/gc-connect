package pl.zdna.gcconnect.shared.events;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@ToString
public abstract class DomainEvent {
    protected @Getter final Instant when = Instant.now();
}
