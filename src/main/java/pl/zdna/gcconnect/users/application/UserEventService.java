package pl.zdna.gcconnect.users.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import pl.zdna.gcconnect.authorization.UserFirstLogin;
import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.events.CorrelationalDomainEvent;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.users.application.events.AuthenticatedUserCreated;
import pl.zdna.gcconnect.users.application.events.AuthenticatedUserCreationFailed;
import pl.zdna.gcconnect.users.application.results.AuthenticatedUserCreatedResult;
import pl.zdna.gcconnect.users.application.results.TemporaryUserCreatedResult;
import pl.zdna.gcconnect.users.domain.events.EventWithUsername;
import pl.zdna.gcconnect.users.domain.events.TemporaryUserCreated;
import pl.zdna.gcconnect.users.domain.events.UserActivated;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserEventService {
    private final CorrelationEventPublisher eventPublisher;
    private final UserService userService;
    private final UserAuthorizationService userAuthService;
    private final TemporaryUserRepository temporaryUserRepository;

    @EventListener
    public void onUserFirstLogin(final UserFirstLogin event) {
        logEvent(event);
        userService.activateTemporaryUser(event.getUsername(), event.getUserId());
    }

    @EventListener
    public void onUserActivated(final UserActivated event) {
        logEvent(event);
        val response = userAuthService.activateUser(event.getUserId());
        if (response.isSuccess()) {
            log.debug("User {} activated", event.getUserId());
        }
        // TODO GCC-48
    }

    @EventListener
    public void onTemporaryUserCreated(final TemporaryUserCreated event) {
        logEvent(event);
        final String username = event.getUsername();
        val response = userAuthService.createAuthorizedUser(username, event.getInviterUsername());
        val eventToPublish = getEventForAuthenticatedUserCreation(response, username);

        eventPublisher.withCorrelationId(event.getCorrelationId()).publish(eventToPublish);
    }

    private CorrelationalDomainEvent getEventForAuthenticatedUserCreation(
            final Response response, final String username) {
        if (response.isSuccess()) {
            final AuthenticatedUserCreatedResult result = response.getResult();
            return new AuthenticatedUserCreated(
                    result.username(), result.password(), result.inviterUsername());
        } else {
            return new AuthenticatedUserCreationFailed(username, response.getError());
        }
    }

    @EventListener
    public void onAuthenticatedUserCreated(final AuthenticatedUserCreated event) {
        logEvent(event);
        val result = new TemporaryUserCreatedResult(event.getUsername(), event.getPassword());
        val response = Response.success(result);
        userService.completeFutureCorrelation(event.getCorrelationId(), response);
    }

    @EventListener
    public void onAuthenticatedUserCreationFailed(final AuthenticatedUserCreationFailed event) {
        logEvent(event);
        temporaryUserRepository.deleteByUsername(event.getUsername());
        val response = Response.failure(event.getErrorMessage());
        userService.completeFutureCorrelation(event.getCorrelationId(), response);
    }

    private void logEvent(final EventWithUsername event) {
        if (event instanceof final CorrelationalDomainEvent cde) {
            log.debug(
                    "Received {} event for user: {} with correlation id: {}",
                    event.getClass().getSimpleName(),
                    event.getUsername(),
                    cde.getCorrelationId());
        } else {
            log.debug(
                    "Received {} event for user: {}",
                    event.getClass().getSimpleName(),
                    event.getUsername());
        }
    }
}
