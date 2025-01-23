package pl.zdna.gcconnect.vgn;

public interface VGNFactory {
    <T> void validate(final VGNType type, final T object);
    <T> boolean isValid(final VGNType type, final T object);
    <T> boolean isNotValid(final VGNType type, final T object);
    <T> T normalize(final VGNType type, final T object);
    <T> T generate(final VGNType type);
}
