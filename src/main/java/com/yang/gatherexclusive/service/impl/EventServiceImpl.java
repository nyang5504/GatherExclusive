package com.yang.gatherexclusive.service.impl;

import com.yang.gatherexclusive.dto.EventDto;
import com.yang.gatherexclusive.dto.EventInviteeDto;
import com.yang.gatherexclusive.dto.PotluckItemDto;
import com.yang.gatherexclusive.entity.Event;
import com.yang.gatherexclusive.entity.EventInvitee;
import com.yang.gatherexclusive.entity.PotluckItem;
import com.yang.gatherexclusive.entity.User;
import com.yang.gatherexclusive.repository.EventRepository;
import com.yang.gatherexclusive.repository.UserRepository;
import com.yang.gatherexclusive.service.EventService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveEvent(EventDto eventDto) {
        Event event = new Event();
        event.setEventName(eventDto.getEventName());
        event.setEventType(eventDto.getEventType());
        event.setEventTime(eventDto.getEventTime());
        event.setEventLocation(eventDto.getEventLocation());
        event.setEventDescription(eventDto.getEventDescription());
//        Set<EventInviteeDto> invites = new HashSet<>(eventDto.getInvites());
        Set<EventInvitee> inviteeSet = eventDto.getInvites().stream().map(inviteeDto ->
                convertDtoToEntity(inviteeDto,event)).collect(Collectors.toSet());
        event.setEventInvitees(inviteeSet);
        if(!eventDto.getPotluckItems().isEmpty()){
            Set<PotluckItem> items = eventDto.getPotluckItems().stream().map(itemDto ->
                    convertDtoToEntity(itemDto)).collect(Collectors.toSet());
            event.setEventPotluckItems(items);
        }
        eventRepository.save(event);
    }

    @Override
    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    private EventInvitee convertDtoToEntity(EventInviteeDto eventInviteeDto, Event event){
        EventInvitee eventInvitee = new EventInvitee();
        User inviteeUser = userRepository.findByEmail(eventInviteeDto.getInviteeEmail());
        eventInvitee.setEvent(event);
        eventInvitee.setInvitee(inviteeUser);
        eventInvitee.setRsvped(false);
        return eventInvitee;
    }

    private PotluckItem convertDtoToEntity(PotluckItemDto potluckItemDto){
        PotluckItem potluckItem = new PotluckItem();
        potluckItem.setItemName(potluckItemDto.getItemName());
        potluckItem.setQuantity(potluckItem.getQuantity());
        return potluckItem;
    }
}
