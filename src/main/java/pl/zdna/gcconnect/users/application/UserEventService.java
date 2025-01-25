package pl.zdna.gcconnect.users.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.events.CorrelationalDomainEvent;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.shared.interfaces.FutureCorrelationAware;
import pl.zdna.gcconnect.users.application.events.AuthenticatedUserCreated;
import pl.zdna.gcconnect.users.application.events.AuthenticatedUserCreationFailed;
import pl.zdna.gcconnect.users.application.results.AuthenticatedUserCreatedResult;
import pl.zdna.gcconnect.users.application.results.TemporaryUserCreatedResult;
import pl.zdna.gcconnect.users.domain.TemporaryUser;
import pl.zdna.gcconnect.users.domain.TemporaryUserRepository;
import pl.zdna.gcconnect.users.domain.events.EventWithUsername;
import pl.zdna.gcconnect.users.domain.events.TemporaryUserCreated;
import pl.zdna.gcconnect.users.domain.events.UserActivated;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserEventService {
    private final CorrelationEventPublisher eventPublisher;

    private final FutureCorrelationAware userService;
    private final UserAuthorizationService userAuthService;

    private final TemporaryUserRepository temporaryUserRepository;

    @EventListener
    public void onUserActivation(final UserActivated event) {
        logEvent(event);
        deleteTemporaryUser(event.getUsername());
    }

    private void deleteTemporaryUser(final String username) {
        final TemporaryUser temporaryUser = temporaryUserRepository.getByUsername(username);
        temporaryUserRepository.delete(temporaryUser);
    }

    @EventListener
    public void onTemporaryUserCreated(final TemporaryUserCreated event) {
        logEvent(event);
        final String username = event.getUsername();
        final Response response =
                userAuthService.createAuthorizedUser(username, event.getInviterUsername());

        final var eventToPublish = getEventForAuthenticatedUserCreation(response, username);

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
        final var result = new TemporaryUserCreatedResult(event.getUsername(), event.getPassword());
        final Response response = Response.success(result);
        userService.completeFutureCorrelation(event.getCorrelationId(), response);
    }

    @EventListener
    public void onAuthenticatedUserCreationFailed(final AuthenticatedUserCreationFailed event) {
        logEvent(event);
        deleteTemporaryUser(event.getUsername());
        final Response response = Response.failure(event.getErrorMessage());
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
