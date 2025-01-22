package pl.zdna.gcconnect.users.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdna.gcconnect.authorization.LoggedInUserService;
import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.events.FutureCorrelation;
import pl.zdna.gcconnect.shared.interfaces.FutureCorrelationAware;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.shared.validators.VGNFactory;
import pl.zdna.gcconnect.users.domain.Privacy;
import pl.zdna.gcconnect.users.domain.ReactivationPolicy;
import pl.zdna.gcconnect.users.domain.TemporaryUser;
import pl.zdna.gcconnect.users.domain.TemporaryUserRepository;
import pl.zdna.gcconnect.users.domain.User;
import pl.zdna.gcconnect.users.domain.UserRepository;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, FutureCorrelationAware {
    private final LoggedInUserService loggedInUserService;
    private final VGNFactory VGNFactory;
    private final CorrelationEventPublisher eventPublisher;


    private final UserRepository userRepository;
    private final TemporaryUserRepository temporaryUserRepository;

    @Override
    public void activateTemporaryUser() {
        final String loggedInUsername = loggedInUserService.getLoggedInUsername();
        final TemporaryUser temporaryUser = temporaryUserRepository.getByUsername(loggedInUsername);
        final User activeUser = User.createActiveUserFrom(temporaryUser);

        userRepository.save(activeUser);

        eventPublisher.publishAll(activeUser.getDomainEvents());
        activeUser.clearDomainEvents();
    }

    @Override
    public CompletableFuture<Response> createTemporaryUser(final String invitedUsername, final String invitedPhoneNumber) {
        final FutureCorrelation futureCorrelation = newFutureCorrelation();

        final String inviterUsername = loggedInUserService.getLoggedInUsername();
        final User inviter = userRepository.getByUsername(inviterUsername);
        final var builder = TemporaryUser.with(VGNFactory);

        final TemporaryUser temporaryUser = builder.invitedBy(inviter)
                .setUsername(invitedUsername)
                .setPhoneNumber(invitedPhoneNumber)
                .build();

        temporaryUserRepository.save(temporaryUser);

        eventPublisher.withCorrelationId(futureCorrelation.correlationId())
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
