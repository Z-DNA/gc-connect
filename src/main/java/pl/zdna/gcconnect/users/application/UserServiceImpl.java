package pl.zdna.gcconnect.users.application;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.events.FutureCorrelation;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.users.domain.Privacy;
import pl.zdna.gcconnect.users.domain.ReactivationPolicy;
import pl.zdna.gcconnect.users.domain.TemporaryUser;
import pl.zdna.gcconnect.users.domain.User;
import pl.zdna.gcconnect.vgn.VGNFactory;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final LoggedInUserService loggedInUserService;
    private final VGNFactory VGNFactory;
    private final CorrelationEventPublisher eventPublisher;

    private final UserRepository userRepository;
    private final TemporaryUserRepository temporaryUserRepository;
    private final TemporaryUserSpecification temporaryUserSpecification;

    @Override
    public void activateTemporaryUser(final String username, String userId) {
        val specs = temporaryUserSpecification.isWaitingForActivationHavingUsername(username);
        val temporaryUser = temporaryUserRepository.getOneBy(specs);
        final User activeUser = User.createActiveUserFrom(temporaryUser, userId);
        temporaryUser.useDetailsFromActivatedUser(activeUser);

        userRepository.save(activeUser);
        temporaryUserRepository.update(temporaryUser);

        eventPublisher.publishAll(activeUser.getDomainEvents());
        activeUser.clearDomainEvents();
    }

    @Override
    public CompletableFuture<Response> createTemporaryUser(
            final String invitedUsername, final String invitedPhoneNumber) {
        final FutureCorrelation futureCorrelation = newFutureCorrelation();

        final String inviterUsername = loggedInUserService.getLoggedInUsername(); // TODO GCC-43
        final User inviter = userRepository.getByUsername(inviterUsername);

        final TemporaryUser temporaryUser =
                TemporaryUser.with(VGNFactory)
                        .invitedBy(inviter)
                        .setUsername(invitedUsername)
                        .setPhoneNumber(invitedPhoneNumber)
                        .build(); // TODO GCC-44

        temporaryUserRepository.save(temporaryUser);

        eventPublisher
                .withCorrelationId(futureCorrelation.correlationId())
                .publishAll(temporaryUser.getDomainEvents());
        temporaryUser.clearDomainEvents();

        return futureCorrelation.futureResponse();
    }

    @Override
    public void changePhoneNumber(final String phoneNumber) {
        final User user = getLoggedInUser();
        user.withValNormFactory(VGNFactory).changePhoneNumber(phoneNumber);

        eventPublisher.publishAll(user.getDomainEvents());
        user.clearDomainEvents();
    }

    private User getLoggedInUser() {
        final String loggedInUsername = loggedInUserService.getLoggedInUsername();
        return userRepository.getByUsername(loggedInUsername);
    }

    @Override
    public void changeEmail(final String email) {
        final User user = getLoggedInUser();
        user.withValNormFactory(VGNFactory).setEmail(email);
    }

    @Override
    public void changePrivacy(final Privacy privacy) {
        final User user = getLoggedInUser();
        user.changePrivacy(privacy);
    }

    @Override
    public void changeReactivationPolicy(final ReactivationPolicy reactivationPolicy) {
        final User user = getLoggedInUser();
        user.changeReactivationPolicy(reactivationPolicy);
    }
}
