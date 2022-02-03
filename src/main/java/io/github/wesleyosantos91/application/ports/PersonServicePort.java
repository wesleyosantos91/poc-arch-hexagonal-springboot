package io.github.wesleyosantos91.application.ports;

import io.github.wesleyosantos91.application.core.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonServicePort {

    List<Person> findAll();
    Person findById(Long id);
    Person exist(Long id);
    Person save(Person person);
    Person update(Long id, Person person);
    void delete(Long id);

}
