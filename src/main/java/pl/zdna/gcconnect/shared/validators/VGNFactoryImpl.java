package pl.zdna.gcconnect.shared.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.zdna.gcconnect.shared.interfaces.Generator;
import pl.zdna.gcconnect.shared.interfaces.Normalizer;
import pl.zdna.gcconnect.shared.interfaces.Validator;

@Component
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "SwitchStatementWithTooFewBranches"})
public class VGNFactoryImpl implements VGNFactory {
    private final Validator<?> phoneNumberValidator;
    private final Validator<?> emailValidator;
    private final Validator<?> usernameValidator;

    private final Normalizer<?> phoneNumberNormalizer;

    private final Generator<?> temporaryPasswordGenerator;

    public <T> void validate(final VGNType type, final T object) {
        Validator<T> validator = (Validator<T>) switch (type) {
            case PHONE_NUMBER -> phoneNumberValidator;
            case EMAIL -> emailValidator;
            case USERNAME -> usernameValidator;
            default -> throw new IllegalArgumentException("Unknown validator type: " + type);
        };

        validator.validate(object);
    }

    public <T> T normalize(final VGNType type, final T object) {
        Normalizer<T> normalizer = (Normalizer<T>) switch (type) {
            case PHONE_NUMBER -> phoneNumberNormalizer;
            default -> throw new IllegalArgumentException("Unknown normalizer type: " + type);
        };

        return normalizer.normalize(object);
    }

    public <T> T generate(final VGNType type) {
        Generator<T> generator = (Generator<T>) switch (type) {
            case PASSWORD -> temporaryPasswordGenerator;
            default -> throw new IllegalArgumentException("Unknown generator type: " + type);
        };

        return generator.generate();
    }
}
