package pl.zdna.gcconnect.shared.events;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.shared.interfaces.EventPublisher;

@Component
@RequiredArgsConstructor
public class CorrelationalEventPublisherImpl implements CorrelationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(final DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public EventPublisher withCorrelationId(final String correlationId) {
        return new CorrelationIdAwareEventPublisher(correlationId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class CorrelationIdAwareEventPublisher implements EventPublisher {
        private final String correlationId;

        @Override
        public void publish(final DomainEvent event) {
            if (event instanceof final CorrelationalDomainEvent correlationalDomainEvent)
                correlationalDomainEvent.setCorrelationId(correlationId);

            CorrelationalEventPublisherImpl.this.publish(event);
        }
    }
}
