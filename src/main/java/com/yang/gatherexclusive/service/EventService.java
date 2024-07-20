package com.yang.gatherexclusive.service;

import com.yang.gatherexclusive.dto.EventDto;
import com.yang.gatherexclusive.entity.Event;

import java.util.Optional;

public interface EventService {
    void saveEvent(EventDto eventDto);
    Optional<Event> findEventById(Long id);
//    List<Event> findAllEvents();
}
