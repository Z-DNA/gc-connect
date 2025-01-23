package pl.zdna.gcconnect.vgn;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberValidator implements Validator<String> {
    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
    private final Parser<String, Phonenumber.PhoneNumber> parser;

    @Override
    public void validate(final String number) {
        try{
            validateNumber(number);
        } catch (Exception _) {
            throw new InvalidPhoneNumberException(number);
        }
    }

    private void validateNumber(final @NonNull String number) throws Exception {
        var parsedNumber = parser.parse(number);
        if (isNotValidNumber(parsedNumber) || isNotMobile(parsedNumber))
            throw new Exception();
    }

    private boolean isNotValidNumber(final Phonenumber.PhoneNumber parsedNumber){
        return !PHONE_NUMBER_UTIL.isValidNumber(parsedNumber);
    }

    private boolean isNotMobile(final Phonenumber.PhoneNumber parsedNumber) {
        return !PHONE_NUMBER_UTIL.getNumberType(parsedNumber).equals(PhoneNumberUtil.PhoneNumberType.MOBILE);
    }
}
