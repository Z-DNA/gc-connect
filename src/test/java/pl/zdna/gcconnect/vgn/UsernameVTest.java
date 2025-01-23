package pl.zdna.gcconnect.vgn;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.zdna.gcconnect.shared.interfaces.Validator;
import pl.zdna.gcconnect.users.infrastructure.vgn.GCUsernameValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsernameVTest {
    @Autowired
    @Qualifier("usernameValidator")
    private Validator<String> usernameValidator;

    @BeforeEach
    @SneakyThrows
    public void waitToProtectServiceFlood(){
        final int time = 1000 + (int) (Math.random()*1000);
        Thread.sleep(time);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Z-DNA", "z-dna", "Svarträv"})
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
