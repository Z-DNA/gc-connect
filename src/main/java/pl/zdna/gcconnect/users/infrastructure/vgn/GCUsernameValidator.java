package pl.zdna.gcconnect.users.infrastructure.vgn;

import kong.unirest.core.Empty;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.zdna.gcconnect.shared.interfaces.Validator;

@Slf4j
@Component("usernameValidator")
public class GCUsernameValidator implements Validator<String> {

    @Value("${gc-service.username-validator-url}")
    private String usernameValidatorUrl;

    @Override
    public void validate(final String string) {
        try {
            validateUsername(string);
        } catch (Exception _) {
            throw new InvalidUsernameException(string);
        }
    }

    private void validateUsername(final String username) {
        final String url = usernameValidatorUrl.formatted(username);
        final var response = Unirest.get(usernameValidatorUrl.formatted(username)).asEmpty();
        validateResponse(response, username);
    }

    private void validateResponse(final HttpResponse<Empty> response, final String username){
        if (response.getStatus() == 404) {
            throw new InvalidUsernameException(username);
        } else if (response.getStatus() != 200) {
            // TODO: handle connection errors
            log.error("Unexpected status code: {} for username: {}", response.getStatus(), username);
            throw new RuntimeException();
        }
    }
}
