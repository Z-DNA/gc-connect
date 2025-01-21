package pl.zdna.gcconnect.users.infrastructure;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import pl.zdna.gcconnect.users.domain.TemporaryUser;
import pl.zdna.gcconnect.users.domain.TemporaryUserRepository;

@Repository
public class TemporaryUserRepositoryImpl implements TemporaryUserRepository {
    @Override
    public void save(final TemporaryUser temporaryUser) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(final TemporaryUser temporaryUser) {
        throw new NotImplementedException();
    }

    @Override
    public TemporaryUser getByUsername(final String username) {
        throw new NotImplementedException();
    }
}
