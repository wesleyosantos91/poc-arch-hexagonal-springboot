package io.github.wesleyosantos91.application.ports.out;

import io.github.wesleyosantos91.application.core.domain.PageInfo;
import io.github.wesleyosantos91.application.core.domain.Person;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PersonDatabasePort {

    Page<Person> find(PageInfo pageInfo);
    Optional<Person> findById(Long id);
    Person exist(Long id);
    Person create(Person person);
    Person update(Long id, Person person);
    void delete(Long id);

}
