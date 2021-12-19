package com.ywook.calendar.repository;

import com.ywook.calendar.domain.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConcertRepository extends JpaRepository<ConcertEntity, Long> {

    Optional<ConcertEntity> findById(Long id);


}
