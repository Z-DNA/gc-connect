package pl.zdna.gcconnect.shared;

public class EntityNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Entity %s was not found.";

    public EntityNotFoundException(final Class<? extends PersistenceEntity> entity) {
        super(MESSAGE.formatted(entity.getSimpleName()));
    }
}
