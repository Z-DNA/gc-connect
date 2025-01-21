package pl.zdna.gcconnect.users.infrastructure;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.shared.validators.VGNFactory;
import pl.zdna.gcconnect.shared.validators.VGNType;
import pl.zdna.gcconnect.users.application.UserAuthenticationService;

@Service
@RequiredArgsConstructor
public class Auth0UserAuthenticationService implements UserAuthenticationService {
    private final CorrelationEventPublisher eventPublisher;
    private final Auth0Service auth0Service;
    private final VGNFactory VGNFactory;

    @Override
    public Response createAuthenticatedUser(final String username, final String inviterUsername) {
        final String password = VGNFactory.generate(VGNType.PASSWORD);
        // TODO: authorize
        throw new NotImplementedException();
//        return Response.success(new AuthenticatedUserCreatedResult(username, password));
    }
}
