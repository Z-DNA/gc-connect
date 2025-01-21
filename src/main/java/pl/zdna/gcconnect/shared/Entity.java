package pl.zdna.gcconnect.shared;

import pl.zdna.gcconnect.shared.events.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Entity {
    protected final List<DomainEvent> events = new ArrayList<>();

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(events);
    }

    protected void addDomainEvent(final DomainEvent event) { events.add(event); }

    protected void removeDomainEvent(final DomainEvent event) { events.remove(event); }

    public void clearDomainEvents() { events.clear(); }
}
