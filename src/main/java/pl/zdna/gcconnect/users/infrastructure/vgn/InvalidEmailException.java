package pl.zdna.gcconnect.users.infrastructure.vgn;

public class InvalidEmailException extends RuntimeException {

    private final static String MESSAGE = "%s is not valid email address";

    public InvalidEmailException(String email) {
        super(MESSAGE.formatted(email));
    }
}
