package pl.zdna.gcconnect.users.infrastructure.temporaryuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import pl.zdna.gcconnect.users.application.TemporaryUserRepository;
import pl.zdna.gcconnect.users.application.TemporaryUserSpecification;
import pl.zdna.gcconnect.users.domain.TemporaryUser;

import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class TemporaryUserRepositoryImpl implements TemporaryUserRepository {
    private final TemporaryUserMongoRepository temporaryUserMongoRepository;
    private final MongoTemplate mongoTemplate;
    private final TemporaryUserMapper temporaryUserMapper;
    private final TemporaryUserSpecification temporaryUserSpecification;

    @Override
    public void save(final TemporaryUser temporaryUser) {
        val entity = temporaryUserMapper.toEntity(temporaryUser);
        temporaryUserMongoRepository.save(entity);
    }

    @Override
    public void update(final TemporaryUser temporaryUser) {
        val id = getIdForUsername(temporaryUser.getUsername());
        val entity = temporaryUserMapper.toEntity(temporaryUser, id);
        temporaryUserMongoRepository.save(entity);
    }

    private String getIdForUsername(final String username) {
        val spec = temporaryUserSpecification.isWaitingForActivationHavingUsername(username);
        val entity = mongoTemplate.findOne(new Query(spec), TemporaryUserEntity.class);
        return entity == null ? null : entity.getId();
    }

    @Override
    public void deleteByUsername(final String username) {
        log.debug("Deleting temporary user {}", username);
        val spec = temporaryUserSpecification.isWaitingForActivationHavingUsername(username);
        val result = mongoTemplate.remove(new Query(spec), TemporaryUserEntity.class);
        log.debug("Deleted: {}", result);
    }

    @Override
    public Iterable<TemporaryUser> findAllBy(Criteria specification) {
        return mongoTemplate.find(new Query(specification), TemporaryUserEntity.class).stream()
                .map(temporaryUserMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<TemporaryUser> findOneBy(Criteria specification) {
        val entity = mongoTemplate.findOne(new Query(specification), TemporaryUserEntity.class);
        return Optional.ofNullable(temporaryUserMapper.toDomain(entity));
    }
}
