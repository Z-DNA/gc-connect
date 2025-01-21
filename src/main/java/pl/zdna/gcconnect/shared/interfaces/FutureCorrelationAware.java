package pl.zdna.gcconnect.shared.interfaces;

import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.events.FutureCorrelation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface FutureCorrelationAware {
    ConcurrentMap<String, FutureCorrelation> correlationMap = new ConcurrentHashMap<>();

    default FutureCorrelation newFutureCorrelation() {
        final FutureCorrelation futureCorrelation = FutureCorrelation.generate();
        correlationMap.put(futureCorrelation.correlationId(), futureCorrelation);
        return futureCorrelation;
    }

    default void completeFutureCorrelation(final String correlationId, final Response response) {
        final var futureCorrelation = extractFutureCorrelation(correlationId);
        futureCorrelation.futureResponse().complete(response);
    }

    private FutureCorrelation extractFutureCorrelation(final String correlationId) {
        return correlationMap.remove(correlationId);
    }
}
