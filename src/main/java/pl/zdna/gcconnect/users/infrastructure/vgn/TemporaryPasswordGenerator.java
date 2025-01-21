package pl.zdna.gcconnect.users.infrastructure.vgn;

import org.springframework.stereotype.Component;
import pl.zdna.gcconnect.shared.interfaces.Generator;

import java.util.UUID;

@Component
public class TemporaryPasswordGenerator implements Generator<String> {
    private static final int PASSWORD_LENGTH = 8;

    @Override
    public String generate() {
        return generatePassword(PASSWORD_LENGTH);
    }

    private String generatePassword(final int passwordLength) {
        final String randomHexAlphaNumeric = UUID.randomUUID().toString().replaceAll("-", "");
        return randomHexAlphaNumeric.substring(0, passwordLength);
    }
}
