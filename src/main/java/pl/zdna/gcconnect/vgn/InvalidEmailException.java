package pl.zdna.gcconnect.vgn;

public class InvalidEmailException extends IllegalArgumentException {

    private static final String MESSAGE = "%s is not valid email address";

    public InvalidEmailException(String email) {
        super(MESSAGE.formatted(email));
    }
}
