package pl.zdna.gcconnect.users.infrastructure.temporaryuser;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemporaryUserMongoRepository
        extends MongoRepository<TemporaryUserEntity, String> {}
