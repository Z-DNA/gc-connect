package pl.zdna.gcconnect.authorization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import pl.zdna.gcconnect.shared.events.DomainEvent;
import pl.zdna.gcconnect.users.domain.events.EventWithUsername;

@Getter
@RequiredArgsConstructor
public class UserFirstLogin extends DomainEvent implements EventWithUsername {
    private final String username;
    private final String userId;
}
