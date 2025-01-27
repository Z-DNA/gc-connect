package pl.zdna.gcconnect.users.infrastructure.temporaryuser;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import pl.zdna.gcconnect.users.application.TemporaryUserSpecification;
import pl.zdna.gcconnect.users.domain.UserAccountStatus;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Log4j2
@Component
@PropertySource("classpath:specification/users.yml")
@ConfigurationProperties(prefix = "temporary-user")
public class TemporaryUserCriteria implements TemporaryUserSpecification {
    @Value("${expiration-in-days}")
    private int expirationInDays; // TODO GCC-47

    @Override
    public Criteria isExpired() {
        return Criteria.where(TemporaryUserEntity.Fields.status)
                .is(UserAccountStatus.INVITED)
                .and(TemporaryUserEntity.Fields.creationTimestamp)
                .lt(Instant.now().minus(expirationInDays, ChronoUnit.DAYS));
    }

    @Override
    public Criteria isWaitingForActivationHavingUsername(String username) {
        return Criteria.where(TemporaryUserEntity.Fields.username)
                .is(username.toLowerCase())
                .and(TemporaryUserEntity.Fields.status)
                .is(UserAccountStatus.INVITED)
                .norOperator(isExpired());
    }
}
