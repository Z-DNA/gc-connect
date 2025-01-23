package pl.zdna.gcconnect.vgn;

public class InvalidPhoneNumberException extends IllegalArgumentException {

    private final static String MESSAGE = "%s is invalid phone number";

    public InvalidPhoneNumberException(String number) {
        super(MESSAGE.formatted(number));
    }
}
