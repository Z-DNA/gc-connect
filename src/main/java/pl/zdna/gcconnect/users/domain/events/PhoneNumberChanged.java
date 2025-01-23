package pl.zdna.gcconnect.users.domain.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.zdna.gcconnect.shared.events.DomainEvent;

@Getter
@RequiredArgsConstructor
public final class PhoneNumberChanged extends DomainEvent implements EventWithUsername {
    private final String username;
    private final String phoneNumber;
}
