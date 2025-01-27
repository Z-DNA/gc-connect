package pl.zdna.gcconnect.users.domain.events;

import lombok.Getter;

import pl.zdna.gcconnect.shared.events.DomainEvent;
import pl.zdna.gcconnect.users.domain.User;

@Getter
public final class UserActivated extends DomainEvent implements EventWithUsername {
    private final String userId;
    private final String username;
    private final String inviter;

    public UserActivated(final User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.inviter = user.getInviterUsername();
    }
}
