package pl.zdna.gcconnect.users.infrastructure.vgn;

public class InvalidUsernameException extends RuntimeException {

    private final static String MESSAGE = "%s does not exist";

    public InvalidUsernameException(String username) {
        super(MESSAGE.formatted(username));
    }
}
