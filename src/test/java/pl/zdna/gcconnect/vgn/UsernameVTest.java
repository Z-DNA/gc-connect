package pl.zdna.gcconnect.vgn;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsernameVTest {

    @Autowired
    private Validator<String> usernameValidator;

    @BeforeEach
    @SneakyThrows
    public void waitToProtectServiceFlood() {
        final int time = 1000 + (int) (Math.random() * 1000);
        Thread.sleep(time);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "tEsT", "valid", "VAliD"})
    //TODO GCC-39
    public void shouldValidateCorrectUsernameAsValid(final String username) {
        final boolean isValid = usernameValidator.isValid(username);
        assertTrue(isValid, "Expected username %s to be valid".formatted(username));

    }

    @ParameterizedTest
    @ValueSource(strings = {"_invalid"})
    public void shouldValidateIncorrectUsernameAsInvalid(final String username) {
        final boolean isValid = usernameValidator.isValid(username);
        assertFalse(isValid, "Expected username %s to be invalid".formatted(username));
    }
}
