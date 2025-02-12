package pl.zdna.gcconnect.vgn;

public interface Validator<U> {

    void validate(U u);

    default boolean isValid(U u) {
        try {
            validate(u);
            return true;
        } catch (Exception _) {
            return false;
        }
    }
}
