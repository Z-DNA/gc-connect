package pl.zdna.gcconnect.shared.events;

import lombok.Getter;

import java.time.Instant;


public abstract class DomainEvent {
    protected FutureCorrelation futureCorrelationId;
    protected @Getter final Instant when = Instant.now();
}
