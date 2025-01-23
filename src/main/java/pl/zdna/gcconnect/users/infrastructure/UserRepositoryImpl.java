package pl.zdna.gcconnect.users.infrastructure;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import pl.zdna.gcconnect.users.domain.User;
import pl.zdna.gcconnect.users.domain.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(final User user) {
        throw new NotImplementedException();
    }

    @Override
    @Deprecated
    public User getByUsername(final String username) {
        return User.unvalidatedMinimal(username);
    }
}
