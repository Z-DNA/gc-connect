package pl.zdna.gcconnect.users.application;

import org.springframework.data.mongodb.core.query.Criteria;

import pl.zdna.gcconnect.shared.interfaces.Specification;

/**
 * {@inheritDoc}
 *
 * @see Specification
 */
public interface TemporaryUserSpecification extends Specification {
    Criteria isExpired();

    Criteria isWaitingForActivationHavingUsername(String username);
}
