package pl.zdna.gcconnect.users.domain.events;

import lombok.Getter;

import pl.zdna.gcconnect.shared.events.CorrelationalDomainEvent;
import pl.zdna.gcconnect.users.domain.User;

@Getter
public final class TemporaryUserCreated extends CorrelationalDomainEvent
        implements EventWithUsername {
    private final String username;
    private final String inviterUsername;

    public TemporaryUserCreated(final String username, final User inviter) {
        this.username = username;
        this.inviterUsername = inviter.getUsername();
    }
}
