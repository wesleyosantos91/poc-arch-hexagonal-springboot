package io.github.wesleyosantos91.application.core.service;

import io.github.wesleyosantos91.application.core.domain.PageInfo;
import io.github.wesleyosantos91.application.core.domain.Person;
import io.github.wesleyosantos91.application.ports.in.PersonServicePort;
import io.github.wesleyosantos91.application.ports.out.PersonDatabasePort;
import org.springframework.data.domain.Page;

public class PersonServiceImpl implements PersonServicePort {

    private final PersonDatabasePort personDatabasePort;

    public PersonServiceImpl(PersonDatabasePort personDatabasePort) {
        this.personDatabasePort = personDatabasePort;
    }

    @Override
    public Page<Person> find(PageInfo pageInfo) {
        return personDatabasePort.find(pageInfo);
    }

    @Override
    public Person getById(Long id) {
        return personDatabasePort.findById(id).orElseThrow();
    }

    @Override
    public Person create(Person person) {
        return personDatabasePort.create(person);
    }

    @Override
    public Person update(Long id, Person person) {
        return personDatabasePort.update(id, person);
    }

    @Override
    public void delete(Long id) {
        personDatabasePort.delete(id);
    }
}
