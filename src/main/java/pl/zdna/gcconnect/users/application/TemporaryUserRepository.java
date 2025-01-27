package pl.zdna.gcconnect.users.application;

import pl.zdna.gcconnect.shared.interfaces.SpecificationRepository;
import pl.zdna.gcconnect.users.domain.TemporaryUser;

public interface TemporaryUserRepository extends SpecificationRepository<TemporaryUser> {
    void save(TemporaryUser temporaryUser);

    void update(TemporaryUser temporaryUser);

    void deleteByUsername(String username);
}
