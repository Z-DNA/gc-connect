package pl.zdna.gcconnect.users.username;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import pl.zdna.gcconnect.users.domain.Username;

import java.util.Arrays;

public class UsernameTest {
    @ParameterizedTest
    @UsernameSource(size = 2)
    public void usernameShouldBeCaseInsensitive(final String baseUsername) {
        final Username exemplaryUsername = new Username(baseUsername);
        final var usernames =
                Arrays.stream(StringMutator.randomizeCasing(baseUsername, 10))
                        .map(Username::new)
                        .toList();

        for (Username username : usernames) {
            assertEquals(
                    username,
                    exemplaryUsername,
                    "Expected username %s to be equal to %s"
                            .formatted(username, exemplaryUsername));
            assertEquals(
                    username.hashCode(),
                    exemplaryUsername.hashCode(),
                    "Expected username %s to have the same hash code as %s"
                            .formatted(exemplaryUsername, username));
        }
    }

    @Test
    public void differentUsernamesShouldNotBeEqual() {
        var random = RandomStringUtils.insecure();
        var username1 = new Username(random.next(10));
        var username2 = new Username(random.next(10));
        assertThat(username1).isNotEqualTo(username2);
    }
}
