package pl.zdna.gcconnect.shared.interfaces;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public interface SpecificationRepository<E> {
    Iterable<E> findAllBy(Criteria specification);

    Optional<E> findOneBy(Criteria specification);

    default E getOneBy(Criteria specification) {
        return findOneBy(specification).orElseThrow(); // TODO GCC-44
    }
}
