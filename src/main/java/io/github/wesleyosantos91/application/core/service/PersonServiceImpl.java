package io.github.wesleyosantos91.application.core.service;

import io.github.wesleyosantos91.application.core.domain.Person;
import io.github.wesleyosantos91.application.ports.PersonRepositoryPort;
import io.github.wesleyosantos91.application.ports.PersonServicePort;

import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonServicePort {

    private final PersonRepositoryPort repositoryPort;

    public PersonServiceImpl(PersonRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Person> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return repositoryPort.findById(id);
    }

    @Override
    public Person exist(Long id) {
        return repositoryPort.exist(id);
    }

    @Override
    public Person save(Person person) {
        return repositoryPort.save(person);
    }

    @Override
    public Person update(Long id, Person person) {
        return repositoryPort.update(id, person);
    }

    @Override
    public void delete(Long id) {
        repositoryPort.delete(id);
    }
}
