package pl.zdna.gcconnect.users.application;

import pl.zdna.gcconnect.shared.Response;

public interface UserAuthenticationService {
    Response createAuthenticatedUser(String username, String inviterUsername);
}
