package pl.zdna.gcconnect.users.username;

import java.util.Random;

public class StringMutator {
    private static final Random random = new Random();

    public static String[] randomizeCasing(final String input, final int mutations) {
        final String[] mutatedStrings = new String[mutations];
        for (int i = 0; i < mutations; i++) {
            mutatedStrings[i] = randomizeCasing(input);
        }
        return mutatedStrings;
    }

    public static String randomizeCasing(final String input) {
        return input.codePoints()
                .map(StringMutator::randomizeCodePointCase)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static int randomizeCodePointCase(final int codePoint) {
        return random.nextBoolean()
                ? Character.toUpperCase(codePoint)
                : Character.toLowerCase(codePoint);
    }
}
