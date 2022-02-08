package io.github.wesleyosantos91.adapter.inbound.rest.v1.mapper;

import io.github.wesleyosantos91.adapter.inbound.rest.v1.request.PersonRequest;
import io.github.wesleyosantos91.adapter.inbound.rest.v1.response.PersonResponse;
import io.github.wesleyosantos91.application.core.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PersonHttpMapper {

    PersonHttpMapper INSTANCE = Mappers.getMapper(PersonHttpMapper.class);
    Person toDomain(PersonRequest request);
    PersonResponse toResponse(Person person);

    default List<PersonResponse> toListResponse(List<Person> domains){
        List<PersonResponse> list = new ArrayList<>();
        domains.forEach(d-> list.add(toResponse(d)));
        return list;
    }

    default Page<PersonResponse> toPageResponse(Page<Person> pages){
        List<PersonResponse> list = toListResponse(pages.getContent());
        return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());

    }
}
