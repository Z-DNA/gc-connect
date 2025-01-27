package pl.zdna.gcconnect.users.application;

import pl.zdna.gcconnect.users.domain.User;

public interface UserRepository {
    void save(User user);

    User getByUsername(String username);
}
