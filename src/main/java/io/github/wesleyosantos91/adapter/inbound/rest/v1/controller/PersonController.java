package io.github.wesleyosantos91.adapter.inbound.rest.v1.controller;

import io.github.wesleyosantos91.adapter.inbound.rest.v1.event.ResourceCreatedEvent;
import io.github.wesleyosantos91.adapter.inbound.rest.v1.request.PersonRequest;
import io.github.wesleyosantos91.adapter.inbound.rest.v1.response.PersonResponse;
import io.github.wesleyosantos91.adapter.inbound.rest.v1.mapper.PersonHttpMapper;
import io.github.wesleyosantos91.application.core.domain.PageInfo;
import io.github.wesleyosantos91.application.ports.in.PersonServicePort;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/persons")
public class PersonController {

    private final PersonServicePort personServicePort;
    private final ApplicationEventPublisher publisher;
    private final HttpServletResponse response;

    public PersonController(PersonServicePort personServicePort, ApplicationEventPublisher publisher, HttpServletResponse response) {
        this.personServicePort = personServicePort;
        this.publisher = publisher;
        this.response = response;
    }

    @PostMapping
    public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonRequest request) throws Exception {

        var person = personServicePort.create(PersonHttpMapper.INSTANCE.toDomain(request));
        publisher.publishEvent(new ResourceCreatedEvent(this, response, person.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(PersonHttpMapper.INSTANCE.toResponse(person));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonResponse> getById(@PathVariable Long id) {
        var person = personServicePort.getById(id);
        return ResponseEntity.ok().body(PersonHttpMapper.INSTANCE.toResponse(person));
    }

    @GetMapping
    public ResponseEntity<Page<PersonResponse>> find(@PageableDefault(sort = "id",
            direction = Sort.Direction.ASC,
            page = 0,
            size = 10) Pageable pageable) {

        PageInfo pageInfo = new PageInfo();
        BeanUtils.copyProperties(pageable, pageInfo);
        var page = personServicePort.find(pageInfo);

        return ResponseEntity.ok().body(PersonHttpMapper.INSTANCE.toPageResponse(page));
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable Long id, @RequestBody PersonRequest request) throws Exception {

        var person = personServicePort.update(id, PersonHttpMapper.INSTANCE.toDomain(request));
        return ResponseEntity.status(HttpStatus.OK).body(PersonHttpMapper.INSTANCE.toResponse(person));
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        personServicePort.delete(id);
        return ResponseEntity.noContent().build();
    }

}
