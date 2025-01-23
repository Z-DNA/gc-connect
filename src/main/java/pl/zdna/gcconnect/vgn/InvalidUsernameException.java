package pl.zdna.gcconnect.vgn;

public class InvalidUsernameException extends IllegalArgumentException {

    private final static String MESSAGE = "%s does not exist";

    public InvalidUsernameException(String username) {
        super(MESSAGE.formatted(username));
    }
}
