package pl.zdna.gcconnect.users.application.events;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.zdna.gcconnect.shared.events.CorrelationalDomainEvent;
import pl.zdna.gcconnect.users.domain.events.EventWithUsername;

@Getter
@RequiredArgsConstructor
public class AuthenticatedUserCreated extends CorrelationalDomainEvent implements EventWithUsername {
    private final String username;
    private final String password;
    private final String inviterUsername;
}
