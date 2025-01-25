package pl.zdna.gcconnect.vgn;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.springframework.stereotype.Component;

@Component
public class PhoneNumberParser implements Parser<String, Phonenumber.PhoneNumber> {
    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

    @Override
    public Phonenumber.PhoneNumber parse(final String s) throws NumberParseException {
        return PHONE_NUMBER_UTIL.parse(s, "PL");
    }
}
