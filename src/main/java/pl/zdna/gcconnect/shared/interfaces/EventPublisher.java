package pl.zdna.gcconnect.shared.interfaces;

import pl.zdna.gcconnect.shared.events.DomainEvent;

public interface EventPublisher {
    <T extends DomainEvent> void publish(T event);
    default <T extends DomainEvent> void publishAll(Iterable<T> events) {
        events.forEach(this::publish);
    }
}
