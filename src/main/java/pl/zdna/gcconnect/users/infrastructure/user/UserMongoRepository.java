package pl.zdna.gcconnect.users.infrastructure.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserEntity, String> {
    UserEntity getByUsername(String username);
}
