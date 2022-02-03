package io.github.wesleyosantos91.adapters.outbound.sql.mapper;

import io.github.wesleyosantos91.adapters.outbound.sql.entity.PersonEntity;
import io.github.wesleyosantos91.application.core.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonPersistenceMapper {

    PersonPersistenceMapper INSTANCE = Mappers.getMapper(PersonPersistenceMapper.class);
    Person toDomain(PersonEntity entity);
    PersonEntity toEntity(Person person);
}
