package pl.zdna.gcconnect.users.infrastructure.temporaryuser;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import pl.zdna.gcconnect.users.domain.TemporaryUser;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TemporaryUserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "details")
    TemporaryUserEntity toEntity(final TemporaryUser temporaryUser);

    @Mapping(target = ".", source = "temporaryUser.details")
    TemporaryUserEntity toEntity(final TemporaryUser temporaryUser, final String id);

    @InheritInverseConfiguration
    TemporaryUser toDomain(final TemporaryUserEntity temporaryUserEntity);
}
