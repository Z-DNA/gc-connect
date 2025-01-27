package pl.zdna.gcconnect.users.infrastructure.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import pl.zdna.gcconnect.users.domain.User;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = ".", source = "details")
    UserEntity toEntity(final User User);

    @InheritInverseConfiguration
    User toDomain(final UserEntity userEntity);
}
