package pl.zdna.gcconnect.users.infrastructure;

import org.springframework.stereotype.Repository;

import pl.zdna.gcconnect.users.domain.TemporaryUser;
import pl.zdna.gcconnect.users.domain.TemporaryUserRepository;

@Repository
public class TemporaryUserRepositoryImpl implements TemporaryUserRepository {
    @Override
    public void save(final TemporaryUser temporaryUser) {
        // TODO GCC-34
    }

    @Override
    public void delete(final TemporaryUser temporaryUser) {
        // TODO GCC-34
    }

    @Override
    public TemporaryUser getByUsername(final String username) {
        // TODO GCC-34
        return null;
    }
}
