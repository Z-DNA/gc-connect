package pl.zdna.gcconnect.users.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.zdna.gcconnect.shared.Entity;
import pl.zdna.gcconnect.shared.validators.VGNFactory;
import pl.zdna.gcconnect.shared.validators.VGNType;
import pl.zdna.gcconnect.users.domain.events.PhoneNumberChanged;
import pl.zdna.gcconnect.users.domain.events.UserActivated;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public final class User extends Entity {
    private final String username;
    private String phoneNumber;
    private final UserDetails userDetails;

    private String email;
    private Privacy privacy = Privacy.getDefault();
    private ReactivationPolicy reactivationPolicy = ReactivationPolicy.getDefault();

    public static User createActiveUserFrom(final TemporaryUser temporaryUser) {
        return new User(temporaryUser.getUsername(), temporaryUser.getPhoneNumber(), temporaryUser.getDetails());
    }

    @Deprecated
    public static User unvalidatedMinimal(final String username){
        return new User(username, null, UserDetails.uninvited());
    }

    private User(final String username, final String phoneNumber, final UserDetails userDetails){
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.userDetails = userDetails.withActiveStatus();

        addDomainEvent(new UserActivated(this));
    }

    public String getInviterUsername() {
        return userDetails.getInviterUsername();
    }

    public void changePrivacy(final Privacy privacy) {
        this.privacy = privacy;
    }

    public void changeReactivationPolicy(final ReactivationPolicy reactivationPolicy) {
        this.reactivationPolicy = reactivationPolicy;
    }

    public UserWithValNormFactory withValNormFactory(final @NonNull VGNFactory VGNFactory) {
        return new UserWithValNormFactory(VGNFactory);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class UserWithValNormFactory {
        private final VGNFactory VGNFactory;

        public void setEmail(final String email) {
            VGNFactory.validate(VGNType.EMAIL, email);
            User.this.email = email;
        }

        public void changePhoneNumber(final String phoneNumber) {
            VGNFactory.validate(VGNType.PHONE_NUMBER, phoneNumber);
            final String normalizedPhoneNumber = normalizePhoneNumber(phoneNumber);
            User.this.phoneNumber = normalizedPhoneNumber;

            addDomainEvent(new PhoneNumberChanged(username, normalizedPhoneNumber));
        }

        private String normalizePhoneNumber(final String phoneNumber) {
            return VGNFactory.normalize(VGNType.PHONE_NUMBER, phoneNumber);
        }
    }
}

