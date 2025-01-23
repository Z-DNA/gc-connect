package pl.zdna.gcconnect.users.application;

import pl.zdna.gcconnect.shared.Response;

public interface UserAuthorizationService {
    Response createAuthorizedUser(String username, String inviterUsername);
}
