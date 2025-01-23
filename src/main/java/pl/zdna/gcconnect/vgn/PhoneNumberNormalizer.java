package pl.zdna.gcconnect.vgn;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberNormalizer implements Normalizer<String> {
    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
    private final Parser<String, Phonenumber.PhoneNumber> parser;

    @SneakyThrows
    @Override
    public String normalize(String number) {
        final Phonenumber.PhoneNumber parsedNumber = parser.parse(number);
        return PHONE_NUMBER_UTIL.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    }
}
