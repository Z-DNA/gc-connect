package pl.zdna.gcconnect.users.infrastructure.vgn;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.stereotype.Component;
import pl.zdna.gcconnect.shared.interfaces.Validator;

@Component
public class EmailValidator implements Validator<String> {

    @Override
    public void validate(final String string) {
        try {
            validateEmail(string);
        } catch (Exception _) {
            throw new InvalidEmailException(string);
        }
    }

    private void validateEmail(final String email) throws AddressException {
        new InternetAddress(email).validate();
    }
}
