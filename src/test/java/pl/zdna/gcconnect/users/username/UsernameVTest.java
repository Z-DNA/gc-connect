package pl.zdna.gcconnect.users.username;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pl.zdna.gcconnect.ExtendedTest;
import pl.zdna.gcconnect.vgn.GCUsernameValidator;
import pl.zdna.gcconnect.vgn.Validator;

@Log4j2
@SpringBootTest(classes = {GCUsernameValidator.class})
public class UsernameVTest {
    @Autowired private Validator<String> usernameValidator;

    @BeforeEach
    @SneakyThrows
    public void waitToProtectServiceFlood() {
        final int time = (int) (Math.random() * 1000);
        Thread.sleep(time);
    }

    @ParameterizedTest
    @UsernameSource(size = 1)
    public void shouldValidateCorrectUsernameAsValidMinimal(final String username) {
        final boolean isValid = usernameValidator.isValid(username);
        assertTrue(isValid, "Expected username %s to be valid".formatted(username));
    }

    @ExtendedTest
    @ParameterizedTest
    @UsernameSource(size = 3)
    public void shouldValidateCorrectUsernameAsValid(final String username) {
        final boolean isValid = usernameValidator.isValid(username);
        assertTrue(isValid, "Expected username %s to be valid".formatted(username));
    }

    @ExtendedTest
    @ParameterizedTest
    @UsernameSource(size = 3, status = UsernameSource.INVALID)
    public void shouldValidateIncorrectUsernameAsInvalid(final String username) {
        final boolean isValid = usernameValidator.isValid(username);
        assertFalse(isValid, "Expected username %s to be invalid".formatted(username));
    }
}
