package pl.zdna.gcconnect.users.application.results;

import pl.zdna.gcconnect.shared.interfaces.Result;

public record TemporaryUserCreatedResult(String username, String password) implements Result {
}
