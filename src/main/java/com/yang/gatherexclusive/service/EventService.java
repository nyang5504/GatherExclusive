package com.yang.gatherexclusive.service;

import com.yang.gatherexclusive.dto.EventDto;
import com.yang.gatherexclusive.entity.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface EventService {
    void saveEvent(EventDto eventDto) throws Exception;
    EventDto findEventById(Long id);
//    List<Event> findAllEvents();
    List<EventDto> findEventsByOrganizerEmail(String email);
    List<EventDto> findPendingEventsByInviteeEmail(String email);
    List<EventDto> findRSVPedEventsByInviteeEmail(String email);
    void deleteEvent(Long id);
}
