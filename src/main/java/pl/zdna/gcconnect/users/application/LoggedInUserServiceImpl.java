package pl.zdna.gcconnect.users.application;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggedInUserServiceImpl implements LoggedInUserService {
    private final UserAuthorizationService authorizationService;

    @Override
    public OidcUser getLoggedInPrincipal() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof OidcUser user) return user;
        throw new IllegalStateException("User not logged in");
    }

    @Override
    public String getLoggedInUsername() {
        return getLoggedInPrincipal().getClaimAsString("name");
    }

    @Override
    public String getLoggedInUserId() {
        return getLoggedInPrincipal().getSubject();
    }
}
