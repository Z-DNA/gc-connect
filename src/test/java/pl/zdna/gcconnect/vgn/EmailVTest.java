package pl.zdna.gcconnect.vgn;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zdna.gcconnect.shared.interfaces.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmailVTest {
    @Autowired
    private Validator<String> emailValidator;

    @ParameterizedTest(name = "Email {0} is not valid")
    @ValueSource(strings = {"correct.email@gmail.com", "correct.email+test@subsub.sub.domail.com",
            "jon.o'conner@example.com", "\".John..Doe\"@example.com"})
    public void shouldValidateCorrectEmailAsValid(final String email){
        boolean emailIsValid = emailValidator.isValid(email);
        assertTrue(emailIsValid, "Expected email %s to be valid".formatted(email));
    }

    @ParameterizedTest(name = "Email {0} is not valid")
    @NullAndEmptySource
    @ValueSource(strings = {"@gmail.com", "invalid.email",
            "incorrect.email.@gmail.com", "incorrect email@.gmail.com"})
    public void shouldValidateIncorrectEmailAsInvalid(final String email){
        boolean emailIsValid = emailValidator.isValid(email);
        assertFalse(emailIsValid, "Expected email %s to be invalid".formatted(email));
    }
}
