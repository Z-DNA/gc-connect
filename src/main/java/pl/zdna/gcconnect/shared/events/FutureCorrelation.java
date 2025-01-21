package pl.zdna.gcconnect.shared.events;

import pl.zdna.gcconnect.shared.Response;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public record FutureCorrelation(String correlationId, CompletableFuture<Response> futureResponse) {
    public static FutureCorrelation generate() {
        return new FutureCorrelation(UUID.randomUUID().toString(), new CompletableFuture<>());
    }
}
