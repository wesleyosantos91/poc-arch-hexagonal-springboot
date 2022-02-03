package io.github.wesleyosantos91.application.ports.in;

import io.github.wesleyosantos91.application.core.domain.PageInfo;
import io.github.wesleyosantos91.application.core.domain.Person;
import org.springframework.data.domain.Page;

public interface PersonServicePort {

    Page<Person> find(PageInfo pageInfo);
    Person getById(Long id);
    Person create(Person person);
    Person update(Long id, Person person);
    void delete(Long id);

}
