package pl.zdna.gcconnect.users.application.results;

import pl.zdna.gcconnect.shared.interfaces.Result;

public record AuthenticatedUserCreatedResult(String username, String password) implements Result {
}
