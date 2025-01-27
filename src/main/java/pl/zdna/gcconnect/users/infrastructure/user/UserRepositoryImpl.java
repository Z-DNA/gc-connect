package pl.zdna.gcconnect.users.infrastructure.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import pl.zdna.gcconnect.users.application.UserRepository;
import pl.zdna.gcconnect.users.domain.User;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserMongoRepository userMongoRepository;
    private final UserMapper userMapper;

    @Override
    public void save(final User user) {
        userMongoRepository.save(userMapper.toEntity(user));
    }

    @Override
    public User getByUsername(final String username) {
        return userMapper.toDomain(userMongoRepository.getByUsername(username));
    }
}
