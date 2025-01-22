package pl.zdna.gcconnect.authorization;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.shared.interfaces.CorrelationEventPublisher;
import pl.zdna.gcconnect.shared.validators.VGNFactory;
import pl.zdna.gcconnect.shared.validators.VGNType;

@Service
@RequiredArgsConstructor
public class Auth0UserAuthorizationService implements UserAuthorizationService {
    private final CorrelationEventPublisher eventPublisher;
    private final VGNFactory VGNFactory;

    @Override
    public Response createAuthorizedUser(final String username, final String inviterUsername) {
        final String password = VGNFactory.generate(VGNType.PASSWORD);
        // TODO: authorize
        throw new NotImplementedException();
//        return Response.success(new AuthenticatedUserCreatedResult(username, password));
    }
}
