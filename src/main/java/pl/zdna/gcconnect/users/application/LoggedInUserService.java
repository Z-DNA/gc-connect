package pl.zdna.gcconnect.users.application;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public interface LoggedInUserService {
    OidcUser getLoggedInPrincipal();

    String getLoggedInUsername();

    String getLoggedInUserId();
}
