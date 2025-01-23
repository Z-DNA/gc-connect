package pl.zdna.gcconnect.vgn;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.zdna.gcconnect.shared.interfaces.Validator;
import pl.zdna.gcconnect.users.infrastructure.vgn.PhoneNumberNormalizer;
import pl.zdna.gcconnect.users.infrastructure.vgn.PhoneNumberParser;
import pl.zdna.gcconnect.users.infrastructure.vgn.PhoneNumberValidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneNumberVNTest {
    //TODO: Create PhoneNumberFactory and mutatePhoneNumber(String phoneNumber)
    // for creating valid but not normalized forms

    //Valid phoneNumber formats of number +48 512 345 678
    private static final String[] VALID_PHONE_NUMBERS = {"+48 512 345 678", "512 345 678",
            "512-345-678", "5 1 2 3 4 5 6 7 8", "51 234 56 78"};

    //Valid numbers that are not phoneNumber numbers of number +48 12 345 67 89
    private static final String[] NOT_MOBILE_VALID_PHONE_NUMBERS = {"+48 123 456 789", "123 456 789",
            "1 2 3  4 5 6  7 8 9", "1 2 3 -  4 5 6 - 7 8 9", "123456789", "12 345 67 89"};

    private static final String[] INVALID_PARSABLE_PHONE_NUMBERS = {"123 456 7890", "0123456789", "+48 00 000 00"};

    private static final String[] NOT_PHONE_NUMBERS = {"NotANumber", "0", "+48", "5H2 3Z5 67W"};

    private static final PhoneNumberParser phoneNumberParser = new PhoneNumberParser();

    @Nested
    class PhoneNumberValidatorTest{
        private static final String[] VALID_PHONE_NUMBERS = PhoneNumberVNTest.VALID_PHONE_NUMBERS;
        private static final String[] NOT_MOBILE_VALID_PHONE_NUMBERS = PhoneNumberVNTest.NOT_MOBILE_VALID_PHONE_NUMBERS;
        private static final String[] INVALID_PARSABLE_PHONE_NUMBERS = PhoneNumberVNTest.INVALID_PARSABLE_PHONE_NUMBERS;
        private static final String[] NOT_PHONE_NUMBERS = PhoneNumberVNTest.NOT_PHONE_NUMBERS;

        private final Validator<String> phoneNumberValidator = new PhoneNumberValidator(phoneNumberParser);

        @ParameterizedTest(name = "Phone number {0} is valid phoneNumber number")
        @FieldSource("VALID_PHONE_NUMBERS")
        public void shouldValidateCorrectPhoneNumberAsValid(final String phoneNumber) {
            boolean phoneNumberIsValid = phoneNumberValidator.isValid(phoneNumber);
            assertTrue(phoneNumberIsValid, "Expected phone number %s to be valid".formatted(phoneNumber));
        }

        @ParameterizedTest(name = "{0} is not valid phone number")
        @NullAndEmptySource
        @FieldSource({"INVALID_PARSABLE_PHONE_NUMBERS", "NOT_MOBILE_VALID_PHONE_NUMBERS", "NOT_PHONE_NUMBERS"})
        public void shouldValidateIncorrectPhoneNumberAsInvalid(final String phoneNumber) {
            boolean phoneNumberIsValid = phoneNumberValidator.isValid(phoneNumber);
            assertFalse(phoneNumberIsValid, "Expected phone number %s to be invalid".formatted(phoneNumber));
        }
    }

    @Nested
    class PhoneNumberNormalizerTest{
        private static final String MOBILE_NORMALIZED_PHONE_NUMBER = "+48 512 345 678";
        private static final String[] VALID_PHONE_NUMBERS = PhoneNumberVNTest.VALID_PHONE_NUMBERS;

        private static final String NOT_MOBILE_NORMALIZED_PHONE_NUMBER = "+48 12 345 67 89";
        private static final String[] NOT_MOBILE_VALID_PHONE_NUMBERS = PhoneNumberVNTest.NOT_MOBILE_VALID_PHONE_NUMBERS;

        private static final String[] INVALID_PARSABLE_PHONE_NUMBERS = PhoneNumberVNTest.INVALID_PARSABLE_PHONE_NUMBERS;
        private static final String[] NOT_PHONE_NUMBERS = PhoneNumberVNTest.NOT_PHONE_NUMBERS;

        private final PhoneNumberNormalizer phoneNumberNormalizer = new PhoneNumberNormalizer(phoneNumberParser);

        @ParameterizedTest(name = "Valid phoneNumber phone number {0} is normalized correctly")
        @FieldSource("VALID_PHONE_NUMBERS")
        public void shouldNormalizeMobilePhoneNumber(final String phoneNumber) {
            String normalizedPhoneNumber = phoneNumberNormalizer.normalize(phoneNumber);
            assertThat(normalizedPhoneNumber).isEqualTo(MOBILE_NORMALIZED_PHONE_NUMBER);
        }

        @ParameterizedTest(name = "Valid not phoneNumber phone number {0} is normalized correctly")
        @FieldSource("NOT_MOBILE_VALID_PHONE_NUMBERS")
        public void shouldNormalizePolandValidPhoneNumber(final String phoneNumber) {
            String normalizedPhoneNumber = phoneNumberNormalizer.normalize(phoneNumber);
            assertThat(normalizedPhoneNumber).isEqualTo(NOT_MOBILE_NORMALIZED_PHONE_NUMBER);
        }

        @ParameterizedTest(name = "Valid parsable phone number {0} is normalized correctly")
        @FieldSource("INVALID_PARSABLE_PHONE_NUMBERS")
        public void shouldNormalizeParsablePhoneNumberWithoutException(final String phoneNumber) {
            assertDoesNotThrow(() -> phoneNumberNormalizer.normalize(phoneNumber));
        }

        @ParameterizedTest(name = "String {0} should throw exception while normalizing")
        @NullAndEmptySource
        @FieldSource("NOT_PHONE_NUMBERS")
        public void shouldThrowExceptionIfPhoneNumberCannotBeNormalized(final String phoneNumber) {
            assertThrows(Exception.class, () -> phoneNumberNormalizer.normalize(phoneNumber));
        }

        @ParameterizedTest
        @FieldSource({"VALID_PHONE_NUMBERS", "NOT_MOBILE_VALID_PHONE_NUMBERS", "INVALID_PARSABLE_PHONE_NUMBERS"})
        public void normalizationShouldBeIdempotent(final String phoneNumber) {
            final String normalizedPhoneNumber = phoneNumberNormalizer.normalize(phoneNumber);
            final String secondarilyNormalizedPhoneNumber = phoneNumberNormalizer.normalize(normalizedPhoneNumber);

            assertThat(normalizedPhoneNumber).isEqualTo(secondarilyNormalizedPhoneNumber);
        }
    }
}
