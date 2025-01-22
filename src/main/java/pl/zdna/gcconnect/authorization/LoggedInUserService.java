package pl.zdna.gcconnect.authorization;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public interface LoggedInUserService {
    OidcUser getLoggedInPrincipal();
    String getLoggedInUsername();
}
