package pl.zdna.gcconnect.users.infrastructure;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import pl.zdna.gcconnect.users.domain.UserRepository;
import pl.zdna.gcconnect.users.domain.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(final User user) {
        throw new NotImplementedException();
    }

    @Override
    public User getByUsername(final String username) {
        throw new NotImplementedException();
    }
}
