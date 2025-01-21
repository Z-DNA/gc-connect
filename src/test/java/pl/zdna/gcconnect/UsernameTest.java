package pl.zdna.gcconnect;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.zdna.gcconnect.users.domain.Username;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsernameTest {
    private static final String[] ZDNA_USERNAMES = {"Z-DNA", "z-dna", "Z-DNa", "Z-Dna"};
    private static final String[] SVART_USERNAMES = {"Svarträv", "SVARTRÄV", "SvarträV", "svartrÄv", "svarträv"};

    private static Stream<Arguments> usernameShouldBeCaseInsensitive(){
        return Stream.of(
                Arguments.of("Z-DNA", ZDNA_USERNAMES),
                Arguments.of("Svarträv", SVART_USERNAMES));
    }

    @ParameterizedTest
    @MethodSource
    public void usernameShouldBeCaseInsensitive(String exemplaryUsernameString, String[] usernameStrings) {
        final Username exemplaryUsername = new Username(exemplaryUsernameString);
        final var usernames = Arrays.stream(usernameStrings).map(Username::new).toList();

        for (Username username : usernames) {
            assertEquals(username, exemplaryUsername, "Expected username %s to be equal to %s".formatted(username, exemplaryUsername));
            assertEquals(username.hashCode(), exemplaryUsername.hashCode(), "Expected username %s to have the same hash code as %s".formatted(exemplaryUsername, username));
        }
    }

    @Test
    public void differentUsernamesShouldNotBeEqual() {
        for(String zUsername : ZDNA_USERNAMES) {
            for(String sUsername : SVART_USERNAMES) {
                assertThat(zUsername).isNotEqualTo(sUsername);
            }
        }
    }
}
