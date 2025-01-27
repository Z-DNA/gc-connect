package pl.zdna.gcconnect.users.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import pl.zdna.gcconnect.shared.Entity;
import pl.zdna.gcconnect.users.domain.events.TemporaryUserCreated;
import pl.zdna.gcconnect.vgn.VGNFactory;
import pl.zdna.gcconnect.vgn.VGNType;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public final class TemporaryUser extends Entity {
    private String username;
    private String phoneNumber;
    private UserDetails details;

    public void useDetailsFromActivatedUser(final User user) {
        if (user.getUsername().equals(username)) {
            this.details = user.getDetails();
        }
        // TODO GCC-44
    }

    public static TemporaryUserBuilder with(final VGNFactory VGNFactory) {
        return new TemporaryUserBuilder(VGNFactory);
    }

    @Accessors(chain = true)
    public static class TemporaryUserBuilder {
        final VGNFactory VGNFactory;

        private User inviter;
        private String username;
        private @Setter String phoneNumber;

        private TemporaryUserBuilder(final VGNFactory VGNFactory) {
            this.VGNFactory = VGNFactory;
        }

        public TemporaryUserBuilder setUsername(String username) {
            this.username = username.toLowerCase();
            return this;
        }

        public TemporaryUserBuilder invitedBy(final User inviter) {
            this.inviter = inviter;
            return this;
        }

        public TemporaryUser build() {
            validate();
            var user =
                    new TemporaryUser(username, phoneNumber, UserDetails.newUserDefault(inviter));
            user.addDomainEvent(new TemporaryUserCreated(username, inviter));

            return user;
        }

        private void validate() {
            if (inviter == null) {
                throw new IllegalArgumentException("Inviter cannot be null");
            }
            VGNFactory.validate(VGNType.USERNAME, username);
            VGNFactory.validate(VGNType.PHONE_NUMBER, phoneNumber);
        }
    }
}
