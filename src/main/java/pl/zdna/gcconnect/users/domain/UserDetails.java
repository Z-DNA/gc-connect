package pl.zdna.gcconnect.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetails{
    UserAccountStatus status;
    Instant creationTimestamp;
    String inviterUsername;

    static UserDetails newUserDefault(final User inviter) {
        return new UserDetails(UserAccountStatus.INACTIVE, Instant.now(), inviter.getUsername());
    }

    public UserDetails withActiveStatus() {
        return new UserDetails(UserAccountStatus.ACTIVE, creationTimestamp, inviterUsername);
    }
}
