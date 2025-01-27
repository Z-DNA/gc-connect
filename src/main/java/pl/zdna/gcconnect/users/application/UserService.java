package pl.zdna.gcconnect.users.application;

import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.interfaces.FutureCorrelationAware;
import pl.zdna.gcconnect.users.domain.Privacy;
import pl.zdna.gcconnect.users.domain.ReactivationPolicy;

import java.util.concurrent.CompletableFuture;

public interface UserService extends FutureCorrelationAware {

    void activateTemporaryUser(String username, String userId);

    CompletableFuture<Response> createTemporaryUser(
            String invitedUsername, String invitedPhoneNumber);

    void changePhoneNumber(String phoneNumber);

    void changeEmail(String email);

    void changePrivacy(Privacy privacy);

    void changeReactivationPolicy(ReactivationPolicy reactivationPolicy);
}
