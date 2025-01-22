package pl.zdna.gcconnect.users.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.zdna.gcconnect.authorization.UserAuthorizationService;
import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.events.CorrelationalDomainEvent;
import pl.zdna.gcconnect.shared.interfaces.FutureCorrelationAware;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.users.application.events.AuthenticatedUserCreated;
import pl.zdna.gcconnect.users.application.events.AuthenticatedUserCreationFailed;
import pl.zdna.gcconnect.users.application.results.AuthenticatedUserCreatedResult;
import pl.zdna.gcconnect.users.application.results.TemporaryUserCreatedResult;
import pl.zdna.gcconnect.users.domain.TemporaryUser;
import pl.zdna.gcconnect.users.domain.TemporaryUserRepository;
import pl.zdna.gcconnect.users.domain.events.TemporaryUserCreated;
import pl.zdna.gcconnect.users.domain.events.UserActivated;

@Service
@RequiredArgsConstructor
public class UserEventService {
    private final CorrelationEventPublisher eventPublisher;

    private final FutureCorrelationAware userService;
    private final UserAuthorizationService userAuthService;

    private final TemporaryUserRepository temporaryUserRepository;

    @EventListener
    public void onUserActivation(final UserActivated event) {
        deleteTemporaryUser(event.getUsername());
    }

    private void deleteTemporaryUser(final String username) {
        final TemporaryUser temporaryUser = temporaryUserRepository.getByUsername(username);
        temporaryUserRepository.delete(temporaryUser);
    }

    @EventListener
    public void onTemporaryUserCreated(final TemporaryUserCreated event) {
        final String username = event.getUsername();
        final Response response = userAuthService.createAuthorizedUser(
                username, event.getInviterUsername());

        final var eventToPublish = getEventForAuthenticatedUserCreation(response, username);

        eventPublisher.withCorrelationId(event.getCorrelationId())
                .publish(eventToPublish);
    }

    private CorrelationalDomainEvent getEventForAuthenticatedUserCreation(final Response response, final String username) {
        if (response.isSuccess()) {
            final AuthenticatedUserCreatedResult result = response.getResult();
            return new AuthenticatedUserCreated(result.username(), result.password());
        } else {
            return new AuthenticatedUserCreationFailed(username, response.getError());
        }
    }

    @EventListener
    public void onAuthenticatedUserCreated(final AuthenticatedUserCreated event) {
        final var result = new TemporaryUserCreatedResult(event.getUsername(), event.getPassword());
        final Response response = Response.success(result);
        userService.completeFutureCorrelation(event.getCorrelationId(), response);
    }

    @EventListener
    public void onAuthenticatedUserCreationFailed(final AuthenticatedUserCreationFailed event) {
        deleteTemporaryUser(event.getUsername());
        final Response response = Response.failure(event.getErrorMessage());
        userService.completeFutureCorrelation(event.getCorrelationId(), response);
    }
}
