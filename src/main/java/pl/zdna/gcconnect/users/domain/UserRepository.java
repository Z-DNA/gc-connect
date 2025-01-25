package pl.zdna.gcconnect.users.domain;

public interface UserRepository {
    void save(User user);

    User getByUsername(String username);
}
