package com.yang.gatherexclusive.service.impl;

import com.yang.gatherexclusive.dto.EventDto;
import com.yang.gatherexclusive.dto.EventInviteeDto;
import com.yang.gatherexclusive.dto.PotluckItemDto;
import com.yang.gatherexclusive.entity.Event;
import com.yang.gatherexclusive.entity.EventInvitee;
import com.yang.gatherexclusive.entity.PotluckItem;
import com.yang.gatherexclusive.entity.User;
import com.yang.gatherexclusive.repository.EventInviteeRepository;
import com.yang.gatherexclusive.repository.EventRepository;
import com.yang.gatherexclusive.repository.UserRepository;
import com.yang.gatherexclusive.service.EventInviteeService;
import com.yang.gatherexclusive.service.EventService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventInviteeServiceImpl implements EventInviteeService {

    private final EventInviteeRepository eventInviteeRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    public EventInviteeServiceImpl(EventInviteeRepository eventInviteeRepository,
                                   EventRepository eventRepository, UserRepository userRepository,
                                   EventService eventService) {
        this.eventInviteeRepository = eventInviteeRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
    }

    /**
     * allows user to rsvp to an event
     * @param event_id
     * @param email
     */
    @Override
    public void rsvpToEvent(Long event_id, String email) {
        Optional<Event> eventOptional = eventRepository.findById(event_id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            User user = userRepository.findByEmail(email);
            EventInvitee eventInvitee =  eventInviteeRepository.findByEventAndInvitee(event,user);
            if (eventInvitee != null) {
                eventInvitee.setRsvped(true);
                eventInviteeRepository.save(eventInvitee);
            }
        }
    }

    /**
     *
     * @param event_id
     * @return a list of those invited to an event
     */
    @Override
    public List<EventInviteeDto> findInvitedByEventId(Long event_id) {
        return eventInviteeRepository.findByEvent_IdAndRsvped(event_id, false).stream()
                .map(invitee -> convertEntityToDto(invitee)).collect(Collectors.toList());
    }

    /**
     *
     * @param event_id
     * @return list of all those who rsvped to a certain event
     */
    @Override
    public List<EventInviteeDto> findRsvpedByEventId(Long event_id) {
        return eventInviteeRepository.findByEvent_IdAndRsvped(event_id, true).stream()
                .map(invitee -> convertEntityToDto(invitee)).collect(Collectors.toList());
    }

    /**
     * helper method to convert EventInvitee to EventInviteeDto
     * @param eventInvitee
     * @return EventInviteeDto
     */
    private EventInviteeDto convertEntityToDto(EventInvitee eventInvitee) {
        EventInviteeDto eventInviteeDto = new EventInviteeDto();
        eventInviteeDto.setId(eventInvitee.getId());
        eventInviteeDto.setEvent(convertEntityToDto(eventInvitee.getEvent()));
        eventInviteeDto.setInviteeEmail(eventInvitee.getInvitee().getEmail());
        eventInviteeDto.setRsvped(eventInvitee.getRsvped());
        return eventInviteeDto;
    }

    /**
     * helper method to convert Event to EventDto
     * @param event
     * @return EventDto
     */
    private EventDto convertEntityToDto(Event event){
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setEventName(event.getEventName());
        eventDto.setEventType(event.getEventType());
        eventDto.setEventTime(event.getEventTime());
        eventDto.setEventLocation(event.getEventLocation());
        eventDto.setEventDescription(event.getEventDescription());
        Set<String> inviteeEmails = event.getEventInvitees().stream().map(eInvitee -> eInvitee.getInvitee().getEmail()).collect(Collectors.toSet());
        eventDto.setInvites(String.join(", ", inviteeEmails));
        List<PotluckItem> potluckItems = event.getEventPotluckItems().stream().toList();
        eventDto.setPotluckItems(potluckItems.stream().map(item -> convertEntityToDto(item)).collect(Collectors.toList()));
        return eventDto;
    }

    /**
     * helper method to convert PotluckItem to PotluckItemDto
     * @param potluckItem
     * @return PotluckItemDto
     */
    private PotluckItemDto convertEntityToDto(PotluckItem potluckItem){
        PotluckItemDto potluckItemDto = new PotluckItemDto();
        potluckItemDto.setItemName(potluckItem.getItemName());
        potluckItemDto.setQuantity(potluckItem.getQuantity());
        return potluckItemDto;
    }
}
