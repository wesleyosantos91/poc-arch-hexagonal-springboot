package io.github.wesleyosantos91.adapter.outbound.sql.mapper;

import io.github.wesleyosantos91.adapter.outbound.sql.entity.PersonEntity;
import io.github.wesleyosantos91.application.core.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PersonPersistenceMapper {

    PersonPersistenceMapper INSTANCE = Mappers.getMapper(PersonPersistenceMapper.class);
    Person toDomain(PersonEntity entity);
    PersonEntity toEntity(Person person);


    default List<Person> toListDomain(List<PersonEntity> entities){
        List<Person> list = new ArrayList<>();
        entities.forEach(d-> list.add(toDomain(d)));
        return list;
    }

    default Page<Person> toPageDomain(Page<PersonEntity> pages){
        List<Person> list = toListDomain(pages.getContent());
        return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());

    }
}
