package pl.zdna.gcconnect.shared.events;

import lombok.Getter;
import lombok.Setter;

public abstract class CorrelationalDomainEvent extends DomainEvent {
    protected @Getter @Setter String correlationId;
}
