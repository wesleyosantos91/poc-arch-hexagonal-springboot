package io.github.wesleyosantos91.adapter.outbound.sql.mysql.repository;

import io.github.wesleyosantos91.adapter.outbound.sql.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
