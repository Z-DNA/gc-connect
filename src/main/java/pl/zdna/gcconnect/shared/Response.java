package pl.zdna.gcconnect.shared;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import pl.zdna.gcconnect.shared.interfaces.Result;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Response {
    private final boolean success;
    private final Result result;
    private final String error;

    @SuppressWarnings("unchecked")
    public <T extends Result> T getResult() {
        return (T) result;
    }

    public static Response success() {
        return new Response(true, null, null);
    }

    public static <T extends Result> Response success(final T result) {
        return new Response(true, result, null);
    }

    public static Response failure(final String error) {
        return new Response(false, null, error);
    }
}
