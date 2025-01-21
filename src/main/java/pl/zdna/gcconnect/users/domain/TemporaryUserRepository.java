package pl.zdna.gcconnect.users.domain;

public interface TemporaryUserRepository {
    void save(TemporaryUser temporaryUser);
    void delete(TemporaryUser temporaryUser);
    TemporaryUser getByUsername(String username);
}
