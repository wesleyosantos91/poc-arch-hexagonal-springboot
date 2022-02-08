package io.github.wesleyosantos91.adapter.outbound.sql.mysql.repository;

import io.github.wesleyosantos91.adapter.outbound.sql.entity.PersonEntity;
import io.github.wesleyosantos91.application.core.domain.PageInfo;
import io.github.wesleyosantos91.application.core.domain.Person;
import io.github.wesleyosantos91.application.core.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.application.ports.out.PersonDatabasePort;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.github.wesleyosantos91.adapter.outbound.sql.mapper.PersonPersistenceMapper.INSTANCE;
import static java.lang.String.format;

@Component
@Primary
public class MysqlPersonDatabase implements PersonDatabasePort {

    private final PersonRepository repository;

    public MysqlPersonDatabase(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Person> find(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        Page<PersonEntity> page = repository.findAll(pageable);
        return INSTANCE.toPageDomain(page);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Person> findById(Long id) {
        return Optional.of(exist(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Person exist(Long id) {
        var personEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Not found regitstry with code %d", id)));

        return INSTANCE.toDomain(personEntity);
    }

    @Transactional
    @Override
    public Person create(Person person) {
        return INSTANCE.toDomain(repository.save(INSTANCE.toEntity(person)));
    }

    @Transactional
    @Override
    public Person update(Long id, Person person) {
        var personSaved = exist(id);
        BeanUtils.copyProperties(person, personSaved, "id");
        return INSTANCE.toDomain(repository.save(INSTANCE.toEntity(personSaved)));

    }

    @Transactional
    @Override
    public void delete(Long id) {
        var personSaved = exist(id);
        repository.delete(INSTANCE.toEntity(personSaved));
    }
}
