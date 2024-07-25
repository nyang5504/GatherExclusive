package com.yang.gatherexclusive.service;

import com.yang.gatherexclusive.dto.EventInviteeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EventInviteeService {
    void rsvpToEvent(Long event_id, String email);
    List<EventInviteeDto> findInvitedByEventId(Long event_id);
    List<EventInviteeDto> findRsvpedByEventId(Long event_id);
}
