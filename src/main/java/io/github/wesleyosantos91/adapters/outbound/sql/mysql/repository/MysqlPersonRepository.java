package io.github.wesleyosantos91.adapters.outbound.sql.mysql.repository;

import io.github.wesleyosantos91.application.core.domain.Person;
import io.github.wesleyosantos91.application.core.exception.ObjectNotFoundException;
import io.github.wesleyosantos91.application.ports.PersonRepositoryPort;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.wesleyosantos91.adapters.outbound.sql.mapper.PersonPersistenceMapper.INSTANCE;
import static java.lang.String.format;

@Component
@Primary
public class MysqlPersonRepository implements PersonRepositoryPort {

    private final PersonRepository repository;

    public MysqlPersonRepository(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> findAll() {
        return repository.findAll().stream().map(INSTANCE::toDomain).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Person> findById(Long id) {
        return Optional.of(exist(id));
    }

    @Override
    public Person exist(Long id) {
        var personEntity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(format("Not found regitstry with code %d", id)));

        return INSTANCE.toDomain(personEntity);
    }

    @Override
    public Person save(Person person) {
        return INSTANCE.toDomain(repository.save(INSTANCE.toEntity(person)));
    }

    @Override
    public Person update(Long id, Person person) {
        var personSaved = exist(id);
        BeanUtils.copyProperties(person, personSaved);
        return INSTANCE.toDomain(repository.save(INSTANCE.toEntity(personSaved)));

    }

    @Override
    public void delete(Long id) {
        var personSaved = exist(id);
        repository.delete(INSTANCE.toEntity(personSaved));
    }
}
