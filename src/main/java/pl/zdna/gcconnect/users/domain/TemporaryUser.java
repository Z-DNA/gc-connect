package pl.zdna.gcconnect.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import pl.zdna.gcconnect.shared.Entity;
import pl.zdna.gcconnect.vgn.VGNFactory;
import pl.zdna.gcconnect.vgn.VGNType;
import pl.zdna.gcconnect.users.domain.events.TemporaryUserCreated;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class TemporaryUser extends Entity {
    private String username;
    private String phoneNumber;
    private UserDetails details;

    public static TemporaryUserBuilder with(final VGNFactory VGNFactory) {
        return new TemporaryUserBuilder(VGNFactory);
    }

    @Accessors(chain = true)
    public static class TemporaryUserBuilder {
        final VGNFactory VGNFactory;

        private User inviter;
        private @Setter String username;
        private @Setter String phoneNumber;

        private TemporaryUserBuilder(final VGNFactory VGNFactory) {
            this.VGNFactory = VGNFactory;
        }

        public TemporaryUserBuilder invitedBy(final User inviter) {
            this.inviter = inviter;
            return this;
        }

        public TemporaryUser build() {
            validate();
            var user = new TemporaryUser(username, phoneNumber, UserDetails.newUserDefault(inviter));
            user.addDomainEvent(new TemporaryUserCreated(username, inviter));

            return user;
        }

        private void validate(){
            if (inviter == null) {
                throw new IllegalArgumentException("Inviter cannot be null");
            }
            VGNFactory.validate(VGNType.USERNAME, username);
            VGNFactory.validate(VGNType.PHONE_NUMBER, phoneNumber);
        }
    }
}
