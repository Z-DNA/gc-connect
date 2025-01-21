package pl.zdna.gcconnect.shared.interfaces;

public interface CorrelationEventPublisher extends EventPublisher {
    EventPublisher withCorrelationId(String correlationId);
}
