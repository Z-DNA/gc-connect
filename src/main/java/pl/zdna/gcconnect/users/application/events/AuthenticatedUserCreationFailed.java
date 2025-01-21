package pl.zdna.gcconnect.users.application.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.zdna.gcconnect.shared.events.CorrelationalDomainEvent;

@Getter
@RequiredArgsConstructor
public class AuthenticatedUserCreationFailed extends CorrelationalDomainEvent {
    private final String username;
    private final String errorMessage;
}
