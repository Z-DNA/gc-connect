package pl.zdna.gcconnect.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.zdna.gcconnect.users.domain.events.PhoneNumberChanged;
import pl.zdna.gcconnect.users.domain.events.UserActivated;

@Component
@RequiredArgsConstructor
public class NotificationsEventListener {

    @EventListener
    public void onUserActivated(final UserActivated event) {
        // TODO: send notification to inviter
    }

    @EventListener
    public void onPhoneNumberChanged(final PhoneNumberChanged event) {
        // TODO: send notification to friends
    }
}
