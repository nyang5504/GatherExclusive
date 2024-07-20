package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
