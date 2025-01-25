package pl.zdna.gcconnect.users.username;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.io.IOException;
import java.util.Random;
import java.util.stream.Stream;

@Log4j2
public class UsernameArgumentProvider
        implements ArgumentsProvider, AnnotationConsumer<UsernameSource> {
    private final String[] valids;
    private final String[] invalids;

    private int size;
    private boolean provideValidArguments;

    public UsernameArgumentProvider() throws IOException {
        valids = PropertyLoader.getPropertyValues("usernames.valid");
        invalids = PropertyLoader.getPropertyValues("usernames.invalid");
        log.debug("Valid[].size: {}", valids.length);
        log.debug("Invalid[].size: {}", invalids.length);
    }

    @Override
    public void accept(UsernameSource usernameSource) {
        this.size = usernameSource.size();
        this.provideValidArguments = usernameSource.status();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        log.debug("Valid[].size: {}, Invalid[].size: {}", valids.length, invalids.length);
        final String[] usernames = provideValidArguments ? valids : invalids;
        return new Random()
                .ints(0, usernames.length)
                .mapToObj(i -> usernames[i])
                .map(StringMutator::randomizeCasing)
                .limit(size)
                .map(Arguments::of);
    }
}
