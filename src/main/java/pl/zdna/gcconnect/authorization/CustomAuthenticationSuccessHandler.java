package pl.zdna.gcconnect.authorization;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import pl.zdna.gcconnect.shared.interfaces.EventPublisher;
import pl.zdna.gcconnect.users.domain.UserAccountStatus;

import java.io.IOException;

@Log4j2
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired private EventPublisher eventPublisher;

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication)
            throws IOException {
        final OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        val username = oidcUser.getClaimAsString("name").toLowerCase(); // TODO GCC-43
        val subject = oidcUser.getSubject();
        val userStatus = getUserStatus(oidcUser);
        log.debug("User: {} logged in; Sub: {}; Status: {}", username, subject, userStatus);

        if (userStatus.isNewUserStatus()) {
            eventPublisher.publish(new UserFirstLogin(username, subject));
            response.sendRedirect("/welcome");
        } else {
            response.sendRedirect("/");
        }
    }

    private UserAccountStatus getUserStatus(final OidcUser oidcUser) {
        val appMetadata = oidcUser.getClaimAsMap("app_metadata");
        val status = (String) appMetadata.get("status");
        return UserAccountStatus.valueOf(status);
    }
}
