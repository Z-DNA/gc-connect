package pl.zdna.gcconnect.users.domain;

import java.time.Instant;

public record UserDetails(
        UserAccountStatus status,
        Instant creationTimestamp,
        Instant activationTimestamp,
        String inviterUsername) {
    static UserDetails newUserDefault(final User inviter) {
        return new UserDetails(
                UserAccountStatus.INVITED, Instant.now(), null, inviter.getUsername());
    }

    @Deprecated
    static UserDetails uninvited() {
        return new UserDetails(UserAccountStatus.ACTIVE, Instant.now(), Instant.now(), null);
    }

    public UserDetails userActivated() {
        return new UserDetails(
                UserAccountStatus.ACTIVE, creationTimestamp, Instant.now(), inviterUsername);
    }
}
