package io.github.wesleyosantos91.adapters.inbound.rest.controller;

import io.github.wesleyosantos91.adapters.inbound.rest.mapper.PersonHttpMapper;
import io.github.wesleyosantos91.adapters.inbound.rest.request.PersonRequest;
import io.github.wesleyosantos91.adapters.inbound.rest.response.PersonResponse;
import io.github.wesleyosantos91.adapters.outbound.sql.mapper.PersonPersistenceMapper;
import io.github.wesleyosantos91.application.ports.PersonServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.wesleyosantos91.adapters.inbound.rest.mapper.PersonHttpMapper.INSTANCE;

@RestController
@RequestMapping("persons")
public class PersonController {

    private final PersonServicePort personServicePort;

    public PersonController(PersonServicePort personServicePort) {
        this.personServicePort = personServicePort;
    }

    @PostMapping
    public ResponseEntity<PersonResponse> create(@RequestBody PersonRequest request) throws Exception {

        var person = personServicePort.save(INSTANCE.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(INSTANCE.toResponse(person));
    }

    @GetMapping(value ="/all")
    public ResponseEntity<List<PersonResponse>> findAll() {

        var persons = personServicePort.findAll()
                .stream().map(INSTANCE::toResponse).collect(Collectors.toList());

        return ResponseEntity.ok().body(persons);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable Long id, @RequestBody PersonRequest request) throws Exception {

        var person = personServicePort.update(id, INSTANCE.toDomain(request));
        return ResponseEntity.status(HttpStatus.OK).body(INSTANCE.toResponse(person));
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        personServicePort.delete(id);
        return ResponseEntity.ok().build();
    }

}
