package pl.zdna.gcconnect.users.infrastructure;

import org.springframework.stereotype.Repository;

import pl.zdna.gcconnect.users.domain.User;
import pl.zdna.gcconnect.users.domain.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(final User user) {
        // TODO GCC-35
    }

    @Override
    public User getByUsername(final String username) {
        // TODO GCC-35
        return User.unvalidatedMinimal(username);
    }
}
