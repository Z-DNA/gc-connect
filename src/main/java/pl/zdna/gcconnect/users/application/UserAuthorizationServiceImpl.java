package pl.zdna.gcconnect.users.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.users.application.results.AuthenticatedUserCreatedResult;
import pl.zdna.gcconnect.vgn.VGNFactory;
import pl.zdna.gcconnect.vgn.VGNType;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserAuthorizationServiceImpl implements UserAuthorizationService {
    private final AuthorizationHttpService authorizationHttpService;
    private final VGNFactory vgnFactory;

    @Override
    public Response createAuthorizedUser(final String username, final String inviterUsername) {
        log.debug("Creating authorized user {}", username);
        final String password = vgnFactory.generate(VGNType.PASSWORD);

        try {
            authorizationHttpService.createNewUser(username, password, inviterUsername);
        } catch (Exception e) {
            // TODO GCC-31
            return Response.failure(e.getMessage());
        }

        return Response.success(
                new AuthenticatedUserCreatedResult(username, password, inviterUsername));
    }
}
