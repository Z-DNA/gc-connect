package pl.zdna.gcconnect.authorization;

import pl.zdna.gcconnect.shared.Response;

public interface UserAuthorizationService {
    Response createAuthorizedUser(String username, String inviterUsername);
}
