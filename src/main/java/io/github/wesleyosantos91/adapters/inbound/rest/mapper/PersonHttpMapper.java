package io.github.wesleyosantos91.adapters.inbound.rest.mapper;

import io.github.wesleyosantos91.adapters.inbound.rest.request.PersonRequest;
import io.github.wesleyosantos91.adapters.inbound.rest.response.PersonResponse;
import io.github.wesleyosantos91.adapters.outbound.sql.entity.PersonEntity;
import io.github.wesleyosantos91.application.core.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonHttpMapper {

    PersonHttpMapper INSTANCE = Mappers.getMapper(PersonHttpMapper.class);
    Person toDomain(PersonRequest request);
    PersonResponse toResponse(Person person);
}
